
public class Part implements Comparable<Part> {

	private int number;
	private String description;
	
	public Part(int number, String description) {
		setNumber(number);
		setDescription(description);
	}
		
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return String.format("Part(number: %d, description: %s)", number, description);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + number;
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
		Part other = (Part) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (number != other.number)
			return false;
		return true;
	}

	@Override
	public int compareTo(Part other) {
		
		// Compare on number first, description second
		
		if (number < other.number) {
			return -1;
		}
		else if (number > other.number) {
			return 1;
		}
		else {
			return description.compareTo(other.description);
		}
	}
	
	
}
