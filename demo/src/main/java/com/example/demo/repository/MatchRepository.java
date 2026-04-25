package com.example.demo.repository;

import com.example.demo.domain.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {

	List<Match> findBySeasonAndMatchDateLessThanEqualOrderByMatchDateAscIdAsc(String season, LocalDate date);
}
