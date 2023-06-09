package com.onezero.ozerp.appbase.config;

import com.onezero.ozerp.appbase.entity.Action;
import com.onezero.ozerp.appbase.entity.Permission;
import com.onezero.ozerp.appbase.entity.Role;
import com.onezero.ozerp.appbase.entity.RolePermission;
import com.onezero.ozerp.appbase.entity.User;
import com.onezero.ozerp.appbase.entity.UserRole;
import com.onezero.ozerp.appbase.repository.ActionRepository;
import com.onezero.ozerp.appbase.repository.ComponentRepository;
import com.onezero.ozerp.appbase.repository.RoleRepository;
import com.onezero.ozerp.appbase.repository.UserRepository;
import com.onezero.ozerp.appbase.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.event.EventListener;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class InitialDataDeployer {

    private final Long timestamp = DateTime.now(DateTimeZone.UTC).getMillis();
    private final PasswordEncoder passwordEncoder;
    private final ActionRepository actionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ComponentRepository componentRepository;

    @EventListener
    public void appReady(ApplicationEvent event) throws ParseException {
        List<Role> roles = Arrays.asList(
                new Role(null, "Admin", "ADMIN", timestamp, null, null, null)
                , new Role(null, "System User", "SYSTEM_USER", timestamp, null, null, null)
        );

        roles.forEach(role -> {
            if (!roleRepository.existsByCode(role.getCode())) {
                roleRepository.saveAndFlush(role);
            }
        });

        Role adminRole = roleRepository.findByCode("ADMIN");

        User adminUser = new User();
        adminUser.setEmail("admin@admin.com");
        adminUser.setFirstName("super");
        adminUser.setLastName("admin");
        adminUser.setEnabled(true);
        adminUser.setPassword(passwordEncoder.encode("1234"));
        adminUser.setCreatedDate(timestamp);

        User savedAdminUser;
        if (!userRepository.existsByEmail("admin@admin.com")) {
            savedAdminUser = userRepository.saveAndFlush(adminUser);
        } else {
            savedAdminUser = null;
        }

        UserRole userRole = new UserRole(null, timestamp, null, savedAdminUser, adminRole);
        if (!userRoleRepository.existsById(1L)) {
            userRoleRepository.saveAndFlush(userRole);
        }


        Action addAction = new Action(null, "Add", "ADD", timestamp, timestamp, null);
        if (!actionRepository.existsByCode(addAction.getCode())) {
            addAction = actionRepository.saveAndFlush(addAction);
        }
        Action viewAction = new Action(null, "View", "VIEW", timestamp, timestamp, null);
        if (!actionRepository.existsByCode(viewAction.getCode())) {
            viewAction = actionRepository.saveAndFlush(viewAction);
        }
        Action viewListAction = new Action(null, "View List", "VIEW_LIST", timestamp, timestamp, null);
        if (!actionRepository.existsByCode(viewListAction.getCode())) {
            viewListAction = actionRepository.saveAndFlush(viewListAction);
        }
        Action editAction = new Action(null, "Edit", "EDIT", timestamp, timestamp, null);
        if (!actionRepository.existsByCode(editAction.getCode())) {
            editAction = actionRepository.saveAndFlush(editAction);
        }
        Action deleteAction = new Action(null, "Delete", "DELETE", timestamp, timestamp, null);
        if (!actionRepository.existsByCode(deleteAction.getCode())) {
            deleteAction = actionRepository.saveAndFlush(deleteAction);
        }

        Action finalAddAction = actionRepository.findByCode(addAction.getCode());
        Action finalViewAction = actionRepository.findByCode(viewAction.getCode());
        Action finalViewListAction = actionRepository.findByCode(viewListAction.getCode());
        Action finalEditAction = actionRepository.findByCode(editAction.getCode());
        Action finalDeleteAction = actionRepository.findByCode(deleteAction.getCode());

        List<String> entityNames = scanEntityNames();
        Collections.sort(entityNames);
        entityNames.forEach(entity -> {
            if (!entity.equalsIgnoreCase("PasswordResetToken") && !entity.equalsIgnoreCase("VerificationToken")) {
                com.onezero.ozerp.appbase.entity.Component component =
                        new com.onezero.ozerp.appbase.entity.Component(null, entity, addUnderscores(entity).toUpperCase(), timestamp, null, null);
                if (!componentRepository.existsByCode(component.getCode())) {
                    assert savedAdminUser != null;
                    List<Permission> permissions = Arrays.asList(
                            new Permission(null, "ADD_" + component.getCode(), timestamp, timestamp, component, finalAddAction, new ArrayList<>()),
                            new Permission(null, "VIEW_" + component.getCode(), timestamp, timestamp, component, finalViewAction, new ArrayList<>()),
                            new Permission(null, "VIEW_LIST_" + component.getCode(), timestamp, timestamp, component, finalViewListAction, new ArrayList<>()),
                            new Permission(null, "EDIT_" + component.getCode(), timestamp, timestamp, component, finalEditAction, new ArrayList<>()),
                            new Permission(null, "DELETE_" + component.getCode(), timestamp, timestamp, component, finalDeleteAction, new ArrayList<>())
                    );

                    permissions.forEach(permission -> {
                        RolePermission rolePermission = new RolePermission(null, timestamp, timestamp, permission, adminRole);
                        permission.getRolePermissions().add(rolePermission);
                    });


                    component.setPermissions(permissions);
                    System.out.println("Saving component: " + component.getPermissions().toString());
                    componentRepository.saveAndFlush(component);
                }

            }
        });
    }

    private List<String> scanEntityNames() {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        List<String> entityNames = new ArrayList<>();
        for (Class<?> clazz : provider.findCandidateComponents("com.onezero.ozerp.enterprise.entity")
                .stream()
                .map(beanDef -> {
                    try {
                        return Class.forName(beanDef.getBeanClassName());
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toArray(Class<?>[]::new)) {
            entityNames.add(clazz.getSimpleName());
        }
        return entityNames;
    }

    private String addUnderscores(String text) {
        StringBuilder newText = new StringBuilder();

        for (int i = 0; i < text.length(); i++) {
            if (i > 0 && Character.isUpperCase(text.charAt(i))) {
                newText.append("_");
            }
            newText.append(text.charAt(i));
        }

        return newText.toString();
    }
}
