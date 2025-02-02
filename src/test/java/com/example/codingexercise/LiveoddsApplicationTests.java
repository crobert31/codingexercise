package com.example.codingexercise;

import com.example.codingexercise.library.Match;
import com.example.codingexercise.library.ScoreboardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;


import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LiveoddsApplicationTests {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private ScoreboardService scoreboardService;

    @Test
    void contextLoads() {
    }

    @Test
    void startMatchTest() {

        //check results and summary
        //assertEquals(1,);
        //assertEquals("Home 0 - Away 0", );
    }

    @Test
    void endMatchTest() {
        //start match and finish it check if its finished
        //assertTrue();
    }

    @Test
    void updateScoreTest() {
        //update score and check new results
       // assertEquals("Home 3 - Away 2", );

    }

    @Test
    void startMatchWithSameTeamsTest() {
        //start match with same teamnames - expected only 1 match to be started
        //assertEquals(1, );
    }

    @Test
    void endMatchNonExistentTeamsTest() {
        //end match that hasn't started
        //assertTrue();

    }
    @Test
    void negativeScoreUpdate() {
        //update score with negative numbers
    }

    @Test
    void matchesSummaryOrderTest() {
        //return matches in order summary test
    }



}
