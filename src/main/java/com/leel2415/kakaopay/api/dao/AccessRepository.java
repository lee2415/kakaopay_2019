package com.leel2415.kakaopay.api.dao;

import com.leel2415.kakaopay.api.entity.Access;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

public interface AccessRepository extends JpaRepository<Access, String> {
    public List<Access> findAll();
}
