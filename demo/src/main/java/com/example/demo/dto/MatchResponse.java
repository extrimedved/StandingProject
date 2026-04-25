package com.example.demo.dto;

import java.time.LocalDate;

public record MatchResponse(Long id, String season, LocalDate matchDate, String homeTeam, String awayTeam, int homeScore, int awayScore) {}
