package com.example.java17restdocsdemo.repository.custom;

import com.example.java17restdocsdemo.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepositoryCustom {

    Page<Member> findAllByName(String name, Pageable pageable);
}
