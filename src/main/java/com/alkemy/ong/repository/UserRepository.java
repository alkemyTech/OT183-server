package com.alkemy.ong.repository;

import com.alkemy.ong.model.UserModel;
import com.alkemy.ong.dto.UserBasicDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    UserModel findByEmail(String email);

    @Query(value = "SELECT new com.alkemy.ong.dto.UserBasicDto(u.id, u.firstName, u.lastName, u.email) FROM UserModel u")
    List<UserBasicDto> getAllUsers();

}
