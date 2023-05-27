package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.Action;
import com.onezero.ozerp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyId(String code);
    Company findByCompanyId(String code);
}