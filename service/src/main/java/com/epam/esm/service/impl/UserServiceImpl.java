package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.service.UserService;
import com.epam.esm.util.MapperDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type User service.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    /**
     * The User dao.
     */
    public final UserDAO userDAO;
    /**
     * The Mapper dto.
     */
    public final MapperDTO mapperDTO;

    @Override
    public List<UserDTO> findAll() {
        return userDAO.findAll().stream().map(mapperDTO::convertUserToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findById(Long id) {
        Optional<User> user = userDAO.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException(id.toString());
        }
        return mapperDTO.convertUserToDTO(user.get());
    }

    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }
}
