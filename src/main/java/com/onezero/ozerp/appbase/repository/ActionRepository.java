package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appbase.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

    @Query(value = "SELECT id FROM Action WHERE id NOT IN (:actionIds)", nativeQuery = true)
    List<Long> findIdsNotInAction(@Param("actionIds") List<Long> actionIds);

    boolean existsByCode(String code);

    Action findByCode(String code);
}