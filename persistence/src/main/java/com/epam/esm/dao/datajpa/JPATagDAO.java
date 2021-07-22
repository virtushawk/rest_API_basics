package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JPATagDAO extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query(value = "select tag.id,tag.name FROM gift_order " +
            "INNER JOIN order_has_gift_certificate ON gift_order.id = gift_order_id " +
            "INNER JOIN tag_has_gift_certificate ON order_has_gift_certificate.gift_certificate_id = tag_has_gift_certificate.gift_certificate_id " +
            "INNER JOIN tag ON tag_id = tag.id GROUP BY user_id,tag_id ORDER BY sum(cost) DESC, count(tag_id) DESC " +
            "LIMIT 1", nativeQuery = true)
    Tag findPopular();
}
