
public class Test {

	public static void main(String[] args) {

		PartsDatabase db = new PartsDatabase();
		
		Part bolt = new Part(1, "Bolt");
		db.addPart(bolt);
		db.addPart(new Part(1, "Bolt"));
		db.addPart(new Part(1, "Bolt"));
		db.addPart(new Part(1, "Bolt"));
		
		Supplier acme = new Supplier("v-001", "Acme Bolts");
		db.addSupplier(acme);
		db.addSupplier(new Supplier("v-001", "Acme Bolts"));
		db.addSupplier(new Supplier("v-001", "Acme Bolts"));
		
		db.addPartSupplier(bolt,  acme);
		
		for (Supplier supplier : db.getSuppliers()) {
			if (supplier.getName().equals("Acme Bolts")) {
				// DANGER! You should not modify fields that are used to sort Tree-based collections
				//supplier.setName("ACME Bolts");
				
				// Do this instead
				db.changeSupplierName(supplier, "ACME Bolts");
			}
		}

		System.out.println(db);
		
		System.out.println("\n");
		for (Part part : db) {
			System.out.println(part);
		}
	}
	
}
