package it.polito.tdp.newufosightings.model;

import java.time.LocalDateTime;

public class Event implements Comparable<Event>{
	
	public enum EventType {
		AVVISTAMENTO, INCREMENTO
	}
	
	private EventType tipo;
	private LocalDateTime time;
	private State state;
	private float incremento;
	
	public Event(EventType tipo, LocalDateTime time, State state) {
		this.tipo = tipo;
		this.time = time;
		this.state = state;
	}
	
	public Event(EventType tipo, LocalDateTime time, State state, float incremento) {
		this.tipo = tipo;
		this.time = time;
		this.state = state;
		this.incremento = incremento;
	}

	public EventType getTipo() {
		return tipo;
	}

	public void setTipo(EventType tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public float getIncremento() {
		return incremento;
	}

	public void setIncremento(float incremento) {
		this.incremento = incremento;
	}

	@Override
	public int compareTo(Event o) {
		return this.time.compareTo(o.getTime());
	}
}
