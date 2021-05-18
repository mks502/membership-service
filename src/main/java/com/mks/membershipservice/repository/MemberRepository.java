package com.mks.membershipservice.repository;

import com.mks.membershipservice.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member,Long> {
    Optional<Member> findOneByEmail(String email);
}
