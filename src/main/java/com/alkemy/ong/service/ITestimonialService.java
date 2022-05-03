package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialDto;

public interface ITestimonialService {

    public Object createTestimonial(TestimonialDto dto);
    public void deleteTestimonial(Long id);
    Object updateTestimonialById(Long id, TestimonialDto testimonialDto);

}
