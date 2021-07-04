package com.epam.esm.entity;

import com.fasterxml.jackson.annotation.JacksonInject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "gift_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.PERSIST})
    @JoinTable(
            name = "order_has_gift_certificate",
            joinColumns = @JoinColumn(name = "gift_order_id"),
            inverseJoinColumns = @JoinColumn(name = "gift_certificate_id"))
    private List<Certificate> certificates;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Column(precision = 10,scale = 2,nullable = false)
    private BigDecimal cost;

    @Column(name = "order_date")
    private ZonedDateTime orderTime;
}
