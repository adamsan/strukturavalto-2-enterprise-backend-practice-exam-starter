package com.codecool.train.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainDto {
    String id;
    String driver;
    String destination;
    Boolean isLate;
}
