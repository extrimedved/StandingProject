package com.example.demo.match;

import com.example.demo.domain.entity.Match;
import com.example.demo.dto.MatchResponse;
import com.example.demo.dto.RegisterMatchRequest;
import com.example.demo.mapper.MatchMapper;
import com.example.demo.repository.MatchRepository;
import com.example.demo.service.MatchService;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MatchServiceTest {

	private final MatchRepository matchRepository = mock(MatchRepository.class);
	private final MatchService matchService = new MatchService(matchRepository, new MatchMapper());

	@Test
	void registersMatch() {
		when(matchRepository.save(any(Match.class))).thenAnswer(invocation -> invocation.getArgument(0));

		MatchResponse response = matchService.register(new RegisterMatchRequest(
				" 2025/2026 ",
				LocalDate.of(2025, 10, 26),
				"Barcelona",
				"Real Madrid",
				3,
				1
		));

		assertThat(response.season()).isEqualTo("2025/2026");
		assertThat(response.homeTeam()).isEqualTo("Barcelona");
		assertThat(response.awayTeam()).isEqualTo("Real Madrid");
		assertThat(response.homeScore()).isEqualTo(3);
		assertThat(response.awayScore()).isEqualTo(1);
	}

	@Test
	void rejectsSameTeam() {
		RegisterMatchRequest request = new RegisterMatchRequest(
				"2025/2026",
				LocalDate.of(2026, 3, 15),
				"Barcelona",
				"barcelona",
				2,
				1
		);

		assertThatThrownBy(() -> matchService.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Команды хозяев и гостей должны отличаться");
	}

	@Test
	void rejectsReversedSeason() {
		RegisterMatchRequest request = new RegisterMatchRequest(
				"2021/2020",
				LocalDate.of(2021, 9, 12),
				"Sevilla",
				"Valencia",
				2,
				1
		);

		assertThatThrownBy(() -> matchService.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Год окончания сезона должен быть на 1 больше года начала");
	}

	@Test
	void rejectsSkippedSeason() {
		RegisterMatchRequest request = new RegisterMatchRequest(
				"2021/2023",
				LocalDate.of(2021, 11, 7),
				"Atletico Madrid",
				"Villarreal",
				2,
				1
		);

		assertThatThrownBy(() -> matchService.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Год окончания сезона должен быть на 1 больше года начала");
	}

	@Test
	void rejectsDateOutsideSeason() {
		RegisterMatchRequest request = new RegisterMatchRequest(
				"2021/2022",
				LocalDate.of(2023, 4, 23),
				"Real Betis",
				"Athletic Bilbao",
				2,
				1
		);

		assertThatThrownBy(() -> matchService.register(request))
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessage("Дата матча должна входить в диапазон сезона");
	}
}
