
public class Supplier implements Comparable<Supplier> {

	private String vendorID;
	private String name;
	
	public Supplier(String vendorID, String name) {
		setVendorID(vendorID);
		setName(name);
	}

	public String getVendorID() {
		return vendorID;
	}

	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return String.format("Supplier(vendor-id: %s, name: %s)", vendorID, name);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((vendorID == null) ? 0 : vendorID.hashCode());
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
		Supplier other = (Supplier) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (vendorID == null) {
			if (other.vendorID != null)
				return false;
		} else if (!vendorID.equals(other.vendorID))
			return false;
		return true;
	}

	@Override
	public int compareTo(Supplier other) {
		
		// Compare on vendorID first, name second
		
		int result = vendorID.compareTo(other.vendorID);
		if (result != 0) {
			return result;
		}
		else {
			return name.compareTo(other.name);
		}
	}

}
