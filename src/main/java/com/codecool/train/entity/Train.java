package com.codecool.train.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Train {
    @Id
    String id;
    String driver;
    String destination;
    Boolean isLate;

    @JsonIgnore
    @OneToMany(mappedBy = "train")
    List<Wagon> wagons;
}
