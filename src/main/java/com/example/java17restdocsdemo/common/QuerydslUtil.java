package com.example.java17restdocsdemo.common;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.List;

@UtilityClass
public class QuerydslUtil {

    /**
     * Querydsl의 eq메소드를 전체적으로 사용할 수 있도록 만든 util
     * optional한 경우 사용
     *
     * @param path  QClass.variable
     * @param value value
     * @return return null or BooleanExpression
     */
    public <T> BooleanExpression eq(SimpleExpression<T> path, T value) {
        if(value == null) {
            return null;
        }

        // if value is String type, must check "".
        if(value instanceof String) {
            if("".equals(value)) {
                return null;
            }
        }

        return path.eq(value);
    }

    /**
     * Querydsl의 like메소드를 전체적으로 사용할 수 있도록 만든 util
     * 부분일치 검색용, optional한 경우 사용
     *
     * @param stringPath  QClass.variable
     * @param value value
     * @return return null or BooleanExpression
     */
    public BooleanExpression like(StringPath stringPath, String value) {
        if(!StringUtils.hasLength(value)) {
            return null;
        }

        return stringPath.like("%" + value + "%");
    }

    public <T> BooleanExpression in(SimpleExpression<T> path, List<T> value) {
        if(value == null || value.isEmpty()) {
            return null;
        }

        return path.in(value);
    }

    /**
     * order specifier. querydsl로 pageable sort 처리할 때 사용
     * 객체 그래프를 타야 하는 속성들을 넣으면 오류가 발생한다.(ex) serviceEntity.gitlab.id)
     *
     * @param property 정렬하고 싶은 속성(QEntity field에 맞춰서 써줘야 함)
     * @param orderDirection 오름차순? 내림차순?
     * @param basePath QEntity 객체(ex) serviceEntity, appEntity, ...)
     * @return
     */
    public <T> OrderSpecifier<?> toOrderSpecifier(String property, Sort.Direction orderDirection, PathBuilder<T> basePath) {
        StringPath path = basePath.getString(property);

        Order querydslOrder = null;
        if(orderDirection == Sort.Direction.ASC) {
            querydslOrder = Order.ASC;
        }
        if(orderDirection == Sort.Direction.DESC) {
            querydslOrder = Order.DESC;
        }

        return new OrderSpecifier(querydslOrder, path);
    }

    public <T> PageImpl<T> getPageImpl(JPAQuery<Long> countQuery, JPAQuery<T> paginationQuery, Pageable pageable) {
        long totalCount = countQuery.fetchOne();

        paginationQuery
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        return new PageImpl<>(paginationQuery.fetch(), pageable, totalCount);
    }
}

