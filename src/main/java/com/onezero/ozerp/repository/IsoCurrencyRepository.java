package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.IsoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IsoCurrencyRepository extends JpaRepository<IsoCurrency, Long> {

}
