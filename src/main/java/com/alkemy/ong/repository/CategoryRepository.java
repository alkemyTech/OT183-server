package com.alkemy.ong.repository;

import com.alkemy.ong.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query(value = "SELECT COUNT(id) FROM Category")
    int getCategoriesQuantity();

}
