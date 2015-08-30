 package model;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;
 
 @XmlRootElement
public class List {
	private long id;
	private String name;
	private long board;
	
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
	public long getBoard() {
		return board;
	}
	public void setBoard(long board) {
		this.board = board;
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
