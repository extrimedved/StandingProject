package com.example.demo.migration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = "spring.jpa.hibernate.ddl-auto=none")
class FlywayMigrationTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	void createsTable() {
		Integer tableCount = jdbcTemplate.queryForObject(
				"SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME = 'MATCHES'",
				Integer.class
		);

		assertThat(tableCount).isEqualTo(1);
	}
}
