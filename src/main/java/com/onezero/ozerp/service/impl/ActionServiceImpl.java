package com.onezero.ozerp.service.impl;

import com.onezero.ozerp.constant.EntityNotFoundConstatnt;
import com.onezero.ozerp.dto.ActionDTO;
import com.onezero.ozerp.dto.response.ResponseListDTO;
import com.onezero.ozerp.entity.Action;
import com.onezero.ozerp.error.exception.NotFoundException;
import com.onezero.ozerp.error.exception.TransformerException;
import com.onezero.ozerp.repository.ActionRepository;
import com.onezero.ozerp.service.ActionService;
import com.onezero.ozerp.transformer.ActionTransformer;
import com.onezero.ozerp.util.SaasUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ActionServiceImpl implements ActionService {

    private final Logger logger = LoggerFactory.getLogger(ActionService.class);
    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private ActionTransformer actionTransformer;

    @Override
    public ActionDTO saveAction(ActionDTO actionDTO) throws TransformerException {
        Action action = actionTransformer.transformDTOToDomain(actionDTO);
        return actionTransformer.transformDomainToDTO(actionRepository.saveAndFlush(action));
    }

    @Override
    public ActionDTO getActionById(Long id) throws NotFoundException, TransformerException {
        if (!actionRepository.existsById(id)) {
            throw new NotFoundException(EntityNotFoundConstatnt.ACTION_NOT_FOUND + id);
        }
        return actionTransformer.transformDomainToDTO(actionRepository.findById(id).get());
    }

    @Override
    public ResponseListDTO<ActionDTO> getAllActions(Integer page, Integer size, String sort) throws TransformerException {
        Page<Action> pageResponse = actionRepository.findAll(SaasUtil.createPageRequest(page, size, sort));
        List<ActionDTO> actionDTOList = actionTransformer.transformDomainToDTO(pageResponse.getContent());
        return new ResponseListDTO<>(actionDTOList, pageResponse.getTotalPages(), pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(), pageResponse.getNumber(),
                pageResponse.getSort(), pageResponse.getNumberOfElements());
    }

}