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


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
class LiveoddsApplicationTests {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @InjectMocks
    private ScoreboardService scoreboardService;


    @Test
    void startMatchTest() {
        //check results and summary
        scoreboardService.startMatch("Slovenia", "Croatia");
        List<Match> summary = scoreboardService.getSummary();
        assertEquals(1, summary.size());
        assertEquals("Slovenia 0 - Croatia 0", summary.get(0).toString());  //check results and summary

    }

    @Test
    void endMatchTest() {
        //start match and finish it check if its finished
        scoreboardService.startMatch("Germany", "Finland");
        scoreboardService.finishMatch("Germany", "Finland");
        List<Match> summary = scoreboardService.getSummary();
        assertTrue(summary.isEmpty());
    }

    @Test
    void updateScoreTest() {
        //update score and check new results
        scoreboardService.startMatch("Slovenia", "Slovakia");
        scoreboardService.updateScore("Slovenia", "Slovakia", 3, 2);
        List<Match> summary = scoreboardService.getSummary();
        assertEquals("Slovenia 3 - Slovakia 2", summary.get(0).toString());
    }

    @Test
    void startMatchWithSameTeamsTest() {
        //start match with same teamnames - expected only 1 match to be started
        scoreboardService.startMatch("Croatia", "Montenegro");
        scoreboardService.startMatch("Croatia", "Montenegro");
        List<Match> summary = scoreboardService.getSummary();
        assertEquals(1, summary.size());
    }

    @Test
    void endMatchNonExistentTeamsTest() {
        //end match that hasn't started
        scoreboardService.finishMatch("Slovenia", "Italy");
        List<Match> summary = scoreboardService.getSummary();
        assertTrue(summary.isEmpty());

    }

    @Test
    void negativeScoreUpdate() {
        //update score with negative numbers
        scoreboardService.startMatch("Italy", "Austria");
        scoreboardService.updateScore("Italy", "Austria", -1, -2);
        List<Match> summary = scoreboardService.getSummary();
        assertEquals("Italy 0 - Austria 0", summary.get(0).toString());
    }

    @Test
    void matchesSummaryOrderTest() {
        //return matches in order summary test
        scoreboardService.startMatch("Mexico", "Canada");
        scoreboardService.updateScore("Mexico", "Canada", 0, 5);

        scoreboardService.startMatch("Spain", "Brazil");
        scoreboardService.updateScore("Spain", "Brazil", 10, 2);

        scoreboardService.startMatch("Germany", "France");
        scoreboardService.updateScore("Germany", "France", 2, 2);

        scoreboardService.startMatch("Uruguay", "Italy");
        scoreboardService.updateScore("Uruguay", "Italy", 6, 6);

        scoreboardService.startMatch("Argentina", "Australia");
        scoreboardService.updateScore("Argentina", "Australia", 3, 1);

        List<Match> summary = scoreboardService.getSummary();
        assertEquals("Uruguay 6 - Italy 6", summary.get(0).toString());
        assertEquals("Spain 10 - Brazil 2", summary.get(1).toString());
        assertEquals("Mexico 0 - Canada 5", summary.get(2).toString());
        assertEquals("Argentina 3 - Australia 1", summary.get(3).toString());
        assertEquals("Germany 2 - France 2", summary.get(4).toString());
    }


}
