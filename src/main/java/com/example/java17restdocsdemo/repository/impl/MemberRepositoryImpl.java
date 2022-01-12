package com.example.java17restdocsdemo.repository.impl;

import com.example.java17restdocsdemo.common.QuerydslUtil;
import com.example.java17restdocsdemo.domain.Member;
import com.example.java17restdocsdemo.domain.QMember;
import com.example.java17restdocsdemo.repository.custom.MemberRepositoryCustom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QMember member;

    public MemberRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
        this.member = QMember.member;
    }


    @Override
    public Page<Member> findAllByName(String name, Pageable pageable) {
        return QuerydslUtil.getPageImpl(
                queryFactory
                        .select(member.count())
                        .from(member)
                        .where(
                                QuerydslUtil.like(member.name, name)
                        ),
                queryFactory
                        .selectFrom(member)
                        .where(
                                QuerydslUtil.like(member.name, name)
                        )
                        .orderBy(member.id.asc()),
                pageable
        );
    }
}
