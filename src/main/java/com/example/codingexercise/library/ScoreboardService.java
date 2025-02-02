package com.example.codingexercise.library;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ScoreboardService {
    private final Map<String, Match> liveMatches = new ConcurrentHashMap<>();
    private final KafkaTemplate<String, String> kafkaTemplate;

    public ScoreboardService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        kafkaTemplate.send(topic, message);
    }

    @KafkaListener(topics = "matches", groupId = "scoreboard-group")
    public void listen(String message) {
        System.out.println("Kafka Event: " + message);
    }


}
