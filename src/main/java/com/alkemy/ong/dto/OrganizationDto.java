package com.alkemy.ong.dto;

import com.alkemy.ong.dto.type.OrganizationDtoType;
import com.alkemy.ong.exception.DataRepresentationException;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto implements IGenericDto<OrganizationDtoType> {

    private Long id;

    @NotBlank(message = "Name must be not empty")
    private String name;

    @NotBlank(message = "Image must be not empty")
    private String image;

    private String address;

    private int phone;

    @NotBlank(message = "Email must be not empty")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Welcome text must be not empty")
    private String welcomeText;

    private String aboutUsText;

    private LocalDate created;

    private LocalDate updated;

    public OrganizationDto(String name, String image, String address, int phone) {
        this.name = name;
        this.image = image;
        this.address = address;
        this.phone = phone;
    }

    @Override
    public Object generateDto(OrganizationDtoType type) {
        if (type == OrganizationDtoType.PUBLIC_DATA) {
            return new OrganizationPublicDataDto(name, image, address, phone);
        }
        throw new DataRepresentationException("There isn't a valid representation object used");
    }
}
