package ar.uba.fi.fiubappREST.domain;

import org.codehaus.jackson.annotate.JsonCreator;
import org.codehaus.jackson.annotate.JsonValue;

public enum GroupState {

	ENABLED(0, "Habilitado"), SUSPENDED(1, "Suspendido");

	private int id;
	private String name;

	GroupState(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonValue
	public String getName() {
		return name;
	}

	@JsonCreator
	public static GroupState create(String value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		for (GroupState name : values()) {
			if (value.equals(name.getName())) {
				return name;
			}
		}
		throw new IllegalArgumentException();
	}
	
	@Override
	public String toString() {
		return name;
	}
}
