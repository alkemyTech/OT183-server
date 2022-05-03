package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationDto;
import com.alkemy.ong.dto.SlideDto;
import com.alkemy.ong.dto.SlideRequestDto;
import com.alkemy.ong.dto.SlideResponseCreationDto;
import com.alkemy.ong.dto.type.SlideDtoType;
import com.alkemy.ong.exception.EntityNotFoundException;
import com.alkemy.ong.exception.NullListException;
import com.alkemy.ong.mapper.SlideMapper;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.AmazonS3Service;
import com.alkemy.ong.service.IOrganizationService;
import com.alkemy.ong.service.ISlideService;
import com.alkemy.ong.util.CustomMultipart;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;
import java.util.Locale;

@AllArgsConstructor
@Service
public class SlideServiceImpl implements ISlideService {

    private final SlideRepository repository;

    private final SlideMapper mapper;

    private final MessageSource messageSource;

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

    public void deleteSlice(Long id){

        if (!repository.existsById(id)){
            throw new EntityNotFoundException("Slice", "id", id);
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

}
