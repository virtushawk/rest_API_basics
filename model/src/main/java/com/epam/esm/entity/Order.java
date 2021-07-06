package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@EqualsAndHashCode(exclude = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gift_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private List<Certificate> certificates;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal cost;

    @Column(name = "order_date")
    private ZonedDateTime orderTime;

    @PrePersist
    public void onPersist() {
        setOrderTime(ZonedDateTime.now(ZoneId.systemDefault()));
    }
}
