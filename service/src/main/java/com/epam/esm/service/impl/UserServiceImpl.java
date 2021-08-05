package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    public final UserDAO userDAO;
    public final MapperDTO mapperDTO;

    @Override
    public List<UserDTO> findAll(Page page) {
        Pageable pageRequest = PageRequest.of(page.getPage(), page.getSize());
        return userDAO.findAll(pageRequest)
                .stream()
                .map(mapperDTO::convertUserToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        return mapperDTO.convertUserToDTO(userDAO.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id.toString())));
    }

    @Override
    @Transactional
    public UserDTO create(UserDTO userDTO) {
        Optional<User> optional = userDAO.findByName(userDTO.getName());
        if (optional.isEmpty()) {
            return mapperDTO.convertUserToDTO(userDAO.create(mapperDTO.convertDTOToUser(userDTO)));
        }
        return mapperDTO.convertUserToDTO(optional.get());
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("method not allowed");
    }
}
