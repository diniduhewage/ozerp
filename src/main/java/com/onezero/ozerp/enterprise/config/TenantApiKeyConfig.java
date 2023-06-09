package com.onezero.ozerp.enterprise.config;

import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.onezero.ozerp.enterprise.entity.Tenant;
import com.onezero.ozerp.enterprise.repository.TenantRepository;
import com.onezero.ozerp.enterprise.repository.UserTenantRepository;
import com.onezero.ozerp.enterprise.service.impl.TenantServiceImpl;


@Configuration
public class TenantApiKeyConfig {
    private final static Logger LOG = LogManager.getLogger(TenantServiceImpl.class);
    private ConcurrentHashMap<String, Tenant> tenantMap;
    private ConcurrentHashMap<String, Long> contextUserMap;

    private TenantRepository tenantRepository;
    private UserTenantRepository userTenantRepository;

    @PostConstruct
    public void initializeTenantMap() {
        tenantMap = new ConcurrentHashMap<>();
        List<Tenant> tenants = tenantRepository.findByEnabledTrueAndIsDeleteFalse();
        for (Tenant tenant : tenants) {
            tenantMap.put(tenant.getTenantApiKey(), tenant);
        }
        LOG.info("TenantApiKeyConfig.initializeTenantMap  {}", tenantMap);
        
        //get values for contextUserMap for context user
        contextUserMap = new ConcurrentHashMap<>();
        List<Object[]> contextUserTenantList = userTenantRepository.getEmailAndTenantCountForContextUsers();
        for (Object[] row : contextUserTenantList) {
            String email = (String) row[0];
            BigInteger tenantCountBigInteger = (BigInteger) row[1];
            contextUserMap.put(email, tenantCountBigInteger.longValue());
        }
    }

    public boolean isValidTenantApiKey(String apiKey) {
        return tenantMap.containsKey(apiKey);
    }

    public void addOrUpdateTenantMap(Tenant tenant) {
        if (tenant.getEnabled() && !tenant.getIsDelete()) {
            tenantMap.put(tenant.getTenantApiKey(), tenant);
        } else {
            tenantMap.remove(tenant.getTenantApiKey());
            LOG.info("TenantApiKeyConfig.addOrUpdateTenantMap  removed apiKey {}", tenant.getTenantApiKey());
        }
        LOG.info("TenantApiKeyConfig.addOrUpdateTenantMap  {}", tenantMap);
    }
    
    public void addContextUserMap(String email, Long tenantCount) {
    	contextUserMap.put(email, tenantCount);
    }

    public Long getContextUserMapTenantCount(String email){
        return contextUserMap.get(email);
    }
    public Long getTenantId(String apiKey){
        return tenantMap.get(apiKey).getId();
    }
    @Autowired
    public void setTenantRepository(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    @Autowired
	public void setUserTenantRepository(UserTenantRepository userTenantRepository) {
		this.userTenantRepository = userTenantRepository;
	}
    
    
}
