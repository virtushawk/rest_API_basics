package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * The interface Jpa tag dao.
 */
public interface JPATagDAO extends JpaRepository<Tag, Long> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find popular tag.
     *
     * @return the tag
     */
    @Query(value = "select tag.id,tag.name FROM gift_order " +
            "INNER JOIN order_has_gift_certificate ON gift_order.id = gift_order_id " +
            "INNER JOIN tag_has_gift_certificate ON order_has_gift_certificate.gift_certificate_id = tag_has_gift_certificate.gift_certificate_id " +
            "INNER JOIN tag ON tag_id = tag.id GROUP BY user_id,tag_id ORDER BY sum(cost) DESC, count(tag_id) DESC " +
            "LIMIT 1", nativeQuery = true)
    Tag findPopular();
}
