package com.onenzero.ozerp.appsev.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "iso_country_tab")
public class IsoCountry implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 2, nullable = false, unique = true)
    private String countryCode;

    @Column(length = 3, nullable = false)
    private String countryCode3;

    @Column(length = 100, nullable = false)
    private String countryName;
}
