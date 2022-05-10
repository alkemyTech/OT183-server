package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;
import com.alkemy.ong.util.pagination.Pagination;
import org.springframework.data.domain.Pageable;

public interface ITestimonialService {

    public Object createTestimonial(TestimonialDto dto);
    public void deleteTestimonial(Long id);
    Object updateTestimonialById(Long id, TestimonialDto testimonialDto);
    Pagination<TestimonialDto>getAllTestimonialPaged(Pageable pageable,Integer page);


}
