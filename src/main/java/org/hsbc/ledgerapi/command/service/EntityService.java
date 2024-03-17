package org.hsbc.ledgerapi.command.service;

import org.hsbc.ledgerapi.repository.EntityRepository;
import org.hsbc.ledgerapi.commons.dto.LedgerEntityDto;
import org.hsbc.ledgerapi.commons.model.LedgerEntity;
import org.hsbc.ledgerapi.commons.response.EntityCreationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class EntityService {

    private final EntityRepository entityRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public EntityService(EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
        this.modelMapper = new ModelMapper();
    }
    @Transactional
    public EntityCreationResponse saveEntity(LedgerEntityDto ledgerEntityDto){
        LedgerEntity entity = modelMapper.map(ledgerEntityDto,LedgerEntity.class);
        LedgerEntity entitySaved = entityRepository.save(entity);
        return EntityCreationResponse.builder()
                .status("created")
                .description("Successfully Created Entity "+entitySaved.getName())
                .createdAt(LocalDateTime.now())
                .build();
    }


}
