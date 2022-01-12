package com.example.java17restdocsdemo.domain;

import com.example.java17restdocsdemo.dto.MemberSaveDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "members")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column(name = "inserted_at")
    private LocalDateTime insertedAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    public Member(MemberSaveDto memberSaveDto) {
        this.name = memberSaveDto.getName();
        this.age = memberSaveDto.getAge();
        this.insertedAt = LocalDateTime.now();
    }

    public void updateData(MemberSaveDto memberSaveDto) {
        this.name = memberSaveDto.getName();
        this.age = memberSaveDto.getAge();
        this.updatedAt = LocalDateTime.now();
    }
}
