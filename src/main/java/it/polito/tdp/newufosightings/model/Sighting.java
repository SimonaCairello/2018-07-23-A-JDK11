package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Sighting {
	
	private int id;
	private LocalDateTime datetime;
	private String state;
	private String shape;

	public Sighting(int id, LocalDateTime datetime, String state, String shape) {
		this.id = id;
		this.datetime = datetime;
		this.state = state;
		this.shape = shape;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDateTime getDatetime() {
		return datetime;
	}

	public void setDatetime(LocalDateTime datetime) {
		this.datetime = datetime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sighting other = (Sighting) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("Sighting [datetime=%s, city=%s, state=%s, country=%s, shape=%s, duration=%s]", datetime,
				state, shape);
	}
}
