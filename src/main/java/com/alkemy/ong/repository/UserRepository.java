package com.alkemy.ong.repository;

import com.alkemy.ong.dto.UserBasicDTO;
import com.alkemy.ong.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);
  
    @Query(value = "SELECT new com.alkemy.ong.dto.UserBasicDTO(u.id, u.firstName, u.lastName, u.email) FROM User u")
    public List<UserBasicDTO> getAllUsers();

}
