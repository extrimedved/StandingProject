package com.example.demo.controller;

import com.example.demo.dto.MatchResponse;
import com.example.demo.dto.RegisterMatchRequest;
import com.example.demo.service.MatchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/matches")
@Tag(name = "Матчи", description = "Регистрация результатов сыгранных матчей")
public class MatchController {

	private final MatchService matchService;

	public MatchController(MatchService matchService) {
		this.matchService = matchService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Зарегистрировать результат матча")
	public MatchResponse register(@Valid @RequestBody RegisterMatchRequest request) {
		return matchService.register(request);
	}
}
