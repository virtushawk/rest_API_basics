package com.epam.esm.specification;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Certificate specifications class is used to provide Specification object based on specific criteria.
 */
@UtilityClass
public class CertificateSpecifications {

    private static final String CERTIFICATE_TAGS_ATTRIBUTE_NAME = "tags";
    private static final String NAME_ATTRIBUTE = "name";
    private static final String LIKE_OPERATOR_FORMAT = "%%%s%%";
    private static final String DESCRIPTION_ATTRIBUTE = "description";
    private static final String SQL_ASC = "ASC";
    private static final String IS_ACTIVE_ATTRIBUTE = "isActive";
    private static final boolean IS_ACTIVE_VALUE = true;


    /**
     * Is active specification.
     *
     * @return the specification
     */
    public static Specification<Certificate> isActive() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get(IS_ACTIVE_ATTRIBUTE), IS_ACTIVE_VALUE);
    }

    /**
     * Having tags specification.
     *
     * @param tags the tags
     * @return the specification
     */
    public static Specification<Certificate> havingTags(List<String> tags) {
        return (root, query, criteriaBuilder) -> {
            Join<Certificate, Tag> join = root.join(CERTIFICATE_TAGS_ATTRIBUTE_NAME, JoinType.INNER);
            query.groupBy(root);
            query.having(criteriaBuilder.equal(criteriaBuilder.count(root), tags.size()));
            return criteriaBuilder.in(join.get(NAME_ATTRIBUTE)).value(tags);
        };
    }

    /**
     * Having text in name or description specification.
     *
     * @param text the text
     * @return the specification
     */
    public static Specification<Certificate> havingTextInNameOrDescription(String text) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.or(criteriaBuilder.like(root.get(NAME_ATTRIBUTE),
                String.format(LIKE_OPERATOR_FORMAT, text)),
                criteriaBuilder.like(root.get(DESCRIPTION_ATTRIBUTE),
                        String.format(LIKE_OPERATOR_FORMAT, text)));
    }

    /**
     * Having order and sort specification.
     *
     * @param order the order
     * @param sort  the sort
     * @return the specification
     */
    public static Specification<Certificate> havingOrderAndSort(List<String> order, List<String> sort) {
        return (root, query, criteriaBuilder) -> {
            List<Order> ordersList = new ArrayList<>();
            order
                    .forEach(s -> ordersList.add((ObjectUtils.isEmpty(sort)
                            || sort.remove(0).toUpperCase(Locale.ROOT).equals(SQL_ASC))
                            ? criteriaBuilder.asc(root.get(s)) : criteriaBuilder.desc(root.get(s))));
            Order[] orders = new Order[ordersList.size()];
            query.orderBy(ordersList.toArray(orders));
            return null;
        };
    }
}
