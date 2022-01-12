package com.example.java17restdocsdemo.response;

import com.example.java17restdocsdemo.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.time.LocalDateTime;

@Getter
@Relation(collectionRelation = "members")
public class MemberResponse extends RepresentationModel<MemberResponse> {

    private final Long id;
    private final String name;
    private final Integer age;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime insertedAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private final LocalDateTime updatedAt;


    public MemberResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.age = member.getAge();
        this.insertedAt = member.getInsertedAt();
        this.updatedAt = member.getUpdatedAt();
    }
}
