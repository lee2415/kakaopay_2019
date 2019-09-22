package com.leel2415.kakaopay.api.dao;

import com.leel2415.kakaopay.api.entity.Access;
import com.leel2415.kakaopay.api.entity.QAccess;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.leel2415.kakaopay.api.entity.QAccess.access;

@Repository
public class AccessRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public AccessRepositorySupport(JPAQueryFactory queryFactory) {
        super(Access.class);
        this.queryFactory = queryFactory;
    }

    public List<Access> findByMaxRateDevice() {
        QAccess access2 = new QAccess("access");

        return queryFactory.selectFrom(access)
                .where(access.rate.in(
                        JPAExpressions.select(access2.rate.max())
                        .from(access2)
                        .groupBy(access2.year)
                ))
                .fetch();
    }

    public Access findByMaxYearDevice(String year){
        QAccess access2 = new QAccess("access");

        return queryFactory.selectFrom(access)
                .where(access.rate.eq(
                        JPAExpressions.select(access2.rate.max())
                        .from(access2)
                        .where(access2.year.eq(year))
                )).where(access.year.eq(year))
                .fetchFirst();
    }

    public Access findMyMaxDeviceYear(String deviceId){
        QAccess access2 = new QAccess("access");

        return queryFactory.selectFrom(access)
                .where(access.rate.eq(
                        JPAExpressions.select(access2.rate.max())
                        .from(access2)
                        .where(access2.deviceId.eq(deviceId))
                )).where(access.deviceId.eq(deviceId))
                .fetchFirst();
    }
}
