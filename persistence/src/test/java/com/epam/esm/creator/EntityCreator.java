package com.epam.esm.creator;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;

import java.math.BigDecimal;
import java.util.HashSet;

public class EntityCreator {
    public static Certificate validCertificate;
    public static Certificate invalidCertificate;
    public static Tag tag;
    public static Order order;
    public static User user;

    static {
        tag = Tag.builder()
                .name("IT")
                .build();
        validCertificate = Certificate.builder()
                .name("test name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .isActive(true)
                .tags(new HashSet<>())
                .build();
        validCertificate.getTags().add(tag);
    }

}
