package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appbase.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {
    Component findByCode(String code);

    boolean existsByCode(String code);
}