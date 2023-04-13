package com.codecool.train.dto;

import com.codecool.train.entity.WagonType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WagonDto {
    String id;
    Integer weight;
    WagonType wagonType;
}
