package jdbi.benchmark.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.List;

@RegisterRowMapper(SimpleObjectMapper.class)
public interface SimpleDao {

	@SqlQuery("SELECT * FROM simple")
	List<SimpleObject> getAll();
}
