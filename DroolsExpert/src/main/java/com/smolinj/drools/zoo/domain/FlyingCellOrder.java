package com.smolinj.drools.zoo.domain;

public class FlyingCellOrder {
	private SomeAnimal animal;
	
	public FlyingCellOrder(SomeAnimal a) {
		this.animal = a;
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
		FlyingCellOrder other = (FlyingCellOrder) obj;
		if (animal == null) {
			if (other.animal != null)
				return false;
		} else if (!animal.equals(other.animal))
			return false;
		return true;
	}
	
	
}