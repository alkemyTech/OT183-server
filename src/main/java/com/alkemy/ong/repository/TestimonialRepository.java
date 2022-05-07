package com.alkemy.ong.repository;

import com.alkemy.ong.model.Testimonial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestimonialRepository extends JpaRepository<Testimonial,Long> {

    @Override
    Page<Testimonial> findAll(Pageable pageable);


}
