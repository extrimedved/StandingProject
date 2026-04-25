package com.example.demo.domain.entity;

import java.time.LocalDate;

public record Season(String value, int startYear, int endYear) {

	public static Season parse(String value) {
		if (value == null) {
			throw new IllegalArgumentException("Сезон обязателен");
		}

		String normalizedValue = value.trim();
		if (normalizedValue.length() != 9 || normalizedValue.indexOf('/') != 4 || normalizedValue.lastIndexOf('/') != 4) {
			throw new IllegalArgumentException("Сезон должен быть в формате YYYY/YYYY");
		}

		String startPart = normalizedValue.substring(0, 4);
		String endPart = normalizedValue.substring(5, 9);
		if (!isYear(startPart) || !isYear(endPart)) {
			throw new IllegalArgumentException("Сезон должен быть в формате YYYY/YYYY");
		}

		int startYear = Integer.parseInt(startPart);
		int endYear = Integer.parseInt(endPart);
		if (endYear != startYear + 1) {
			throw new IllegalArgumentException("Год окончания сезона должен быть на 1 больше года начала");
		}

		return new Season(normalizedValue, startYear, endYear);
	}

	public void validateMatchDate(LocalDate date) {
		if (date == null) {
			throw new IllegalArgumentException("Дата матча обязательна");
		}

		if (date.isBefore(startDate()) || date.isAfter(endDate())) {
			throw new IllegalArgumentException("Дата матча должна входить в диапазон сезона");
		}
	}

	private static boolean isYear(String value) {
		for (int i = 0; i < value.length(); i++) {
			if (!Character.isDigit(value.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	public LocalDate startDate() {
		return LocalDate.of(startYear, 7, 1);
	}

	public LocalDate endDate() {
		return LocalDate.of(endYear, 6, 30);
	}
}
