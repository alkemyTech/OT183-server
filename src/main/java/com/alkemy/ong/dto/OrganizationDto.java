package com.alkemy.ong.dto;

import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.exception.DataRepresentationException;
import lombok.*;
import org.springframework.context.MessageSource;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.Locale;

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

    private String facebook;

    private String linkedin;

    private String instagram;

    public OrganizationDto(String name, String image, String address, String phone) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public Object generateDto(OrganizationDtoType type, MessageSource messageSource) {
        if (type == OrganizationDtoType.PUBLIC_DATA) {
            return new OrganizationPublicDataDto(name, image, address, phone);
        }
        if( type == OrganizationDtoType.DETAILED) {
            return OrganizationDetailedDto.builder()
                    .id(id)
                    .name(name)
                    .image(image)
                    .address(address)
                    .phone(phone)
                    .email(email)
                    .welcomeText(welcomeText)
                    .aboutUsText(aboutUsText)
                    .created(created)
                    .updated(updated)
                    .facebook(facebook)
                    .instagram(instagram)
                    .linkedin(linkedin)
                    .build();
        }
        throw new DataRepresentationException(
                messageSource.getMessage("error.representation_data", null, Locale.US)
        );
    }
}
