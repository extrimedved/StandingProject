package com.example.demo.service;

import com.example.demo.domain.entity.Match;
import com.example.demo.domain.entity.Season;
import com.example.demo.dto.MatchResponse;
import com.example.demo.dto.RegisterMatchRequest;
import com.example.demo.mapper.MatchMapper;
import com.example.demo.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchService {

	private final MatchRepository matchRepository;
	private final MatchMapper matchMapper;

	public MatchService(MatchRepository matchRepository, MatchMapper matchMapper) {
		this.matchRepository = matchRepository;
		this.matchMapper = matchMapper;
	}

	@Transactional
	public MatchResponse register(RegisterMatchRequest request) {
		Season season = Season.parse(request.season());
		season.validateMatchDate(request.matchDate());

		if (request.homeTeam().trim().equalsIgnoreCase(request.awayTeam().trim())) {
			throw new IllegalArgumentException("Команды хозяев и гостей должны отличаться");
		}

		Match match = matchMapper.toEntity(request);
		return matchMapper.toResponse(matchRepository.save(match));
	}
}
