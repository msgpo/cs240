package server;

import java.util.*;
import java.io.*;
import java.nio.file.*;
import javax.xml.xpath.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

import shared.model.*;
import server.database.*;
import server.database.dao.*;


/**
 * imports data from XML files and associated folders to a DB
 */
public class importer {
	

	public static void main(String[] args) throws IOException{
		
		// there are tricky variable names in here
		// because I needed so many temporary iterators
		// and the scope of some of these loops was so
		// big.  Here's a guide:
		//		p = project
		//		b = batch
		//			(etc)
		//		n = new (to be added)
		//		it = iterator
		//	arguments for constructors should be 
		//	fairly self-evident by tag name
		
		ArrayList<user> ulist = new ArrayList<user>();
		ArrayList<record> rlist = new ArrayList<record>();
		
		ArrayList<String> imglist = new ArrayList<String>();
		ArrayList<String> kdlist = new ArrayList<String>();
		ArrayList<String> helplist = new ArrayList<String>();
		
		File xmlFile = new File(args[0]);
		
		Database db = new Database("/Users/rt/workspaces/cs240/bigone/stowage.db");
		try{
			Database.initialize();
		}
		catch(DBException e){
			System.out.println("Cannot attach to DB");
			System.out.println("Failing");
			return;
		}
		
		Document doc;
			
		try{
			DocumentBuilderFactory docbf = 
					DocumentBuilderFactory.newInstance();
			DocumentBuilder docb = docbf.newDocumentBuilder();
			doc = docb.parse(xmlFile);
		}	
		catch(Exception e){
			System.out.println("Problem reading XML");
			System.out.println("Check path and file format");
			System.out.println("Failing");
			System.out.println(e);
			return;
		}
		
		doc.getDocumentElement().normalize();
		
		//XPath xpath = XPathFactory.newInstance().newXPath();
		
		// adding users is straightforward: no interaction 
		// between tables here.
		
		NodeList u = doc.getElementsByTagName("user");
		
		for(int it = 0; it < u.getLength(); it ++){
			Element userE = (Element) u.item(it);
			
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
		
		
		for(user t : ulist){
			try{
				db.startTX();
				db.getUserDAO().add(t);
				db.endTX(true);
			}
			catch(DBException e){
				System.out.println("Cannot import users from XML file");
				System.out.println("Failing.");
				return;
			}
		}
		
		
		// everything else is tricky, since they all have to know 
		// about each other.
		// So we have to adopt a nested approach, so that
		// the id of each "having" element can be known 
		// easily by the "had" elements
		
		NodeList p = doc.getElementsByTagName("project");
		// this list will contain all the projects, and their 
		// individual batches and fields
		
		for(int it = 0; it < p.getLength(); it++){
			Element projE = (Element) p.item(it);
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
					getElementsByTagName("image").item(0);
			img = (Element)img.
					getElementsByTagName("file").item(0);
			// dirty sample image grab
			// could also simply create np with "", and then 
			// add image later
			
			project np = new project(
					img.getTextContent(),
					tit.getTextContent(),
					0,
					Integer.parseInt(fyc.getTextContent()),
					Integer.parseInt(rht.getTextContent()),
					Integer.parseInt(rpi.getTextContent())
			);
			
			

			try{
				db.startTX();
				db.getProjectDAO().add(np);
				db.endTX(true);
			}
			catch(DBException e){
				System.out.println("Cannot add project from XML file");
				System.out.println("Failing.");
				return;
			}
			
			int pID = np.getID();
			
			ArrayList<Integer> fidlist = new ArrayList<Integer>();
			
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
				String data;
				// kd can point to null since not every field
				// has known data
				if(kd != null){
					data = kd.getTextContent();
				}
				else{
					data = "";
				}
						
						
				field nf = new field(
						ftit.getTextContent(),
						0,		
						pos,	// determined by order in file
						Integer.parseInt(fw.getTextContent()),
						Integer.parseInt(fx.getTextContent()),
						data,
						fh.getTextContent(),
						pID
				);
				
				helplist.add(fh.getTextContent());
				if(!(data.equals(""))){
					kdlist.add(data);
				}

				try{
					db.startTX();
					db.getFieldDAO().add(nf);
					fidlist.add(nf.getID());
					db.endTX(true);
				}
				catch(DBException e){
					System.out.println("Cannot add fields from XML");
					System.out.println("Failing.");
					return;
				}
			}
			
			//db.startTX();
			//for(field t : flist){
				//try{
					//db.getFieldDAO().add(t);
				//}
				//catch(DBException e){
					//System.out.println("Cannot add fields from XML file");
					//System.out.println("Failing.");
					//return;
				//}
			//}
			//db.endTX(true);
			
			NodeList b = projE.getElementsByTagName("image");
			// all the images/batches for this project
			
			for(int bit = 0; bit < b.getLength(); bit++){
				Element batE = (Element) b.item(bit);
				
				Element bimg = (Element)batE.
						getElementsByTagName("file").item(0);
				
				batch nb = new batch(
						bimg.getTextContent(),
						0,
						pID
				);
				
				nb.setFields(f.getLength());
				// a bit esoteric, but we've already counted
				// how many fields there should be.
				
				imglist.add(bimg.getTextContent());

				try{
					db.startTX();
					db.getBatchDAO().add(nb);
					db.endTX(true);
				}
				catch(DBException e){
					System.out.println("Cannot add batches from XML");
					System.out.println("Failing.");
					return;
				}
				
				int bID = nb.getID();
				int recordQ = 0;
				//now, consider the records.
				NodeList r = batE.getElementsByTagName("record");
				// all the records inside this "image" tag
				
				for(int rID = 0; rID < r.getLength(); rID++){
					Element recE = (Element) r.item(rID);
					
					NodeList vals = recE.
							getElementsByTagName("value");
					
					for(int fNum = 0; fNum < vals.getLength(); fNum ++){
						
						record nr = new record(
								bID,
								fidlist.get(fNum),
								0,
								(fNum + 1)
						);
						nr.setValue(vals.item(fNum).getTextContent());
						recordQ++;
						rlist.add(nr);
					}
				}
				
				nb.updateRecords(recordQ);
				try{
					db.startTX();
					db.getBatchDAO().update(nb);
					db.endTX(true);
				}
				catch(DBException e){
					db.endTX(false);
					System.out.println("Can't add num records to batch");
					System.out.println("Failing.");
					return;
				}
			}
		}
		
		
		// only thing not added to DB is records 
		// they were nested most deeply
		for(record r : rlist){
			
			try{
				db.startTX();
				db.getRecordDAO().add(r);
				db.endTX(true);
			}
			catch(DBException e){
				System.out.println("Can't add records from XML");
				System.out.println("Failing.");
				return;
			}
			

		}
		
		// now iterate thru lists of files, move to 
		// right place
		//
		// best way to do this is to use nio
		
		for(String t : imglist){
			moveFile(t, "data", xmlFile.getParent());
		}
		
		for(String t : kdlist){
			moveFile(t, "data", xmlFile.getParent());
		}
		
		for(String t : helplist){
			moveFile(t, "data", xmlFile.getParent());
		}
	}  // end of main
	
	
	private static void moveFile(String file, String toDir, 
			String fromDir){
		try{
			File toFile = new File(toDir, file);
			File inFile = new File(fromDir, file);
			
			Files.copy(inFile.toPath(), toFile.toPath(), 
					StandardCopyOption.REPLACE_EXISTING);
		}
		catch(Exception e){
			//if(
			System.out.println("Problem moving files");
			System.out.println("Failing.");
			System.out.println(e);
		}
	}
}
	
		
		
		
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
