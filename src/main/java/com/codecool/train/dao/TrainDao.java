package com.codecool.train.dao;

import com.codecool.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainDao extends JpaRepository<Train, String> {
}
