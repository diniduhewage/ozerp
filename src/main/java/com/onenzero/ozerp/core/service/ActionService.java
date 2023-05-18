package com.onenzero.ozerp.core.service;

import com.onenzero.ozerp.core.constant.MessageConstants;
import com.onenzero.ozerp.core.dto.ActionDTO;
import com.onenzero.ozerp.core.dto.response.ResponseListDTO;
import com.onenzero.ozerp.core.entity.Action;
import com.onenzero.ozerp.core.error.exception.NotFoundException;
import com.onenzero.ozerp.core.repository.ActionRepository;
import com.onenzero.ozerp.core.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ActionService implements GenericService<ActionDTO, ActionDTO> {
    private static final Logger LOGGER = LoggerFactory.getLogger(ActionService.class);
    private final ActionRepository actionRepository;

    @Override
    public ActionDTO save(ActionDTO actionDTO) {
        LOGGER.debug("Saving action: " + actionDTO);
        actionDTO.setCreatedDate(CommonUtils.timeStampGenerator());
        Action action = new Action();
        BeanUtils.copyProperties(actionDTO, action);
        Action savedAction = actionRepository.save(action);
        ActionDTO returnActionDTO = new ActionDTO();
        BeanUtils.copyProperties(savedAction, returnActionDTO);
        return returnActionDTO;
    }

    @Override
    public ActionDTO getById(Long id) {
        Action action = actionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id));
        ActionDTO actionDTO = new ActionDTO();
        BeanUtils.copyProperties(action, actionDTO);
        return actionDTO;
    }

    @Override
    public ResponseListDTO<ActionDTO> getAll(Pageable pageable) {
        Page<Action> pageResponse = actionRepository.findAll(pageable);
        List<ActionDTO> actionDTOList = pageResponse.map(action -> {
            ActionDTO actionDTO = new ActionDTO();
            BeanUtils.copyProperties(action, actionDTO);
            return actionDTO;
        }).stream().collect(Collectors.toList());
        return new ResponseListDTO<>(actionDTOList,
                pageResponse.getTotalPages(),
                pageResponse.getTotalElements(),
                pageResponse.isLast(),
                pageResponse.getSize(),
                pageResponse.getNumber(),
                pageResponse.getSort(),
                pageResponse.getNumberOfElements());
    }

    @Override
    public ActionDTO update(Long id, ActionDTO actionDTO) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Long id) {
        if (actionRepository.existsById(id)) {
            LOGGER.debug("Deleting action for id: " + id);
            actionRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(MessageConstants.RECORD_NOT_FOUND + id);
        }
    }
}
