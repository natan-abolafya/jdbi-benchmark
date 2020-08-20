package jdbi.benchmark;

import jdbi.benchmark.dao.SimpleDao;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.postgres.PostgresPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleTest {

	private static final String username = "postgres";
	private static final String password = "postgres";

	public static void main(String[] args) throws SQLException {
		var jdbi = createDatabase("test");
		var simpleDao = jdbi.onDemand(SimpleDao.class);
		System.out.println(simpleDao.getAll());
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
