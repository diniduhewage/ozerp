package com.onezero.ozerp.enterprise.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.onezero.ozerp.appbase.entity.User;
import com.onezero.ozerp.appbase.error.exception.BadRequestException;
import com.onezero.ozerp.appbase.error.exception.NotFoundException;
import com.onezero.ozerp.appbase.error.exception.OzErpRuntimeException;
import com.onezero.ozerp.appbase.repository.UserRepository;
import com.onezero.ozerp.appbase.util.CommonUtils;
import com.onezero.ozerp.enterprise.config.TenantApiKeyConfig;
import com.onezero.ozerp.enterprise.dto.UserTenantDTO;
import com.onezero.ozerp.enterprise.entity.UserTenant;
import com.onezero.ozerp.enterprise.entity.UserTenant_PK;
import com.onezero.ozerp.enterprise.repository.TenantRepository;
import com.onezero.ozerp.enterprise.repository.UserTenantRepository;
import com.onezero.ozerp.enterprise.service.UserTenantService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserTenantServiceImpl implements UserTenantService  {

	@Autowired
	private UserTenantRepository userTenantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private TenantApiKeyConfig tenantApiKeyConfig;
	
	@Override
	public UserTenantDTO save(UserTenantDTO userTenantDTO) throws OzErpRuntimeException {
		
		if (!userRepository.existsById(userTenantDTO.getUserId())) {
			throw new NotFoundException("User not found!");
		}
		
		if (!tenantRepository.existsById(userTenantDTO.getTenantId())) {
			throw new NotFoundException("Tenant not found!");
		}
		if (userTenantRepository.findUserTenantByUserIdAndTenantId(userTenantDTO.getUserId(),
				userTenantDTO.getTenantId()) != null) {
              throw new BadRequestException("Already user has the tenant access!");
		}
		UserTenant userTenant = new UserTenant();
		BeanUtils.copyProperties(userTenantDTO, userTenant);
		
		UserTenant_PK userTenant_PK = new UserTenant_PK();
		userTenant_PK.setUserId(userTenantDTO.getUserId());
		userTenant_PK.setTenantId(userTenantDTO.getTenantId());
		userTenant.setUserTenant_PK(userTenant_PK);
		userTenant.setCreatedDate(null != userTenantDTO.getCreatedDate() ? userTenantDTO.getCreatedDate() : CommonUtils.timeStampGenerator());
		userTenant.setModifiedDate(CommonUtils.timeStampGenerator());
		userTenantRepository.save(userTenant);
		//add context user's tenat changes into map
		User user = userRepository.findById(userTenantDTO.getUserId()).get();
		if (user.isContextUser()) {
			tenantApiKeyConfig.addContextUserMap(user.getEmail(), userTenantRepository.countTenantsByUserId(userTenantDTO.getUserId()));
		}
		
		return userTenantDTO;
	}

	@Override
	public UserTenantDTO update(UserTenantDTO userTenantDTO, Long id) throws OzErpRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserTenantDTO delete(Long id) throws OzErpRuntimeException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserTenantDTO getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserTenantDTO> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
