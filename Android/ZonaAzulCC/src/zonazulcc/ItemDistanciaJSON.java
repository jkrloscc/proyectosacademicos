package zonazulcc;

public class ItemDistanciaJSON {

	private String duration;
	private String distance;
	private int durationV;
	private int distanceV;
	private int posicion;
	

	public ItemDistanciaJSON(String duration, String distance, int durationV,
			int distanceV, int posicion) {
		super();
		this.duration = duration;
		this.distance = distance;
		this.durationV = durationV;
		this.distanceV = distanceV;
		this.posicion = posicion;
	}
	
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public int getDurationV() {
		return durationV;
	}
	public void setDurationV(int durationV) {
		this.durationV = durationV;
	}
	public int getDistanceV() {
		return distanceV;
	}
	public void setDistanceV(int distanceV) {
		this.distanceV = distanceV;
	}
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}



}
