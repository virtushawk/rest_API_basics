package com.epam.esm.util;

import com.epam.esm.entity.QuerySpecification;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@UtilityClass
public class SQLStatementFormatter {

    private static final String SELECT_CERTIFICATE_QUERY = "SELECT gift_certificate.id,gift_certificate.name,gift_certificate.description,gift_certificate.price,gift_certificate.duration,gift_certificate.create_date,gift_certificate.last_update_date FROM gift_certificate" +
            " LEFT JOIN tag_has_gift_certificate ON gift_certificate_id = gift_certificate.id LEFT JOIN tag ON tag_id = tag.id" +
            " WHERE (:tag IS NULL OR tag.name = :tag) AND (:text IS NULL OR (gift_certificate.name LIKE %s OR gift_certificate.description LIKE %s)) " +
            "ORDER BY %s";

    public static String prepareCertificateQueryStatement(QuerySpecification querySpecification) {
        String text = "'%%'";
        StringBuilder result = new StringBuilder();
        result.append("NULL");
        if (!ObjectUtils.isEmpty(querySpecification.getText())) {
            text = "'%" + querySpecification.getText() + "%'";
        }
        if (!ObjectUtils.isEmpty(querySpecification.getOrder())) {
            List<String> sorts = new ArrayList<>(querySpecification.getOrder());
            sorts = sorts.stream().map(o -> "ASC").collect(Collectors.toList());
            if (!ObjectUtils.isEmpty(querySpecification.getSort())) {
                int i = 0;
                for (String sort : querySpecification.getSort()) {
                    sorts.set(i, sort);
                    i++;
                    if (i == sorts.size()) {
                        break;
                    }
                }
            }
            result = new StringBuilder();
            int i = 0;
            for (String order : querySpecification.getOrder()) {
                result.append(order).append(' ').append(sorts.get(i).toUpperCase(Locale.ROOT)).append(',');
            }
            result.deleteCharAt(result.length() - 1);
        }
        return String.format(SELECT_CERTIFICATE_QUERY, text, text, result);
    }
}
