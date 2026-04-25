package com.example.demo.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 50)
	private String season;

	@Column(nullable = false)
	private LocalDate matchDate;

	@Column(nullable = false, length = 100)
	private String homeTeam;

	@Column(nullable = false, length = 100)
	private String awayTeam;

	@Column(nullable = false)
	private int homeScore;

	@Column(nullable = false)
	private int awayScore;

	protected Match() {
	}

	public Match(String season, LocalDate matchDate, String homeTeam, String awayTeam, int homeScore, int awayScore) {
		this.season = season;
		this.matchDate = matchDate;
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
	}

	public Long getId() {
		return id;
	}

	public String getSeason() {
		return season;
	}

	public LocalDate getMatchDate() {
		return matchDate;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public int getHomeScore() {
		return homeScore;
	}

	public int getAwayScore() {
		return awayScore;
	}
}
