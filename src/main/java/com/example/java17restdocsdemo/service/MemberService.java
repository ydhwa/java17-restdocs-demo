package com.example.java17restdocsdemo.service;

import com.example.java17restdocsdemo.domain.Member;
import com.example.java17restdocsdemo.dto.MemberSaveDto;
import com.example.java17restdocsdemo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;


    @Transactional(readOnly = true)
    public Page<Member> getAll(String name, Pageable pageable) {
        return memberRepository.findAllByName(name, pageable);
    }

    @Transactional(readOnly = true)
    public Member get(Long id) {
        return memberRepository.findById(id).orElseThrow(
                () -> new RuntimeException("not found")
        );
    }

    @Transactional
    public Member insert(MemberSaveDto memberSaveDto) {
        if(memberRepository.getByName(memberSaveDto.getName()) != null) {
            throw new RuntimeException("conflict");
        }
        Member member = new Member(memberSaveDto);
        return memberRepository.save(member);
    }

    @Transactional
    public Member update(Long id, MemberSaveDto memberSaveDto) {
        Member member = get(id);
        Member memberForCheck = memberRepository.getByName(memberSaveDto.getName());
        if(memberForCheck != null && !memberForCheck.getId().equals(id)) {
            throw new RuntimeException("conflict");
        }
        member.updateData(memberSaveDto);
        return memberRepository.save(member);
    }

    @Transactional
    public void delete(Long id) {
        Member member = get(id);
        memberRepository.delete(member);
    }

    @Transactional
    @PostConstruct
    public void init() {
        List<Member> members = List.of(
                Member.builder()
                        .name("홍길동")
                        .age(30)
                        .insertedAt(LocalDateTime.now())
                        .build(),
                Member.builder()
                        .name("장길산")
                        .age(20)
                        .insertedAt(LocalDateTime.now())
                        .build(),
                Member.builder()
                        .name("임꺽정")
                        .age(40)
                        .insertedAt(LocalDateTime.now())
                        .build()
        );
        memberRepository.saveAll(members);
    }
}
