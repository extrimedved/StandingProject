package com.example.demo.mapper;

import com.example.demo.domain.entity.Match;
import com.example.demo.dto.MatchResponse;
import com.example.demo.dto.RegisterMatchRequest;
import org.springframework.stereotype.Component;

@Component
public class MatchMapper {

	public Match toEntity(RegisterMatchRequest request) {
		return new Match(
				request.season().trim(),
				request.matchDate(),
				request.homeTeam().trim(),
				request.awayTeam().trim(),
				request.homeScore(),
				request.awayScore()
		);
	}

	public MatchResponse toResponse(Match match) {
		return new MatchResponse(
				match.getId(),
				match.getSeason(),
				match.getMatchDate(),
				match.getHomeTeam(),
				match.getAwayTeam(),
				match.getHomeScore(),
				match.getAwayScore()
		);
	}
}
