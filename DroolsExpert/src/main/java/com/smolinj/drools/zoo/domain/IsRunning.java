package com.smolinj.drools.zoo.domain;

public class IsRunning {
	private SomeAnimal animal;
	public IsRunning(SomeAnimal animal) {
		this.animal = animal;
	}
	public SomeAnimal getAnimal() {
		return animal;
	}
	public void setAnimal(SomeAnimal animal) {
		this.animal = animal;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((animal == null) ? 0 : animal.hashCode());
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
		IsRunning other = (IsRunning) obj;
		if (animal == null) {
			if (other.animal != null)
				return false;
		} else if (!animal.equals(other.animal))
			return false;
		return true;
	}
	
}