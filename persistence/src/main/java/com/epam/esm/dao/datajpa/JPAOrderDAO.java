package com.epam.esm.dao.datajpa;

import com.epam.esm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JPAOrderDAO extends JpaRepository<Order, Long> {
}
