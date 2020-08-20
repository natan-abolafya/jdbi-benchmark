package jdbi.benchmark;

import com.google.common.base.Stopwatch;
import jdbi.benchmark.dao.SimpleDao;

import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static jdbi.benchmark.SimpleTest.createDatabase;

public class ConcurrencyTest {

	private static final String username = "postgres";
	private static final String password = "postgres";

	public static void main(String[] args) throws SQLException, InterruptedException {
		var jdbi = createDatabase("test");
		var simpleDao = jdbi.onDemand(SimpleDao.class);
		simpleDao.getAll(); // warm-up

		var executorService = Executors.newFixedThreadPool(20);
		int count = 100;
		var stopwatch = Stopwatch.createStarted();
		for (int i = 0; i < count; i++) {
			executorService.execute(simpleDao::getAll);
		}

		executorService.shutdown();
		executorService.awaitTermination(3, TimeUnit.MINUTES);
		System.out.println("Average: " + (stopwatch.elapsed(TimeUnit.MILLISECONDS) / count) + " ms");
	}
}
