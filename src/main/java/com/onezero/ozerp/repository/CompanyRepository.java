package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByCompanyId(String code);
    Company findByCompanyId(String code);
}