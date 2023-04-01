package com.onenzero.ozerp.appbase.repository;

import com.onenzero.ozerp.appbase.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComponentRepository extends JpaRepository<Component, Long> {

}