package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    Component findByCode(String code);

    boolean existsByCode(String code);
}