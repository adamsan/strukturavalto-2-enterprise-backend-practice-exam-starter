package com.codecool.train.controller;

import com.codecool.train.dao.TrainDao;
import com.codecool.train.dao.WagonDao;
import com.codecool.train.dto.TrainDto;
import com.codecool.train.dto.TrainWagonDto;
import com.codecool.train.entity.Train;
import com.codecool.train.entity.Wagon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TrainController {
    private final TrainDao trainDao;
    private final WagonDao wagonDao;

    @PostMapping("/train")
    public void save(@RequestBody TrainDto t){
        var train = new Train(
                t.getId(),
                t.getDriver(),
                t.getDestination(),
                t.getIsLate(),
                Collections.emptyList()
        );
        trainDao.save(train);
    }
    @PostMapping("/train/addWagon")
    public void addWagon(@RequestBody TrainWagonDto trainWagonDto) {
        Train train = trainDao.findById(trainWagonDto.getTrainId()).orElseThrow();
        Wagon wagon = wagonDao.findById(trainWagonDto.getWagonId()).orElseThrow();
        wagon.setTrain(train);
        wagonDao.save(wagon);
    }

    @GetMapping("/train/{id}")
    public Train findById(@PathVariable("id") String id){
        return trainDao.findById(id).orElseThrow();
    }

    @PutMapping("/train/{id}")
    public void update(@PathVariable("id") String id, @RequestBody TrainDto t) {
        //TODO: trainDao.getReferenceById(id);
//        Train train = trainDao.findById(id).orElseThrow();
        Train train = trainDao.getReferenceById(id);
        train.setDestination(t.getDestination());
        train.setDriver(t.getDriver());
        train.setIsLate(t.getIsLate());
        trainDao.save(train);
    }

    @DeleteMapping("/train/{id}")
    public void delete(@PathVariable("id") String id){
        trainDao.deleteById(id);
    }

    @GetMapping("/train/heavy")
    public List<Train> findHeavy() {
        final int heavyWeightMinimum = 25;
        return trainDao.findAll().stream()
                .filter(t -> t.getWagons().stream().mapToInt(Wagon::getWeight).sum() > heavyWeightMinimum)
                .toList();
    }
}
