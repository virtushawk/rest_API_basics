package com.epam.esm.util;

import com.epam.esm.controller.CertificateController;
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

@UtilityClass
public class ResponseAssembler {

    public static List<CertificateDTO> assembleCertificates(List<CertificateDTO> certificates) {
        return certificates.stream()
                .map(certificateDTO -> {
                    certificateDTO.add(linkTo(methodOn(CertificateController.class).findById(certificateDTO.getId())).withSelfRel());
                    certificateDTO.getTags()
                            .forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel()));
                    return certificateDTO;
                }).collect(Collectors.toList());
    }

    public static CertificateDTO assembleCertificate(CertificateDTO certificate) {
        if (!ObjectUtils.isEmpty(certificate.getTags())) {
            certificate.getTags()
                    .forEach(tagDTO -> tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel()));
        }
        return certificate.add(linkTo(methodOn(CertificateController.class).findById(certificate.getId())).withSelfRel());
    }

    public static List<OrderDTO> assembleOrders(List<OrderDTO> orders) {
        return orders.stream()
                .map(orderDTO -> {
            orderDTO.getCertificateId().forEach(id -> orderDTO.add(linkTo(methodOn(CertificateController.class).findById(id)).withRel("certificates")));
            orderDTO.add(linkTo(methodOn(UserController.class).findById(orderDTO.getUserId())).withRel("user"));
            return orderDTO;
        }).collect(Collectors.toList());
    }

    public static OrderDTO assembleOrder(OrderDTO orderDTO) {
        orderDTO.getCertificateId().forEach(id -> orderDTO.add(linkTo(methodOn(CertificateController.class).findById(id)).withRel("certificates")));
        orderDTO.add(linkTo(methodOn(UserController.class).findById(orderDTO.getUserId())).withRel("user"));
        return orderDTO;
    }

    public static List<TagDTO> assembleTags(List<TagDTO> tags) {
        return tags.stream()
                .map(tagDTO -> {
                    tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel());
                    return tagDTO;
                }).collect(Collectors.toList());
    }

    public static TagDTO assembleTag(TagDTO tagDTO) {
        return tagDTO.add(linkTo(methodOn(TagController.class).findById(tagDTO.getId())).withSelfRel());
    }

    public static List<UserDTO> assembleUsers(List<UserDTO> users) {
        return users.stream()
                .map(userDTO -> {
                    userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
                    return userDTO;
                }).collect(Collectors.toList());
    }

    public static UserDTO assembleUser(UserDTO userDTO) {
        return userDTO.add(linkTo(methodOn(UserController.class).findById(userDTO.getId())).withSelfRel());
    }
}
