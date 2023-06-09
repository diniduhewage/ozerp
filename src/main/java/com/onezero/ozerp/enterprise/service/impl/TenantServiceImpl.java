package com.onezero.ozerp.enterprise.service.impl;

import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.OzErpRuntimeException;
import com.onezero.ozerp.enterprise.config.TenantApiKeyConfig;
import com.onezero.ozerp.enterprise.dto.TenantDTO;
import com.onezero.ozerp.enterprise.entity.Tenant;
import com.onezero.ozerp.enterprise.repository.TenantRepository;
import com.onezero.ozerp.enterprise.service.TenantService;
import com.onezero.ozerp.enterprise.util.ApiKeyGeneratorUtil;
import com.onezero.ozerp.enterprise.util.ContextUtils;
import com.onezero.ozerp.enterprise.util.SaasUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class TenantServiceImpl implements TenantService {
    private final static Logger LOG = LogManager.getLogger(TenantServiceImpl.class);
    private ApiKeyGeneratorUtil apiKeyGenerator;
    private ModelMapper modelMapper;
    private TenantRepository tenantRepository;

    private ContextUtils contextUtils;

    private TenantApiKeyConfig tenantApiKeyConfig;

    @Transactional(readOnly = true)
    @Override
    public boolean isValidTenant(String tenant) {
        return false;
    }

    @Override
    public TenantDTO saveTenant(TenantDTO tenantDTO) throws OzErpRuntimeException {
        LOG.info("TenantServiceImpl.saveTenant req {}", tenantDTO);
        try {
            Tenant tenant = this.modelMapper.map(tenantDTO, Tenant.class);
            tenant.setTenantApiKey(apiKeyGenerator.generateApiKey());
            tenant.setCreatedDate(SaasUtil.timeStampGenerator());
            tenant.setModifiedDate(SaasUtil.timeStampGenerator());
            tenant.setEnabled(tenantDTO.getEnabled());
            tenant.setIsDelete(false);
            tenant.setCreatedBy(contextUtils.getLoggedInUser());
            Tenant savedTenant = tenantRepository.save(tenant);
            LOG.info("TenantServiceImpl.saveTenant res {}", savedTenant);
            // add new tenant to in memory api key map
            tenantApiKeyConfig.addOrUpdateTenantMap(savedTenant);
            return this.modelMapper.map(savedTenant, TenantDTO.class);
        } catch (DataAccessException e) {
            LOG.error("TenantServiceImpl.saveTenant - unable to create tenant {}", tenantDTO.getTenantName(), e);
            throw new OzErpRuntimeException("Cannot proceed with the request. Please try in a few minutes.", 500, null, null);
        }

    }

    @Override
    public TenantDTO updateTenant(TenantDTO tenantDTO, Long id) throws OzErpRuntimeException{
        LOG.info("TenantServiceImpl.updateTenant req {}", tenantDTO);
        try {
            Tenant alreadyExsistsTenant = tenantRepository.findById((Long) id).orElseThrow(() -> {
                    LOG.error("TenantServiceImpl.updateTenant - Tenant not found for id {}", id);;
                    return new NotFoundException("Tenant not found for id = " + id);
                });
                Tenant toBeUpdatedTenant = new Tenant();

                toBeUpdatedTenant.setId(id);
                toBeUpdatedTenant.setEnabled(tenantDTO.getEnabled());
                toBeUpdatedTenant.setTenantCode(tenantDTO.getTenantCode());
                toBeUpdatedTenant.setTenantName(tenantDTO.getTenantName());
                toBeUpdatedTenant.setTenantApiKey(alreadyExsistsTenant.getTenantApiKey());
                toBeUpdatedTenant.setModifiedDate(SaasUtil.timeStampGenerator());
                toBeUpdatedTenant.setCreatedBy(alreadyExsistsTenant.getCreatedBy());
                toBeUpdatedTenant.setCreatedDate(alreadyExsistsTenant.getCreatedDate());
                toBeUpdatedTenant.setIsDelete(alreadyExsistsTenant.getIsDelete());
                toBeUpdatedTenant.setModifiedBy(contextUtils.getLoggedInUser());
                LOG.info("TenantServiceImpl.updateTenant res {}", toBeUpdatedTenant);
                Tenant updatedTenant = this.tenantRepository.save(toBeUpdatedTenant);
                // update in memory api key map
                tenantApiKeyConfig.addOrUpdateTenantMap(updatedTenant);
                return this.modelMapper.map(updatedTenant, TenantDTO.class);
        } catch (DataAccessException e){
            LOG.error("TenantServiceImpl.updateTenant - unable to update tenant {}", tenantDTO.getTenantName(), e);
            throw new OzErpRuntimeException("Cannot proceed with the request. Please try in a few minutes.", 500, null, null);
        }
    }

    @Override
    public TenantDTO deleteTenant(Long id) throws OzErpRuntimeException{
        LOG.info("TenantServiceImpl.deleteTenant req id = {}", id);
        try {
            Tenant alreadyExsistsTenant = tenantRepository.findById((Long) id).orElseThrow(() -> {
                LOG.error("TenantServiceImpl.deleteTenant - Tenant not found for id {}", id);;
                return new NotFoundException("Tenant not found for id = " + id);
            }); alreadyExsistsTenant.setModifiedBy(contextUtils.getLoggedInUser());
                alreadyExsistsTenant.setIsDelete(true);

                Tenant deletedTenant = this.tenantRepository.save(alreadyExsistsTenant);
            // update in memory api key map
            tenantApiKeyConfig.addOrUpdateTenantMap(deletedTenant);
            LOG.info("TenantServiceImpl.deleteTenant res id = {}", deletedTenant);
            return this.modelMapper.map(deletedTenant, TenantDTO.class);
        } catch (DataAccessException e){
            LOG.error("TenantServiceImpl.deleteTenant - unable to deleteTenant tenant {}",  e);
            throw new OzErpRuntimeException("Cannot proceed with the request. Please try in a few minutes.", 500, null, null);
        }
    }
    @Transactional(readOnly = true)
    @Override
    public TenantDTO getTenant(Object id) {
        Tenant tenant;
        if (id instanceof Long) {
            tenant = tenantRepository.findById((Long) id).orElseThrow(() -> {
                LOG.error("TenantServiceImpl.getTenant - Tenant not found for id {}", id);;
                return new NotFoundException("Tenant not found for id = " + id);
            });
        } else if (id instanceof String) {
            tenant = tenantRepository.findTenantByTenantApiKey((String) id).orElseThrow(() -> {
                LOG.error("TenantServiceImpl.getTenant - Tenant not found for key {}", id);;
                return new NotFoundException("Tenant not found for key = " + id);
            });
        } else {
            LOG.error("TenantServiceImpl.getTenant - Invalid ID or API key type {}", id);
            throw new IllegalArgumentException("Invalid ID or API key type");
        }
        return this.modelMapper.map(tenant, TenantDTO.class);
    }
    @Transactional(readOnly = true)
    @Override
    public List<TenantDTO> getAllTenants() {
        try {
        List<Tenant> tenants = this.tenantRepository.findAll();
        return tenants.stream().map(tenant -> this.modelMapper.map(tenant, TenantDTO.class)).collect(Collectors.toList());
    } catch (DataAccessException e) {
        LOG.error("TenantServiceImpl.getAllTenants - unable to get tenants {}", e);
        throw new OzErpRuntimeException("Cannot proceed with the request. Please try in a few minutes.", 500, null, null);
    }
    }


    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setApiKeyGenerator(ApiKeyGeneratorUtil apiKeyGenerator) {
        this.apiKeyGenerator = apiKeyGenerator;
    }
    @Autowired
    public void setTenantRepository(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }
    @Autowired
    public void setContextUtils(ContextUtils contextUtils) {
        this.contextUtils = contextUtils;
    }
    @Autowired
    public void setTenantApiKeyConfig(TenantApiKeyConfig tenantApiKeyConfig) {
        this.tenantApiKeyConfig = tenantApiKeyConfig;
    }
}
