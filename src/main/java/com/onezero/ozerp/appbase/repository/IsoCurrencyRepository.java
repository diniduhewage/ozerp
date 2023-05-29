package com.onezero.ozerp.appbase.repository;

import com.onezero.ozerp.appservice.entity.IsoCurrency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IsoCurrencyRepository extends JpaRepository<IsoCurrency, Long> {

    boolean existsByCurrencyCode(String currencyCode);

    IsoCurrency findByCurrencyCode(String currencyCode);
}
