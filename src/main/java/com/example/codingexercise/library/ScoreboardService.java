package com.example.codingexercise.library;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ScoreboardService {
    private final Map<String, Match> liveMatches = new ConcurrentHashMap<>();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ScoreboardService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @KafkaListener(topics = "matches", groupId = "scoreboard-group")
    public void listen(String message) {
        System.out.println("Kafka message: " + message);
    }

    public void startMatch(String homeTeam, String awayTeam) {
        String matchId = homeTeam + " vs " + awayTeam;
        liveMatches.putIfAbsent(matchId, new Match(homeTeam, awayTeam));
        kafkaTemplate.send("matches", "Match Started: " + matchId);
    }

    public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
        String matchId = homeTeam + " vs " + awayTeam;
        if (liveMatches.containsKey(matchId) && homeScore >= 0 && awayScore >= 0) {
            liveMatches.get(matchId).updateScore(homeScore, awayScore);
            kafkaTemplate.send("matches", "Score Updated: " + matchId + " -> " + homeScore + " - " + awayScore);
        }
    }

    public void finishMatch(String homeTeam, String awayTeam) {
        String matchId = homeTeam + " vs " + awayTeam;
        liveMatches.remove(matchId);
        kafkaTemplate.send("matches", "Match Finished: " + matchId);
    }

    public List<Match> getSummary() {
        return liveMatches.values().stream()
                .sorted(Comparator.comparingInt(Match::getTotalScore).reversed()
                        .thenComparing(Comparator.comparingLong(Match::getStartTime).reversed()))
                .toList();
    }


}
