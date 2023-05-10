package com.onenzero.ozerp.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onenzero.ozerp.core.enums.AddressType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CompanyAddress implements java.io.Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(length = 30, nullable = false, unique = true)
    private String addressId;

    @Enumerated(EnumType.STRING)
    @Column(length = 30, nullable = false)
    private AddressType addressType;

    @Column(length = 30, nullable = false)
    private String address1;

    @Column(length = 30)
    private String address2;

    @Column(length = 30)
    private String city;

    @Column(length = 30)
    private String state;

    @Column(length = 30)
    private String province;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isPrimary;

    @Column(length = 200)
    private String address;
}
