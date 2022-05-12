package com.alkemy.ong.repository;

import com.alkemy.ong.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News,Long> {

    @Query(value = "SELECT COUNT(id) FROM News")
    int getNewsQuantity();
}
