package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gift_certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 150, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String description;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int duration;

    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Column(name = "last_update_date")
    private ZonedDateTime lastUpdateDate;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "tag_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    @ManyToMany(mappedBy = "certificates", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    private Set<Order> orders;

    @PrePersist
    public void onPersist() {
        setCreateDate(ZonedDateTime.now(ZoneId.systemDefault()));
        setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }

    @PreUpdate
    public void onUpdate() {
        setLastUpdateDate(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}

