package com.alkemy.ong.repository;

import com.alkemy.ong.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member,Long>,PagingAndSortingRepository<Member,Long>{
}
