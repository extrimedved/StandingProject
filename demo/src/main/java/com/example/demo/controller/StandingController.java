package com.example.demo.controller;

import com.example.demo.dto.StandingResponse;
import com.example.demo.service.StandingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Validated
@RestController
@RequestMapping("/api/standings")
@Tag(name = "Турнирная таблица", description = "Получение турнирной таблицы чемпионата")
public class StandingController {

	private final StandingService standingService;

	public StandingController(StandingService standingService) {
		this.standingService = standingService;
	}

	@GetMapping
	@Operation(summary = "Получить турнирную таблицу по сезону и дате")
	public StandingResponse getStanding(
			@RequestParam("season") @NotBlank String season,
			@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
		return standingService.getStanding(season.trim(), date);
	}
}
