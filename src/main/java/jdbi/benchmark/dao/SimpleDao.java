package jdbi.benchmark.dao;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.transaction.Transactional;

import java.util.List;

@RegisterRowMapper(SimpleObjectMapper.class)
public interface SimpleDao extends Transactional<SimpleDao> {

	@SqlQuery("SELECT * FROM simple")
	List<SimpleObject> getAll();
}
