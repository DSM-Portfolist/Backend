package com.example.portfolist.domain.auth.repository.repository;

import com.example.portfolist.domain.auth.entity.Field;
import com.example.portfolist.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.Optional;

public interface FieldRepository extends JpaRepository<Field, Long>, QuerydslPredicateExecutor<Field> {

    List<Field> findByUser(User user);
    Optional<Field> findByFieldKindPk(int pk);
    void deleteByUser(User user);

}