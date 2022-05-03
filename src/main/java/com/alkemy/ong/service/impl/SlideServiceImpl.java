package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlidesUpdateDTO;
import com.alkemy.ong.dto.response.UpdateSlidesDTO;
import com.alkemy.ong.dto.type.SlideDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.ISlideService;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SlideServiceImpl implements ISlideService {

    private final SlideRepository repository;
    private final SlideMapper mapper;
    private final MessageSource messageSource;
    private final OrganizationRepository organizationRepository;

    @Override
    public Object getSlideDetail(Long id) {
        return mapper.toDto(
                repository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Slide", "id", id)
                        )
        ).generateDto(SlideDtoType.DETAILED, messageSource);
    }

    public void deleteSlice(Long id){

        if (!repository.existsById(id)){
            throw new EntityNotFoundException("Slice", "id", id);
        }

        repository.deleteById(id);
    }

    @Override
    public ResponseEntity<?> updateSlide(Long id, SlidesUpdateDTO slideUpdate) {
        Slide slide = repository.getById(id);

        if(slideUpdate.getText() != null) slide.setText(slideUpdate.getText());
        if(slideUpdate.getPosition() != null) slide.setPosition(slideUpdate.getPosition());
        if(slideUpdate.getImageUrl()!= null) slide.setImageUrl(slideUpdate.getImageUrl());

        // CONTROL IF ORGANIZATION EXISTS
        if(slideUpdate.getOrganization() != null){
            if(!controlExistsOrganization(slideUpdate,slide))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Error organization was not found");
        }
        repository.save(slide);
        UpdateSlidesDTO response = new UpdateSlidesDTO();
        response.setId(slide.getId());
        response.setUrl("slides/"+slide.getId());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * 
     * @param slideUpdate
     * @param slide
     * @return true if Organization exists. 
     * @return false if Organization not exists.
     */
    private Boolean controlExistsOrganization(SlidesUpdateDTO slideUpdate,Slide slide){
            Optional<Organization> organizationRequest = 
            organizationRepository.findById(slideUpdate.getOrganization());
            if(organizationRequest.isPresent()){
                slide.setOrganization(organizationRequest.get());
                return true;
            }else{
                return false;
            }
    }

    
}
