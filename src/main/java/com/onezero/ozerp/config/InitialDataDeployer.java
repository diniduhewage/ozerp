package com.onezero.ozerp.config;

import com.onezero.ozerp.entity.Action;
import com.onezero.ozerp.entity.Permission;
import com.onezero.ozerp.entity.Role;
import com.onezero.ozerp.entity.RolePermission;
import com.onezero.ozerp.entity.User;
import com.onezero.ozerp.entity.UserRole;
import com.onezero.ozerp.repository.ActionRepository;
import com.onezero.ozerp.repository.ComponentRepository;
import com.onezero.ozerp.repository.RoleRepository;
import com.onezero.ozerp.repository.UserRepository;
import com.onezero.ozerp.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.Entity;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@AllArgsConstructor
public class InitialDataDeployer {

    private final static String ADD = "ADD";
    private final static String VIEW = "VIEW";
    private final static String VIEW_LIST = "VIEW_LIST";
    private final static String EDIT = "EDIT";
    private final static String DELETE = "DELETE";
    private final Long timestamp = DateTime.now(DateTimeZone.UTC).getMillis();
    private final ActionRepository actionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final ComponentRepository componentRepository;

    @PostConstruct
    public void appReady() throws ParseException {

        Role adminRole = roleRepository.findByCode("SUPER_ADMIN");

        User adminUser = new User();
        adminUser.setEmail("admin@admin.com");
        adminUser.setFirstName("super");
        adminUser.setLastName("admin");
        adminUser.setEnabled(true);
        adminUser.setPassword("$2a$11$0ucwxHF.3670kBEoSqgd1e8Zqez44eS2fsc2tmNrGV6UUdUxhN2K.");
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

        Action finalAddAction = actionRepository.findByCode(ADD);
        Action finalViewAction = actionRepository.findByCode(VIEW);
        Action finalViewListAction = actionRepository.findByCode(VIEW_LIST);
        Action finalEditAction = actionRepository.findByCode(EDIT);
        Action finalDeleteAction = actionRepository.findByCode(DELETE);

        List<String> entityNames = scanEntityNames();
        Collections.sort(entityNames);
        List<com.onezero.ozerp.entity.Component> componentList = new ArrayList<>();
        entityNames.forEach(entity -> {
            if (!entity.equalsIgnoreCase("PasswordResetToken") && !entity.equalsIgnoreCase("VerificationToken")) {
                com.onezero.ozerp.entity.Component component =
                        new com.onezero.ozerp.entity.Component(null, entity, addUnderscores(entity).toUpperCase(), timestamp, null, null);
                if (!componentRepository.existsByCode(component.getCode())) {
                    assert savedAdminUser != null;
                    List<Permission> permissions = Arrays.asList(
                            new Permission(null, ADD + "_" + component.getCode(), timestamp, timestamp, component, finalAddAction, new ArrayList<>()),
                            new Permission(null, VIEW + "_" + component.getCode(), timestamp, timestamp, component, finalViewAction, new ArrayList<>()),
                            new Permission(null, VIEW_LIST + "_" + component.getCode(), timestamp, timestamp, component,
                                    finalViewListAction, new ArrayList<>()),
                            new Permission(null, EDIT + "_" + component.getCode(), timestamp, timestamp, component, finalEditAction, new ArrayList<>()),
                            new Permission(null, DELETE + "_" + component.getCode(), timestamp, timestamp, component, finalDeleteAction, new ArrayList<>())
                    );

                    permissions.forEach(permission -> {
                        RolePermission rolePermission = new RolePermission(null, timestamp, timestamp, permission, adminRole);
                        permission.getRolePermissions().add(rolePermission);
                    });

                    component.setPermissions(permissions);
                    componentList.add(component);
                }

            }
        });
        componentRepository.saveAll(componentList);
    }

    private List<String> scanEntityNames() {
        ClassPathScanningCandidateComponentProvider provider =
                new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AnnotationTypeFilter(Entity.class));
        List<String> entityNames = new ArrayList<>();
        for (Class<?> clazz : provider.findCandidateComponents("com.onenzero.ozerp.entity")
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
