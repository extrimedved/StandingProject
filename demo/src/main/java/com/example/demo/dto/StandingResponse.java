package com.example.demo.dto;

import java.time.LocalDate;
import java.util.List;

public record StandingResponse(String season, LocalDate date, List<TeamStanding> teams) {}
