package com.alkemy.ong.dto;

import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.dto.type.SlideDtoType;
import com.alkemy.ong.exception.DataRepresentationException;
import lombok.*;
import org.springframework.context.MessageSource;

import java.util.Locale;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SlideDto implements IGenericDto<SlideDtoType> {

    private Long id;
    private String imageUrl;
    private String text;

    private Integer position;
    private OrganizationDto organization;

    @Override
    public Object generateDto(SlideDtoType type, MessageSource messageSource) {
        if (type == SlideDtoType.DETAILED) {
            return SlideDetailedDto.builder()
                    .id(id)
                    .imageUrl(imageUrl)
                    .text(text)
                    .position(position)
                    .organization(organization.generateDto(OrganizationDtoType.DETAILED, messageSource))
                    .build();
        }
        throw new DataRepresentationException(
                messageSource.getMessage("error.representation_data", null, Locale.US)
        );
    }


    public  SlideDto(String imageUrl, String text, Integer position, OrganizationDto organization) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.position = position;
        this.organization = organization;
    }

    public SlideDetailedDto2 mappingOrganizationDetailDto() {
        return SlideDetailedDto2.builder()
                .id(id)
                .imageUrl(imageUrl)
                .text(text)
                .position(position)
                .build();
    }

}
