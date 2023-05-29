package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appservice.entity.IsoCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IsoCountryRepository extends JpaRepository<IsoCountry, Long> {

    boolean existsByCountryCode(String currencyCode);

    IsoCountry findByCountryCode(String currencyCode);
}
