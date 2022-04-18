package com.alkemy.ong.repository;

import com.alkemy.ong.model.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<Long,User>{}
