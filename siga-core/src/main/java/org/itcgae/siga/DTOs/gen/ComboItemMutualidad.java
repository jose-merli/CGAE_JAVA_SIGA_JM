package org.itcgae.siga.DTOs.gen;

public class ComboItemMutualidad {
	
	private String label;
	private int value;
	  
	  
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + value;
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
		ComboItemMutualidad other = (ComboItemMutualidad) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ComboItemMutualidad [label=" + label + ", value=" + value + "]";
	}
	  
	
	

}
