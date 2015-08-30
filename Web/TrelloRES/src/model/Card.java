package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Card {
	private long id;
	private String name;
	private long list;
	private String description;
	
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
	public long getList() {
		return list;
	}
	public void setList(long list) {
		this.list = list;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
