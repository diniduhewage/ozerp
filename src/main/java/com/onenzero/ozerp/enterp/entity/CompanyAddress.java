package com.onenzero.ozerp.enterp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onenzero.ozerp.enterp.enums.AddressType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_address_tab")
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
