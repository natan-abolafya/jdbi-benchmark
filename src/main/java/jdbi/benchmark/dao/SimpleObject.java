package jdbi.benchmark.dao;

public class SimpleObject {

	private final int id;
	private final String name;

	public SimpleObject(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}
