
import java.util.*;


public class PartsDatabase implements Iterable<Part> {

	private List<Part> parts;
	private List<Supplier> suppliers;
	private Map<Part, Set<Supplier>> partSuppliers;
	private Map<Supplier, Set<Part>> supplierParts;
	
	private class PartComparator_ByDescription implements Comparator<Part> {
		
		@Override
		public int compare(Part a, Part b) {
			
			// Compare on description first, number second
			
			int result = a.getDescription().compareTo(b.getDescription());
			if (result != 0) {
				return result;
			}
			else if (a.getNumber() < b.getNumber()) {
				return -1;
			}
			else if (a.getNumber() > b.getNumber()) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}
	
	private class SupplierComparator_ByName implements Comparator<Supplier> {

		@Override
		public int compare(Supplier a, Supplier b) {
			
			// Compare on name first, vendorID second
			
			int result = a.getName().compareTo(b.getName());
			if (result != 0) {
				return result;
			}
			else {
				return a.getVendorID().compareTo(b.getVendorID());
			}
		}
	}
	
	public PartsDatabase() {
		//parts = new ArrayList<Part>();
		parts = new LinkedList<Part>();
		
		//suppliers = new ArrayList<Supplier>();
		suppliers = new LinkedList<Supplier>();
		
		//partSuppliers = new HashMap<Part, Set<Supplier>>();
		partSuppliers = new TreeMap<Part, Set<Supplier>>(new PartComparator_ByDescription());
		
		//supplierParts = new HashMap<Supplier, Set<Part>>();
		supplierParts = new TreeMap<Supplier, Set<Part>>(new SupplierComparator_ByName());
	}
	
	public void addPart(Part part) {
		if (!parts.contains(part)) {
			parts.add(part);
		}
		
		if (!partSuppliers.containsKey(part)) {
			//partSuppliers.put(part, new HashSet<Supplier>());
			partSuppliers.put(part, new TreeSet<Supplier>(new SupplierComparator_ByName()));
		}
	}
	
	public Collection<Part> getParts() {
		return parts;
	}
	
	public Collection<Part> getPartsByNumber() {
		ArrayList<Part> result = new ArrayList<Part>(parts);
		Collections.sort(result);
		return result;
	}
	
	public Collection<Part> getPartsByDescription() {
		ArrayList<Part> result = new ArrayList<Part>(parts);
		Collections.sort(result, new PartComparator_ByDescription());
		return result;
	}
	
	public void addSupplier(Supplier supplier) {
		if (!suppliers.contains(supplier)) {
			suppliers.add(supplier);
		}
		
		if (!supplierParts.containsKey(supplier)) {
			//supplierParts.put(supplier, new HashSet<Part>());
			supplierParts.put(supplier, new TreeSet<Part>(new PartComparator_ByDescription()));
		}
	}
	
	public Collection<Supplier> getSuppliers() {
		return suppliers;
	}
	
	public Collection<Supplier> getSuppliersByVendorID() {
		ArrayList<Supplier> result = new ArrayList<Supplier>(suppliers);
		Collections.sort(result);
		return result;
	}
	
	public Collection<Supplier> getSuppliersByName() {
		ArrayList<Supplier> result = new ArrayList<Supplier>(suppliers);
		Collections.sort(result, new SupplierComparator_ByName());
		return result;
	}
	
	public void addPartSupplier(Part part, Supplier supplier) {
		partSuppliers.get(part).add(supplier);
		supplierParts.get(supplier).add(part);
	}
	
	public Collection<Supplier> getPartSuppliers(Part part) {
		return partSuppliers.get(part);
	}
	
	public Collection<Part> getSupplierParts(Supplier supplier) {
		return supplierParts.get(supplier);
	}
	
	public void changePartDescription(Part part, String newDesc) {
		// Get suppliers for the part
		Set<Supplier> suppliers = partSuppliers.get(part);
		
		// Remove part from partSuppliers map
		partSuppliers.remove(part);
		
		// Remove the part from supplierParts map
		for (Supplier supplier : suppliers) {
			supplierParts.get(supplier).remove(part);
		}
		
		// Change part description
		part.setDescription(newDesc);
		
		// Insert part into partSuppliers map
		partSuppliers.put(part, suppliers);
		
		// Insert part into supplierParts map
		for (Supplier supplier : suppliers) {
			supplierParts.get(supplier).add(part);
		}
	}
	
	public void changeSupplierName(Supplier supplier, String newName) {
		// Get parts for the supplier
		Set<Part> parts = supplierParts.get(supplier);
		
		// Remove supplier from supplierParts map
		supplierParts.remove(supplier);
		
		// Remove supplier from partSuppliers map
		for (Part part : parts) {
			partSuppliers.get(part).remove(supplier);
		}
		
		// Change supplier name
		supplier.setName(newName);
		
		// Insert supplier into supplierParts map
		supplierParts.put(supplier, parts);
		
		// Insert supplier into partSuppliers map
		for (Part part : parts) {
			partSuppliers.get(part).add(supplier);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append("PARTS:\n");
		for (Part part : getParts()) {
			result.append(part + "\n");
		}
		
		result.append("\n");
		
		result.append("SUPPLIERS:\n");
		for (Supplier supplier : getSuppliers()) {
			result.append(supplier + "\n");
		}
		
		result.append("\n");
		
		result.append("PART SUPPLIERS:\n");
		for (Part part : partSuppliers.keySet()) {
			result.append(part + "\n");
			
			for (Supplier supplier : partSuppliers.get(part)) {
				result.append("\t" + supplier + "\n");
			}
		}
		
		result.append("\n");
		
		result.append("SUPPLIER PARTS:\n");
		for (Supplier supplier : supplierParts.keySet()) {
			result.append(supplier + "\n");
			
			for (Part part : supplierParts.get(supplier)) {
				result.append("\t" + part + "\n");
			}
		}

		return result.toString();
	}

	@Override
	public Iterator<Part> iterator() {
		return parts.iterator();
	}
	
}
