package jdbi.benchmark;

import com.google.common.base.Stopwatch;
import jdbi.benchmark.dao.SimpleDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class SimpleTest {

	private static final String username = "postgres";
	private static final String password = "postgres";

	public static void main(String[] args) throws SQLException {
		var jdbi = createDatabase("test");
		var simpleDao = jdbi.onDemand(SimpleDao.class);
		simpleDao.getAll(); // warm-up

		int count = 100;
		var stopwatch = Stopwatch.createStarted();
		for (int i = 0; i < count; i++) {
			simpleDao.getAll();
		}
		System.out.println("Average: " + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / count) + " ms");
	}

	public static Jdbi createDatabase(String databaseName) throws SQLException {
		var connection = DriverManager.getConnection("jdbc:postgresql://localhost/", username, password);
		connection.prepareStatement("DROP DATABASE IF EXISTS " + databaseName +
				"; CREATE DATABASE " + databaseName).execute();
		connection.close();

		var jdbi = Jdbi.create(() -> DriverManager.getConnection("jdbc:postgresql://localhost/" + databaseName, username, password));
		jdbi.installPlugin(new PostgresPlugin()).installPlugin(new SqlObjectPlugin());
		try (var handle = jdbi.open()) {
			try (var update = handle.createUpdate("CREATE TABLE simple ( name text ); " +
					"INSERT INTO simple(name) VALUES('name'), ('name2'), ('name3')")) {
				update.execute();
			}
		}

		return jdbi;
	}
}
