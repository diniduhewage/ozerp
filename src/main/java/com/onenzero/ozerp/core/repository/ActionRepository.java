package com.onenzero.ozerp.core.repository;

import com.onenzero.ozerp.core.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

}