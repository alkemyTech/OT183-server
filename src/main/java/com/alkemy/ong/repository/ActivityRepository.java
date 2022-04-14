package com.alkemy.ong.repository;

import com.alkemy.ong.entity.ActivityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
