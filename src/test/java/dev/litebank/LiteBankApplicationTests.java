package dev.litebank;

import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
class LiteBankApplicationTests {

    @Autowired
    private HikariDataSource hikariDataSource;

	@Test
	void contextLoads() {
	}

	@Test
	void testThatConnectToTheDatabase() {
		try(HikariDataSource ds = new HikariDataSource()) {
			ds.setJdbcUrl("jdbc:postgresql://localhost:5432/lite_bank");
			ds.setUsername("postgres");
			ds.setPassword("Password");
			Connection connection = hikariDataSource.getConnection();
			System.out.println(connection);
			assertNotNull(connection);
		}catch(Exception error) {
			assertNull(error);

		}
	}
}
