package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAUserDAO extends JpaRepository<User, Long> {

}
