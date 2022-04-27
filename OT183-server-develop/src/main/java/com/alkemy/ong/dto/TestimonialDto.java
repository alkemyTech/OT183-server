package com.alkemy.ong.dto;

import com.alkemy.ong.dto.type.TestimonialDtoType;
import com.alkemy.ong.exception.DataRepresentationException;
import lombok.Builder;
import lombok.Getter;
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
public class TestimonialDto implements IGenericDto<TestimonialDtoType> {

    private Long id;

    @NotBlank(message = "{error.empty_field}")
    private String name;

    private String image;

    @NotBlank(message = "{error.empty_field}")
    private String content;

    private LocalDate created;

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
