package com.codecool.train;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainApplicationTests {

    private final String TRAIN_URL = "/train";
    private final String WAGON_URL = "/wagon";
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllWagons() {
        Wagon[] expectedArr = {
                new Wagon("AECDBA",6, WagonType.CARGO,null),
                new Wagon("AEXCBA",3, WagonType.PASSENGER,null)
        };
        WagonDto[] wagonDtosToPost = {
                new WagonDto("AECDBA",6, WagonType.CARGO),
                new WagonDto("AEXCBA",3, WagonType.PASSENGER)
        };
        for (WagonDto wagonDto : wagonDtosToPost) {
            postWagon(WAGON_URL,wagonDto);
        }
        final ResponseEntity<Wagon[]> response = restTemplate.getForEntity(WAGON_URL, Wagon[].class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Wagon[] actualArr = response.getBody();
        assertWagonArraysHasSameElements(expectedArr,actualArr);
    }

    @Test
    void getTrain(){
        final String TRAIN_ID = "ABCD";
        WagonDto[] wagonDtosToPost = {
                new WagonDto("BECDBA",6, WagonType.CARGO),
                new WagonDto("BEXCBA",3, WagonType.PASSENGER)
        };
        for (WagonDto wagonDto : wagonDtosToPost) {
            postWagon(WAGON_URL,wagonDto);
        }
        List<Wagon> wagons = new ArrayList<>(){{
            add(new Wagon("BECDBA",6, WagonType.CARGO,null));
            add(new Wagon("BEXCBA",3, WagonType.PASSENGER,null));
        }};
        TrainDto trainDto = new TrainDto(TRAIN_ID,"Bud Spencer", "Budapest",true);
        postTrain(TRAIN_URL,trainDto);
        addWagonsToTrain(TRAIN_ID,wagons);
        Train expectedTrain = new Train(TRAIN_ID,"Bud Spencer", "Budapest",true,wagons);
        final ResponseEntity<Train> response = restTemplate.getForEntity(TRAIN_URL+"/"+TRAIN_ID, Train.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSameTrain(expectedTrain,response.getBody());
    }

    private void addWagonsToTrain(String trainId, List<Wagon> wagons){
        for (Wagon wagon : wagons) {
            restTemplate.postForEntity(TRAIN_URL+"/addWagon", new TrainWagonDto(trainId,wagon.getId()), String.class);
        }
    }

    private void postWagon(String url, WagonDto wagonDto) {
        final HttpEntity<WagonDto> httpEntity = createWagonHttpEntityWithMediatypeJson(wagonDto);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    private void postTrain(String url, TrainDto trainDto) {
        final HttpEntity<TrainDto> httpEntity = createTrainHttpEntityWithMediatypeJson(trainDto);
        restTemplate.postForEntity(url, httpEntity, String.class);
    }

    private HttpEntity<WagonDto> createWagonHttpEntityWithMediatypeJson(WagonDto wagonDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(wagonDto, headers);
    }

    private HttpEntity<TrainDto> createTrainHttpEntityWithMediatypeJson(TrainDto trainDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(trainDto, headers);
    }

    private void assertWagonArraysHasSameElements(Wagon[] expectedArr, Wagon[] actualArr){
        List<Wagon> expected = Arrays.asList(expectedArr);
        List<Wagon> actual = Arrays.asList(actualArr);
        assertWagonListsHasSameElements(expected,actual);
    }

    private void assertWagonListsHasSameElements(List<Wagon> expected, List<Wagon> actual){
        assertTrue(expected.size() == actual.size() && expected.containsAll(actual) && actual.containsAll(expected));
    }

    private void assertSameTrain(Train expected, Train actual){
        assertEquals(expected.getId(),actual.getId());
        assertEquals(expected.getDestination(),actual.getDestination());
        assertEquals(expected.getDriver(),actual.getDriver());
        assertEquals(expected.getIsLate(),actual.getIsLate());
    }

}
