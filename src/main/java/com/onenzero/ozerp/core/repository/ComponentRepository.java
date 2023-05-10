package com.onenzero.ozerp.core.repository;

import com.onenzero.ozerp.core.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

}