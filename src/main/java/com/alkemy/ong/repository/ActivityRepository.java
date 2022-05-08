package com.alkemy.ong.repository;

import com.alkemy.ong.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    @Query(value = "SELECT COUNT(id) FROM Activity")
    int getActivityQuantity();
}
