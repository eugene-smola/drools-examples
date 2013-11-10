package com.smolinj.drools.zoo.domain;

public class SomeAnimal {

	private static final int SIGNS_COUNT = 10;
	private String specie;
	private String name;

	private String[] signs = new String[SIGNS_COUNT];

	public void addSign(String newSign) {
		int i = 0;
		for (i = 0; i < SIGNS_COUNT; i++) {
			if (signs[i] == null || signs[i] == "") {
				signs[i] = newSign;
				break;
			}
		}
		if (i == SIGNS_COUNT) {
			throw new IllegalStateException("No more space for new sign");
		}
	}
	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}
	public String[] getSigns() {
		return signs;
	}
	public void setSigns(String[] signs) {
		this.signs = signs;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((specie == null) ? 0 : specie.hashCode());
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
		SomeAnimal other = (SomeAnimal) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (specie == null) {
			if (other.specie != null)
				return false;
		} else if (!specie.equals(other.specie))
			return false;
		return true;
	}
}