package com.example.demo.standing;

import com.example.demo.domain.entity.Match;
import com.example.demo.dto.StandingResponse;
import com.example.demo.dto.TeamStanding;
import com.example.demo.repository.MatchRepository;
import com.example.demo.service.StandingService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StandingServiceTest {

	private final MatchRepository matchRepository = mock(MatchRepository.class);
	private final StandingService standingService = new StandingService(matchRepository);

	@Test
	void calculatesStanding() {
		LocalDate requestedDate = LocalDate.of(2025, 11, 22);
		when(matchRepository.findBySeasonAndMatchDateLessThanEqualOrderByMatchDateAscIdAsc("2025/2026", requestedDate))
				.thenReturn(List.of(
						new Match("2025/2026", LocalDate.of(2025, 10, 26), "Barcelona", "Real Madrid", 3, 1),
						new Match("2025/2026", LocalDate.of(2025, 11, 2), "Real Madrid", "Sevilla", 1, 1),
						new Match("2025/2026", LocalDate.of(2025, 11, 22), "Sevilla", "Barcelona", 2, 0)
				));

		StandingResponse response = standingService.getStanding("2025/2026", requestedDate);

		assertThat(response.season()).isEqualTo("2025/2026");
		assertThat(response.date()).isEqualTo(requestedDate);
		assertThat(response.teams()).containsExactly(
				new TeamStanding("Sevilla", 2, 4),
				new TeamStanding("Barcelona", 2, 3),
				new TeamStanding("Real Madrid", 2, 1)
		);
	}

	@Test
	void awardsDrawPoints() {
		LocalDate requestedDate = LocalDate.of(2025, 12, 14);
		when(matchRepository.findBySeasonAndMatchDateLessThanEqualOrderByMatchDateAscIdAsc("2025/2026", requestedDate))
				.thenReturn(List.of(new Match("2025/2026", requestedDate, "Valencia", "Villarreal", 0, 0)));

		StandingResponse response = standingService.getStanding("2025/2026", requestedDate);

		assertThat(response.teams()).containsExactly(
				new TeamStanding("Valencia", 1, 1),
				new TeamStanding("Villarreal", 1, 1)
		);
	}

	@Test
	void rejectsBadSeason() {
		assertThatThrownBy(() -> standingService.getStanding("2025", LocalDate.of(2025, 9, 21)))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Сезон должен быть в формате YYYY/YYYY");
	}
}
