package server;

import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;



public class importer {
	
	private ArrayList<user> ulist = new ArrayList<user>();
	private ArrayList<batch> blist = new ArrayList<batch>();
	private ArrayList<field> flist = new ArrayList<field>();
	private ArrayList<record> rlist = new ArrayList<record>();
	
	public static main(String[] args){
		
		File xmlFile = new File(args[1]);
		
		Database db = new Database("/Users/rt/workspaces/cs240/bigone/stowage.db");
		Database.initialize();
		
		
		DocumentBuilderFactor docbf = dbf.newInstance();
		DocumentBuilder docb = dbf.newDocumentBuilder();
		Document doc = docb.parse(xmlFile);
		
		doc.getDocumentElement().normalize();
		
		//XPath xpath = XPathFactory.newInstance().newXPath();
		
		NodeList u = doc.getElementsByTagName("user");
		
		for(Node n : u){
			Element userE = (Element) n;
			
			Element un = (Element)userE.
					getElementsByTagName("username").item(0);
			Element pw = (Element)userE.
					getElementsByTagName("password").item(0);
			Element fn = (Element)userE.
					getElementsByTagName("firstname").item(0);
			Element ln = (Element)userE.
					getElementsByTagName("lastname").item(0);
			Element ri = (Element)userE.
					getElementsByTagName("indexedrecords").item(0);
			
			user nu = new user(
					un.getTextContent(),
					fn.getTextContent(),
					ln.getTextContent(),
					pw.getTextContent(),
					Integer.parseInt(ri.getTextContent()),
					0
			);
			
			ulist.add(nu);
		}
		
		db.startTX();
		for(user t : ulist){
			try{
				db.getUserDAO().add(t);
			}
			catch(DBException e){
				System.out.println("Cannot import users from XML file");
				System.out.println("Failing.");
				return;
			}
		}
		db.endTX(true);
		
		
		NodeList p = doc.getElementByTagName("project");
		// this list will contain all the projects, and their 
		// individual batches and fields
		
		for(Node n : p){
			Element projE = (Element) n;
			// project to pick apart
			
			Element tit = (Element)projE.
					getElementsByTagName("title").item(0);
			Element rpi = (Element)projE.
					getElementsByTagName("recordsperimage").item(0);
			Element fyc = (Element)projE.
					getElementsByTagName("firstycoord").item(0);
			Element rht = (Element)projE.
					getElementsByTagName("recordheight").item(0);
			// that's almost all to create a project
			Element img = (Element)projE.
					getElementsByTagName("image").item(0).
					getElementsByTagName("file").item(0);
			// dirty sample image grab
			
			project np = new project(
					img.getTextContent(),
					tit.getTextContent(),
					0,
					Integer.parseInt(fyc.getTextContent()),
					Integer.parseInt(rht.getTextContent()),
					Integer.parseInt(rpi.getTextContent())
			);
			
			db.startTX()
			try{
				db.getProjectDAO().add(np);
			}
			catch(DBException e){
				System.out.println("Cannot add project from XML file");
				System.out.println("Failing.");
				return;
			}
			db.endTX(true);
			
			int pID = np.getID();
			
			NodeList f = projE.getElementsByTagName("field");
			// all the fields this project has - add after project added
			for(int pos = 0; pos < f.getLength(); pos++){
				Element fE = (Element) f.item(pos);
				
				Element ftit = (Element)fE.
						getElementsByTagName("title").item(0);
				Element fx = (Element)fE.
						getElementsByTagName("xcoord").item(0);
				Element fw = (Element)fE.
						getElementsByTagName("width").item(0);
				Element fh = (Element)fE.
						getElementsByTagName("helphtml").item(0);
				Element kd = (Element)fE.
						getElementsByTagName("knowndata").item(0);
						
				field nf = new field(
						ftit.getTextContent(),
						0,		
						pos,	// determined by order in file
						Integer.parseInt(fw.getTextContent()),
						Integer.parseInt(fx.getTextContent()),
						kd.getTextContent(),
						fh.getTextContent(),
						pID
				);
				
				flist.add(nf);
			}
			
			db.startTX();
			for(field t : flist){
				try{
					db.getFieldDAO().add(t);
				}
				catch(DBException e){
					System.out.println("Cannot add fields from XML file");
					System.out.println("Failing.");
					return;
				}
			}
			db.endTX(true);
			
			NodeList b = projE.getElementsByTagName("image");
			// all the images/batches for this project
			
			for(Node t : b){
				Element batE = (Element) t;
				
				Element bimg = (Element)batE.
						getElementsByTagName("file").item(0);
				
				batch nb = batch(
						bimg.getTextContent(),
						0,
						pID
				);
				
				nb.setFields(f.getLength());
				// a bit esoteric, but we've already counted
				// how many fields there should be.
				
				db.startTX();
				try{
					db.getBatchDAO().add(nb);
				}
				catch(DBException e){
					System.out.println("Cannot add batches from XML");
					System.out.println("Failing.");
					return;
				}
				
				bID = nb.getID();
				
				//now, consider the records.
				NodeList r = batE.getElementsByTagName("record");
				
				for(int rID = 0, rID < rID.getLength(), rID++){
					Element recE = (Element) r.item(rID);
					
					NodeList vals = recE.
							getElementsByTagName("value");
					
					for(int fNum : vals){
						Element value = (Element) vt;
						record nr = new record(b f r num
								bID;
								
								
			
			//  read thru the list of recs, incrementing number
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
