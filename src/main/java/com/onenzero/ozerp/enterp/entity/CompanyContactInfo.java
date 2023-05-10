package com.onenzero.ozerp.enterp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.onenzero.ozerp.enterp.enums.ContactInfoType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_contact_info_tab")
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
