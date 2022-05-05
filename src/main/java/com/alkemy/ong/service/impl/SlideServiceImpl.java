package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseCreationDto;
import com.alkemy.ong.dto.SlidesUpdateDTO;
import com.alkemy.ong.dto.response.UpdateSlidesDTO;
import com.alkemy.ong.dto.type.SlideDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.AmazonS3Service;
import com.alkemy.ong.service.IOrganizationService;
import com.alkemy.ong.service.ISlideService;
import com.alkemy.ong.util.CustomMultipart;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@AllArgsConstructor
@Service
public class SlideServiceImpl implements ISlideService {

    private final SlideRepository repository;

    private final SlideMapper mapper;

    private final MessageSource messageSource;
    private final OrganizationRepository organizationRepository;

    @Autowired
    private IOrganizationService OrganizationService;

    @Autowired
    private AmazonS3Service aws3service;

    @Override
    public Object getSlideDetail(Long id) {
        return mapper.toDto(
                repository.findById(id)
                        .orElseThrow(
                                () -> new EntityNotFoundException("Slide", "id", id)
                        )
        ).generateDto(SlideDtoType.DETAILED, messageSource);
    }

    @Override
    @Transactional
    public List<SlideResponseDto> getAll() {
        List<Slide> slideList = repository.findAll(
                Sort.by(Sort.Direction.ASC, "position"));
        if (slideList.isEmpty()) {
            throw new NullListException(messageSource.getMessage("error.null_list", null, Locale.US));
        }
        return mapper.toDtoResponseList(slideList);
    }

    public void deleteSlide(Long id) {

        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Slide", "id", id);
        }

        repository.deleteById(id);
    }

    @Override
    public SlideResponseCreationDto createSlide(SlideRequestDto requestDto){
        OrganizationDto dtoOrg = OrganizationService.getOrganizationDto(requestDto.getOrganizationId());
        checkPosition(requestDto);
        manageImage(requestDto);

        SlideDto slideDto = new SlideDto(requestDto.getImage(), requestDto.getText(), requestDto.getPosition(), dtoOrg);
        Slide toSave = mapper.toEntity(slideDto, dtoOrg.getId());

        Slide slide = repository.save(toSave);

        return mapper.toDtoResponse(slide);
    }

    ///Auxiliary methods:
    private void checkPosition(SlideRequestDto requestDto){
        if(requestDto.getPosition() == null){
            requestDto.setPosition(repository.maxPosition()+1);
        }
    }

    private void manageImage(SlideRequestDto requestDto){
        requestDto.setImage(aws3service.uploadFile(convertImage(requestDto.getImage())));
    }

    private CustomMultipart convertImage(String imagen){
        //Remove "data:image/jpeg;base64", just to keep the bytes to decode
        String trimmedEncodedImage = imagen.substring(imagen.indexOf(",") + 1);

        byte[] decodedBytes = Base64.getDecoder().decode(trimmedEncodedImage);

        String fileName = "slide"+getExtension(imagen);

        CustomMultipart customMultipartFile = new CustomMultipart(decodedBytes, fileName);

        try {
            customMultipartFile.transferTo(customMultipartFile.getFile());
        } catch (IOException e) {
            throw new NullListException(messageSource.getMessage("error.convert_image", null, Locale.US));
        }

        return customMultipartFile;
    }

    public String getExtension(String imagen){
        Integer positionInitial = imagen.indexOf("/")+1;
        Integer positionFinal = imagen.indexOf(";");

        String extension = "."+imagen.substring(positionInitial, positionFinal);
        return extension;
    }

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
