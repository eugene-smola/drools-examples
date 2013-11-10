package com.smolinj.drools.zoo.domain;

public class AnimalProperty {
	
	public AnimalProperty(SomeAnimal animal, boolean flying, boolean swimming, boolean running) {
		this.flying = flying;  
		this.swimming = swimming;
		this.running = running; 
		this.animal= animal;       
	}

	private boolean flying = false;
	private boolean swimming = false;
	private boolean running = false;
	private SomeAnimal animal;
	
	public boolean isFlying() {
		return flying;
	}
	public void setFlying(boolean flying) {
		this.flying = flying;
	}
	public boolean isSwimming() {
		return swimming;
	}
	public void setSwimming(boolean swimming) {
		this.swimming = swimming;
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
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
		result = prime * result + (flying ? 1231 : 1237);
		result = prime * result + (running ? 1231 : 1237);
		result = prime * result + (swimming ? 1231 : 1237);
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
		AnimalProperty other = (AnimalProperty) obj;
		if (animal == null) {
			if (other.animal != null)
				return false;
		} else if (!animal.equals(other.animal))
			return false;
		if (flying != other.flying)
			return false;
		if (running != other.running)
			return false;
		if (swimming != other.swimming)
			return false;
		return true;
	}
}