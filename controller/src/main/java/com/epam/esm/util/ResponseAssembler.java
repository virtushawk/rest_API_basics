package com.epam.esm.util;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.controller.OrderController;
import com.epam.esm.controller.TagController;
import com.epam.esm.controller.UserController;
import com.epam.esm.dto.CertificateDTO;
import com.epam.esm.dto.OrderDTO;
import com.epam.esm.dto.TagDTO;
import com.epam.esm.dto.UserDTO;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Response assembler.
 */
@UtilityClass
public class ResponseAssembler {

    /**
     * Assemble certificates list.
     *
     * @param certificates the certificates
     * @return the list
     */
    public static List<CertificateDTO> assembleCertificates(List<CertificateDTO> certificates) {
        return certificates
                .stream()
                .map(ResponseAssembler::mapCertificate)
                .collect(Collectors.toList());
    }

    /**
     * Assemble certificate dto
     *
     * @param certificate the certificate
     * @return the certificate dto
     */
    public static CertificateDTO assembleCertificate(CertificateDTO certificate) {
        return mapCertificate(certificate);
    }

    /**
     * Assemble orders
     *
     * @param orders the orders
     * @return the list
     */
    public static List<OrderDTO> assembleOrders(List<OrderDTO> orders) {
        return orders
                .stream()
                .map(ResponseAssembler::mapOrder)
                .collect(Collectors.toList());
    }

    /**
     * Assemble order dto
     *
     * @param orderDTO the order dto
     * @return the order dto
     */
    public static OrderDTO assembleOrder(OrderDTO orderDTO) {
        return mapOrder(orderDTO);
    }

    /**
     * Assemble tags list.
     *
     * @param tags the tags
     * @return the list
     */
    public static List<TagDTO> assembleTags(List<TagDTO> tags) {
        return tags
                .stream()
                .map(ResponseAssembler::mapTag)
                .collect(Collectors.toList());
    }

    /**
     * Assemble  tag dto.
     *
     * @param tagDTO the tag dto
     * @return the tag dto
     */
    public static TagDTO assembleTag(TagDTO tagDTO) {
        return mapTag(tagDTO);
    }

    /**
     * Assemble users list.
     *
     * @param users the users
     * @return the list
     */
    public static List<UserDTO> assembleUsers(List<UserDTO> users) {
        return users
                .stream()
                .map(ResponseAssembler::mapUser)
                .collect(Collectors.toList());
    }

    /**
     * Assemble  user dto.
     *
     * @param userDTO the user dto
     * @return the user dto
     */
    public static UserDTO assembleUser(UserDTO userDTO) {
        return mapUser(userDTO);
    }

    private static CertificateDTO mapCertificate(CertificateDTO certificate) {
        if (!ObjectUtils.isEmpty(certificate.getTags())) {
            certificate.add(linkTo(methodOn(CertificateController.class).findTagsByCertificateId(certificate.getId())).withRel("tags"));
        }
        certificate.setTags(null);
        return certificate.add(linkTo(methodOn(CertificateController.class).findById(certificate.getId())).withSelfRel());
    }

    private static OrderDTO mapOrder(OrderDTO orderDTO) {
        orderDTO.add(linkTo(methodOn(OrderController.class).findById(orderDTO.getId())).withSelfRel());
        orderDTO.add(linkTo(methodOn(OrderController.class).findAllByOrderId(orderDTO.getId())).withRel("certificates"));
        orderDTO.add(linkTo(methodOn(UserController.class).findById(orderDTO.getUserId())).withRel("user"));
        orderDTO.setCertificateId(null);
        orderDTO.setUserId(null);
        return orderDTO;
    }

    private static TagDTO mapTag(TagDTO tagDTO) {
        return tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel());
    }

    private static UserDTO mapUser(UserDTO userDTO) {
        return userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
    }
}
