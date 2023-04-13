package com.codecool.train.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Objects;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Wagon {
    @Id String id;
    Integer weight;
    WagonType wagonType;
    @ManyToOne
    Train train;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wagon wagon = (Wagon) o;
        return Objects.equals(id, wagon.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
