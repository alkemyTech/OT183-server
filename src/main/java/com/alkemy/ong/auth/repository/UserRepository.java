package com.alkemy.ong.auth.repository;

import com.alkemy.ong.auth.model.UserModel;
import com.alkemy.ong.dto.UserBasicDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByEmail(String email);

    @Query(value = "SELECT new com.alkemy.ong.dto.UserBasicDto(u.id, u.firstName, u.lastName, u.email) FROM UserModel u")
    List<UserBasicDto> getAllUsers();

    @Query(value = "SELECT COUNT(id) FROM UserModel")
    int getUsersQuantity();

}
