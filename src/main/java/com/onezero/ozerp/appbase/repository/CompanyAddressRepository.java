package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.enterprise.entity.CompanyAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CompanyAddressRepository extends JpaRepository<CompanyAddress, Long> {
    boolean existsByCompany(String code);
    CompanyAddress findByCompany(String code);
}