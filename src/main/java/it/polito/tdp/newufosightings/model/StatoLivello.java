package it.polito.tdp.newufosightings.model;

public class StatoLivello {
	
	private State s;
	private float livello;
	
	public StatoLivello(State s, float livello) {
		this.s = s;
		this.livello = livello;
	}

	public State getS() {
		return s;
	}

	public void setS(State s) {
		this.s = s;
	}

	public float getLivello() {
		return livello;
	}

	public void setLivello(float livello) {
		this.livello = livello;
	}
	
	public void incrementa(float f) {
		this.livello += f;
	}
	
	public void decrementa(float f) {
		this.livello -= f;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(livello);
		result = prime * result + ((s == null) ? 0 : s.hashCode());
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
		StatoLivello other = (StatoLivello) obj;
		if (Float.floatToIntBits(livello) != Float.floatToIntBits(other.livello))
			return false;
		if (s == null) {
			if (other.s != null)
				return false;
		} else if (!s.equals(other.s))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return s + ", " + livello;
	}
}