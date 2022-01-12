package com.example.java17restdocsdemo.repository;

import com.example.java17restdocsdemo.domain.Member;
import com.example.java17restdocsdemo.repository.custom.MemberRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

    Member getByName(String name);
}
