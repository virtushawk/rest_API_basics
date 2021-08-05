package com.epam.esm.creator;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.QuerySpecification;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;

public class EntityCreator {
    public static Certificate certificate;
    public static final Certificate update;
    public static final QuerySpecification validQuerySpecification;
    public static final QuerySpecification emptyQuerySpecification;
    public static final QuerySpecification invalidQuerySpecification;
    public static final Order order;
    public static final Tag tag;
    public static final Tag existingTag;
    public static Pageable page;

    static {
        validQuerySpecification = QuerySpecification.builder().build();
        validQuerySpecification.setTags(new ArrayList<>());
        validQuerySpecification.getTags().add("IT");
        emptyQuerySpecification = new QuerySpecification();
        invalidQuerySpecification = QuerySpecification.builder().build();
        invalidQuerySpecification.setTags(new ArrayList<>());
        invalidQuerySpecification.getTags().add("new tag");
        certificate = Certificate.builder()
                .name("test name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .isActive(true)
                .build();
        update = Certificate.builder()
                .name("new name")
                .description("Test description")
                .price(new BigDecimal("11"))
                .duration(4)
                .build();
        order = Order.builder()
                .certificates(new ArrayList<>())
                .user(User.builder().id(1L).name("Roman").build())
                .cost(new BigDecimal(5))
                .build();
        order.getCertificates().add(certificate);
        tag = Tag.builder()
                .name("tag")
                .build();
        existingTag = Tag.builder()
                .name("IT")
                .build();
        page = PageRequest.of(0, 10);
    }
}
