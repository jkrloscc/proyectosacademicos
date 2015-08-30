package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Board {
	private long id;
	private String name;
	private long owner;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getOwner() {
		return owner;
	}

	public void setOwner(long owner) {
		this.owner = owner;
	}

	public boolean validate(Map<String, String> messages) {

		messages.put("error", "Error: invalid name");

		if (name == null)
			return false;
		if (name.trim().isEmpty())
			return false;

		return true;
	}

}
