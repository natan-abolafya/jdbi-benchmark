package jdbi.benchmark.dao;

import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

public interface SimpleDao {

	@SqlQuery("SELECT name FROM simple")
	List<String> getAll();
}
