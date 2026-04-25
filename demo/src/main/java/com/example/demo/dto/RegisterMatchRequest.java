package com.example.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record RegisterMatchRequest(@NotBlank String season, @NotNull LocalDate matchDate, @NotBlank String homeTeam, @NotBlank String awayTeam, @Min(0) int homeScore, @Min(0) int awayScore) {}
