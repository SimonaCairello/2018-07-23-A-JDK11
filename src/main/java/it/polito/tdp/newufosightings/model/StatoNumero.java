package it.polito.tdp.newufosightings.model;

public class StatoNumero {
	
	private State s;
	private Integer num;
	
	public StatoNumero(State s, Integer num) {
		this.s = s;
		this.num = num;
	}

	public State getS() {
		return s;
	}

	public void setS(State s) {
		this.s = s;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((num == null) ? 0 : num.hashCode());
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
		StatoNumero other = (StatoNumero) obj;
		if (num == null) {
			if (other.num != null)
				return false;
		} else if (!num.equals(other.num))
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
		return s + ", " + num;
	}

}
