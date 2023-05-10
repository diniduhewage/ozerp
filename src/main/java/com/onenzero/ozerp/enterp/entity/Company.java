package com.onenzero.ozerp.enterp.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.onenzero.ozerp.appbase.entity.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company_tab")
public class Company implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(length = 30, nullable = false, unique = true)
    private String companyId;

    @Column(length = 100, nullable = false)
    private String companyName;
    @Column(length = 100)
    private String companyLogo;

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser createdBy;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyAddress> companyAddressList;

    @JsonManagedReference
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CompanyContactInfo> companyContactList;
}
