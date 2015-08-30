package com.example.monitortrafico;

public class AppItem {

	private String name;
	private long bytesTransmitidos;

	public AppItem(String name, long bytesTransmitidos) {
		super();
		this.name = name;
		this.bytesTransmitidos = bytesTransmitidos;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBytesTransmitidos() {
		return bytesTransmitidos;
	}

	public void setBytesTransmitidos(long bytesTransmitidos) {
		this.bytesTransmitidos = bytesTransmitidos;
	}

}
