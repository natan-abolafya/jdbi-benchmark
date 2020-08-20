package jdbi.benchmark.dao;


import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SimpleObjectMapper implements RowMapper<SimpleObject> {
	@Override
	public SimpleObject map(ResultSet rs, StatementContext ctx) throws SQLException {
		return new SimpleObject(rs.getInt("id"), rs.getString("name"));
	}
}
