package com.alkemy.ong.dto;

import com.alkemy.ong.dto.type.TestimonialDtoType;
import com.alkemy.ong.exception.DataRepresentationException;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.context.MessageSource;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Locale;

@Builder
@Getter
@Setter
public class TestimonialDto implements IGenericDto<TestimonialDtoType> {

    @ApiModelProperty(example = "1", position = 0)
    private Long id;

    @ApiModelProperty(example = "Marta", position = 1)
    @NotBlank(message = "{error.empty_field}")
    private String name;

    @ApiModelProperty(example = "testimonial.jpg", position = 2)
    private String image;

    @ApiModelProperty(example = "this is content", position = 3)
    @NotBlank(message = "{error.empty_field}")
    private String content;

    @ApiModelProperty(example = "02-02-2020", position = 4)
    private LocalDate created;

    @ApiModelProperty(example = "03-03-2020", position = 5)
    private LocalDate updated;

    @Override
    public Object generateDto(TestimonialDtoType type, MessageSource messageSource) {
        if (type == TestimonialDtoType.DETAILED) {
            return TestimonialDetailedDto.builder()
                    .id(id)
                    .name(name)
                    .image(image)
                    .content(content)
                    .created(created)
                    .updated(updated)
                    .build();
        }
        throw new DataRepresentationException(
                messageSource.getMessage("error.representation_data",null,Locale.US)
        );
    }
}
