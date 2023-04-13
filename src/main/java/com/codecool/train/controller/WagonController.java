package com.codecool.train.controller;

import com.codecool.train.dao.WagonDao;
import com.codecool.train.dto.WagonDto;
import com.codecool.train.entity.Wagon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class WagonController {
    private final WagonDao wagonDao;

    @GetMapping("/wagon")
    public List<Wagon> findAll() {
        return wagonDao.findAll();
    }

    @PostMapping("/wagon")
    public void save(@RequestBody WagonDto wagonDto) {
        var wagon = new Wagon(
                wagonDto.getId(),
                wagonDto.getWeight(),
                wagonDto.getWagonType(),
                null
        );
        wagonDao.save(wagon);
    }

    @PutMapping("/wagon/{id}")
    public void update(@PathVariable("id") String id, @RequestBody WagonDto w) {
        var wagon = wagonDao.findById(id).orElseThrow();
        wagon.setWagonType(w.getWagonType());
        wagon.setWeight(w.getWeight());
        wagonDao.save(wagon);
    }

    @DeleteMapping("/wagon/{id}")
    public void delete(@PathVariable("id") String id) {
        wagonDao.deleteById(id);
    }
}
