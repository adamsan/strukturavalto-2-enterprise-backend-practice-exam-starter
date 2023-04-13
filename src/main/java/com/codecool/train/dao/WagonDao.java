package com.codecool.train.dao;

import com.codecool.train.entity.Wagon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WagonDao extends JpaRepository<Wagon, String> {
}
