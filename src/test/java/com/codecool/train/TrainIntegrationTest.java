package com.codecool.train;

import com.codecool.train.dto.TrainDto;
import com.codecool.train.dto.TrainWagonDto;
import com.codecool.train.dto.WagonDto;
import com.codecool.train.entity.Train;
import com.codecool.train.entity.WagonType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void heavyTrainTest() {
        var lightTrain = new TrainDto("ABC123", "J.D", "Vac", true);
        var heavyTrain1 = new TrainDto("ABC456", "Jane.D", "Budapest", true);
        var heavyTrain2 = new TrainDto("ABC789", "Smith.D", "New York", true);

        var lightWagon = new WagonDto("L1", 10, WagonType.CARGO);
        var heavyWagon = new WagonDto("L2", 50, WagonType.CARGO);
        var mediumWagon1 = new WagonDto("L3", 20, WagonType.CARGO);
        var mediumWagon2 = new WagonDto("L4", 20, WagonType.CARGO);


        postTrain("/train", lightTrain);
        postTrain("/train", heavyTrain1);
        postTrain("/train", heavyTrain2);

        final ResponseEntity<Train> response = restTemplate.getForEntity("/train/" + lightTrain.getId(), Train.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(lightTrain.getDestination(), Objects.requireNonNull(response.getBody()).getDestination());

        postWagon("/wagon", lightWagon);
        postWagon("/wagon", heavyWagon);
        postWagon("/wagon", mediumWagon1);
        postWagon("/wagon", mediumWagon2);

        addWagon(lightTrain, lightWagon);
        addWagon(heavyTrain1, heavyWagon);
        addWagon(heavyTrain2, mediumWagon1);
        addWagon(heavyTrain2, mediumWagon2);


        Train[] trains = restTemplate.getForObject("/train/heavy", Train[].class);
        assertEquals(2, trains.length);

        List<String> actual = Stream.of(trains).map(Train::getDestination).toList();
        List<String> expected = List.of(heavyTrain1.getDestination(), heavyTrain2.getDestination());

        assertTrue(actual.containsAll(expected));
    }

    private void addWagon(TrainDto lightTrain, WagonDto lightWagon) {
        TrainWagonDto tw = new TrainWagonDto(lightTrain.getId(), lightWagon.getId());
        HttpEntity<Object> httpEntity = createWagonHttpEntityWithMediatypeJson(tw);
        restTemplate.postForEntity("/train/addWagon", httpEntity, String.class);
    }

    private void postTrain(String url, TrainDto trainDto) {
        final HttpEntity<Object> httpEntity = createWagonHttpEntityWithMediatypeJson(trainDto);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    private void postWagon(String url, Object wagonDto) {
        final HttpEntity<Object> httpEntity = createWagonHttpEntityWithMediatypeJson(wagonDto);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<Object> createWagonHttpEntityWithMediatypeJson(Object wagonDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(wagonDto, headers);
    }
}
