package server;

import java.util.*;

import server.database.*;
import server.database.dao.*;
import shared.model.*;
import shared.communication.*;
import server.*;

public class facade {

	public static void initialize() throws ServerException {		
		try {
			Database.initialize();		
		}
		catch (DBException e) {
			throw new ServerException(e.getMessage(), e);
		}		
	}
	
	/**
	 * auths a user
	 * @param t the usertoken
	 * @return an authtoken containing details if at all possible
	 * @throws ServerException for problems of any kind 
	 */
	public static authToken validateUser(userToken t) 
			throws ServerException{
		Database db = new Database("stowage.db");
		String un = t.getUsername();
		authToken a;
		try{
			db.startTX();
			String pw = db.getUserDAO().get(un);
			if(t.auth(pw)){
				user u = db.getUserDAO().get(un, pw);
				a = new authToken(u.getFName(), u.getLName(), 
						u.getRecords());
			}
			else {
				a = new authToken(1);
			}
			db.endTX(false);
			return a;
		}
		catch(DBException e){
			db.endTX(false);
			if(e.getMessage().equals("no such user")){
				a = new authToken(1);
				return a;
			}
			else {
				throw new ServerException(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * gets all the projects
	 * @param t the user token
	 * @return a list of projects
	 * @throws ServerException if problems
	 */
	public static projList getProjects(userToken t)
			throws ServerException{
		Database db = new Database("stowage.db");
		projList pl = new projList();
		try{
			authToken a = validateUser(t);
			if(a.invalid()){
				pl.setFailure();
				// no DB ops directly here
				return pl;
			}
			else {
				db.startTX();
				ArrayList<project> pal =
					db.getProjectDAO().getAll();
				for(project p : pal){
					pl.addProject(p);
				}
				db.endTX(false);
				return pl;
			}
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	/**
	 * gets the project's sample image
	 * @param t user's token
	 * @param id project ID number
	 * @return string URL to image
	 * @throws ServerException for ANY problem state, no flags
	 */
	public static String getSampleImage(userToken t, int id)
			throws ServerException{
		Database db = new Database("stowage.db");
		String s;
		try{
			authToken a = validateUser(t);
			if(a.invalid()){
				throw new ServerException("invalid user");
			}
			db.startTX();
			project p = db.getProjectDAO().get(id);
			s = p.getImage();
			db.endTX(false);
			return s;
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
				
	/**
	 * downloads a batch, sets new batchlock in DB
	 * @param t user's token
	 * @param id project ID number
	 * @return batchBlob containing batch info
	 * @throws ServerException if stuff is busted up inside
	 */
	public static batchBlob downloadBatch(userToken t, int id)
			throws ServerException{
		Database db = new Database("stowage.db");
		batchBlob bb = new batchBlob();
		try{
			authToken a = validateUser(t);
			if(a.invalid()){
				bb.setFailed();
				return bb;
			}
			else {
				db.startTX();
				project p = db.getProjectDAO().get(id);
				batch b = db.getBatchDAO().get(id);
				bb.addProject(p);
				bb.addBatch(b);
				db.endTX(true); // DB HAS CHANGED!
				return bb;
			}
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	/**
	 * attempts to submit a batch
	 * @param t user's token
	 * @param bp batch proposal
	 * @return true if works, false otherwise
	 * @throws ServerException if problems arise
	 */
	public static boolean submitBatch(userToken t, batchProposal bp)
			throws ServerException{
		Database db = new Database("stowage.db");
		boolean flag = false;
		try{
			authToken a = validateUser(t);
			if(a.invalid()){
				return false;
			}
			else {
				db.startTX();
				batch b = db.getBatchDAO().getInfo(bp.getID());
				String uname = t.getUsername();
				String pw = db.getUserDAO().get(uname);
				user u = db.getUserDAO().get(uname, pw);
				project p = db.getProjectDAO().get(b.getProject());
				if(
					b.isOwned() &&
					(b.whichUser() == u.getID()) &&
					(b.getIndexed() < p.getRecordQuantity())
				){
					String recs = bp.getSubmission();
					ArrayList<record> toAdd = new ArrayList<record>();
					int row = 1;
					Scanner record = new Scanner(recs).useDelimiter(";");
					while(record.hasNext()){
						int field = 1;
						String recsSS = record.next();
						Scanner recordSS = new Scanner(recsSS).useDelimiter(",");
						while(recordSS.hasNext()){
							String v = recordSS.next();
							if(!(v.equals(""))){
								toAdd.add(new record(
										b.getID(),
										field,
										row,
										v
									)
								);
							}
							field++;
						}
						if(field != b.getFields()){
							// in this case, we have a miswritten batch
							throw new ServerException("bad fields");
						}
						row++;
					}
					if(row != p.getRecordQuantity()){
						// in this case, we have a miswritten batch
						throw new ServerException("bad rows");
					}
					int recsAdded = 0;
					for(record r : toAdd){
						int rID = db.getRecordDAO().checkPresence(
								r.getBatch(), r.getFieldNumber(),
								r.getNumber());
						if (rID == -1){
							// get and set field ID
							// add
							int fkey = db.getFieldDAO().getID(
									p.getID(), r.getFieldNumber());
							r.setField(fkey);
							db.getRecordDAO().add(r);
							recsAdded++;
						}
						else {
							// get and set field ID
							// set ID
							// update
							int fkey = db.getFieldDAO().getID(
									p.getID(), r.getFieldNumber());
							r.setField(fkey);
							r.setID(rID);
							db.getRecordDAO().update(r);
						}
					}
					// update batch count, unassign user / batch
					b.updateRecords(recsAdded);
					b.loseUser();
					u.assignBatch(-1);
					db.getBatchDAO().update(b);
					db.getUserDAO().update(u);

					db.endTX(true);

					flag = true;
				}
			}
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
		return flag;
	}

	/**
	*	get all the fields from a certain project
	*	@param t the user's token
	*	@param pID the project ID
	*	@return a fieldList
	*	@throws ServerException in an emergency
	*/
	public static fieldList getFields(userToken t, int pID)
			throws ServerException {
		Database db = new Database("stowage.db");
		fieldList fl = new fieldList();
		try{
			authToken a = validateUser(t);
			if(a.invalid()){
				fl.setFailure();
				return fl;
			}
			else {
				db.startTX();
				ArrayList<field> fal =
					db.getFieldDAO().getAll(pID);
				for(field f: fal){
					fl.addField(f);
				}
				db.endTX(false);
				return fl;
			}
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
	}

	/**
	*	get all the fields from the DB
	*	@param t the user's token	
	*	@return a fieldList
	*	@throws ServerException in an emergency
	*/
	public static fieldList getFields(userToken t)
			throws ServerException {
		Database db = new Database("stowage.db");
		fieldList fl = new fieldList();
		try{
			authToken a = validateUser(t);
			if(a.invalid()){
				fl.setFailure();
				return fl;
			}
			else {
				db.startTX();
				ArrayList<field> fal =
					db.getFieldDAO().getAll();
				for(field f: fal){
					fl.addField(f);
				}
				db.endTX(false);
				return fl;
			}
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
	
	/**
	*	perform a search in the DB
	*	@param t the user's token
	*	@param sp the search proposal
	*	@return a list of searchBlobs
	*	@throws ServerException if necessary
	*/
	public static ArrayList<searchBlob> search(userToken t,
			searchProposal sp) throws ServerException {
		Database db = new Database("stowage.db");
		ArrayList<searchBlob> result = new ArrayList<searchBlob>();

		// create lists of the fields and values
		Scanner termVals = new Scanner(sp.searchTerm()).useDelimiter(",");
		Scanner termfIDs = new Scanner(sp.whichFields()).useDelimiter(",");

		ArrayList<String> vals = new ArrayList<String>();
		while(termVals.hasNext()){
			String value = termVals.next();
			vals.add(value);
		}
		ArrayList<Integer> fIDs = new ArrayList<Integer>();
		while(termfIDs.hasNext()){
			int fID = termfIDs.nextInt();
			fIDs.add(fID);
		}

		try{
			db.startTX();
			for(Integer f : fIDs){
				for(String v : vals){
				ArrayList<record> recs = db.getRecordDAO().getAll(f, v);
					for(record r : recs){
						String image = db.getBatchDAO()
								.getInfo(r.getBatch()).getImage();
						result.add(new searchBlob(
								r.getBatch(),
								image,
								r.getNumber(),
								r.getField()
							)
						);
					}
				}
			}
			db.endTX(false); // no change to DB
			return result;
		}
		catch(Exception e){
			db.endTX(false);
			throw new ServerException(e.getMessage(), e);
		}
	}
}

