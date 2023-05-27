package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.IsoCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IsoCountryRepository extends JpaRepository<IsoCountry, Long> {

}
