package com.example.demo.service;

import com.example.demo.domain.entity.Match;
import com.example.demo.domain.entity.Season;
import com.example.demo.dto.StandingResponse;
import com.example.demo.dto.TeamStanding;
import com.example.demo.repository.MatchRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

@Service
public class StandingService {

	private final MatchRepository matchRepository;

	public StandingService(MatchRepository matchRepository) {
		this.matchRepository = matchRepository;
	}

	@Transactional(readOnly = true)
	public StandingResponse getStanding(String season, LocalDate date) {
		Season parsedSeason = Season.parse(season);
		if (date == null) {
			throw new IllegalArgumentException("Дата таблицы обязательна");
		}

		Map<String, MutableStanding> standings = new HashMap<>();

		for (Match match : matchRepository.findBySeasonAndMatchDateLessThanEqualOrderByMatchDateAscIdAsc(parsedSeason.value(), date)) {
			MutableStanding home = standings.computeIfAbsent(match.getHomeTeam(), MutableStanding::new);
			MutableStanding away = standings.computeIfAbsent(match.getAwayTeam(), MutableStanding::new);

			home.played++;
			away.played++;

			if (match.getHomeScore() > match.getAwayScore()) {
				home.points += 3;
			}
			else if (match.getHomeScore() < match.getAwayScore()) {
				away.points += 3;
			}
			else {
				home.points++;
				away.points++;
			}
		}

		return new StandingResponse(
				parsedSeason.value(),
				date,
				standings.values().stream()
						.map(MutableStanding::toTeamStanding)
						.sorted(Comparator.comparingInt(TeamStanding::points).reversed()
								.thenComparing(TeamStanding::team))
						.toList()
		);
	}

	private static final class MutableStanding {
		private final String team;
		private int played;
		private int points;

		private MutableStanding(String team) {
			this.team = team;
		}

		private TeamStanding toTeamStanding() {
			return new TeamStanding(team, played, points);
		}
	}
}
