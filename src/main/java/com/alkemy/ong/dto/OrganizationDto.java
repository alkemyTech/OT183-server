package com.alkemy.ong.dto;

import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.exception.DataRepresentationException;
import lombok.*;
import org.springframework.context.MessageSource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto implements IGenericDto<OrganizationDtoType> {

    private Long id;

    @NotBlank(message = "{error.empty_field}")
    private String name;

    @NotBlank(message = "{error.empty_field}")
    private String image;

    private String address;

    private String phone;

    @NotBlank(message = "{error.empty_field}")
    @Email(message = "{error.invalid_email}")
    private String email;

    @NotBlank(message = "{error.empty_field}")
    private String welcomeText;

    private String aboutUsText;

    private LocalDate created;

    private LocalDate updated;

    private List<SlideDto> slides;

    private String facebook;

    private String linkedin;

    private String instagram;


    public OrganizationDto(String name, String image, String address, String phone,
                           String facebook, String linkedin, String instagram) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
        this.facebook = facebook;
        this.linkedin = linkedin;
        this.instagram = instagram;
    }

    @Override
    public Object generateDto(OrganizationDtoType type, MessageSource messageSource) {
        if (type == OrganizationDtoType.PUBLIC_DATA) {
            return OrganizationPublicDataDto.builder()
                    .name(name)
                    .image(image)
                    .address(address)
                    .phone(phone)
                    .facebook(facebook)
                    .linkedin(linkedin)
                    .instagram(instagram)
                    .slides(slides
                            .stream()
                            .map(SlideDto::mappingOrganizationDetailDto)
                            .collect(Collectors.toList()))
                    .build();

        }
        throw new DataRepresentationException(
                messageSource.getMessage("error.representation_data", null, Locale.US)
        );
    }
}
