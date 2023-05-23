package com.onezero.ozerp.repository;

import com.onezero.ozerp.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
