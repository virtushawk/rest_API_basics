package com.epam.esm.service;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.dto.UserDTO;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.UserNotFoundException;
import com.epam.esm.service.impl.UserServiceImpl;
import com.epam.esm.util.MapperDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDAO userDAO;

    @Mock
    MapperDTO mapperDTO;

    private User user;
    private Pageable pageable;

    @BeforeEach
    public void initEach() {
        user = User.builder().name("Roman").build();
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void findALlValid() {
        List<User> users = new ArrayList<>();
        users.add(user);
        List<UserDTO> expected = users.stream().map(mapperDTO::convertUserToDTO).collect(Collectors.toList());
        Mockito.when(userDAO.findAll(pageable)).thenReturn(users);
        List<UserDTO> actual = userService.findAll(new Page());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findAllEmpty() {
        List<User> users = new ArrayList<>();
        List<UserDTO> expected = new ArrayList<>();
        Mockito.when(userDAO.findAll(pageable)).thenReturn(users);
        List<UserDTO> actual = userService.findAll(new Page());
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void findByIdValid() {
        Optional<User> optionalUser = Optional.of(user);
        Long id = 1L;
        UserDTO expected = mapperDTO.convertUserToDTO(user);
        Mockito.when(userDAO.findById(id)).thenReturn(optionalUser);
        UserDTO actual = userService.findById(id);
        Assertions.assertEquals(expected, actual);
    }

    @Test()
    void findByIdException() {
        Long id = 1L;
        Mockito.when(userDAO.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById(id));
    }

}
