package com.onenzero.ozerp.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onenzero.ozerp.core.enums.ContactInfoType;
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
public class CompanyContactInfo implements java.io.Serializable {
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
    private String contactId;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactInfoType contactType;

    @Column(length = 100, nullable = false)
    private String contactValue;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean isPrimary;
}
