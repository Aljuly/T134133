
import java.io.*;
import java.util.*;

public class ClientCasetable {
	protected int clientID;
	protected String clientCase;
	protected String contactNumber;
	protected boolean caseOpen;
	protected int reccnt;
	
	// Recordset
	protected ArrayList<Record> recset;
	
	// File of data
	private File file;
	private String currentdir = "";
	
	// CreatedFlag
	private boolean Created;
	
	private class Record {
		int clid;
		String clcase;
		String conumber;
		Boolean copen;
		
		public String toString() {
			return Integer.toString(clid) + "," + clcase + "," + conumber + "," + copen + "\n"; 
		}
	}
	
	public ClientCasetable(String name) {
		currentdir = System.getProperty("user.dir");
		try {
			file = new File(currentdir, name);
			if (file.createNewFile()) {
				System.out.println("Data file created");
				Created = true;
			} else {
				System.out.println("Data file already present");
				Created = true;
			}
		} catch (IOException e) {
			System.err.println("Error in connecting to file: " + e.getMessage());
			Created = false;
		}
		recset = new ArrayList<Record>();
		reccnt = 0;
	}
	
	// Clearing data file
	public boolean ClearDataFile() throws FileNotFoundException, IOException {
		if (Created) {
			try {
				// Opening file for rewrite
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
				// Clearing the file
				out.writeChars("");
				// Closing the file
				out.close();
				return (boolean) true;
			} catch (FileNotFoundException fn) {
				System.err.println("Error in connecting to file: " + fn.getMessage());
				return (boolean) false;
			} catch (IOException e) {
				System.err.println("Error in writing to file: " + e.getMessage());
				return (boolean) false;
			} 
		} else {
			return (boolean) false;
		}
	}	
	
	// Database initializing
	public void initializeDB() throws IOException  {
		if (Created) {
			try {
				// Opening file for read
				BufferedReader br = new BufferedReader(new FileReader(currentdir + "\\ClientCasetable.txt"));
				String curLine;
				while ((curLine = br.readLine()) != null) {
					String[] fields;
					fields = curLine.split(",");
					Record r = new Record();
					//System.out.println(curLine);
					if (fields.length > 1) {
						r.clid = (Integer.parseInt(fields[0].trim()));
						r.clcase = fields[1];
						r.conumber = fields[2];
						if (fields[3].trim().equals("true")) {
							r.copen = true;
						} else {
							r.copen = false;
						}
						//System.out.println(r.copen);
						recset.add(r);
						reccnt = r.clid; 
					}
				}
				br.close();
			} catch (IOException e) {
				System.err.println("Error working with file: " + e.getMessage());
			}
		}
	}
	
	// Database closing
	public void closeDB() throws IOException  {
		if (Created) {
			ClearDataFile();
			try {
				// Opening file for write
				DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
				for (int i = 0; i < recset.size(); i++) {
					out.writeBytes(recset.get(i).toString());
				}
				out.close();
			} catch (IOException e) {
				System.err.println("Error working with file: " + e.getMessage());
			}
		}
	}
	
	// Get current record
	public void view() {
		boolean find = false;
		for (int i = 0; i < recset.size(); i++) {
			if (recset.get(i).clcase.equals(this.clientCase)) {
				this.clientID = recset.get(i).clid;
				this.contactNumber = recset.get(i).conumber;
				this.caseOpen = recset.get(i).copen;
				find = true;
				break;
			}
		}
		if (!find) {
				this.clientID = 0;
				this.contactNumber = "";
				this.caseOpen = false;
		}
	}
	
	// Insert new record
	public void insert() {
		Record r = new Record();
		this.reccnt++; this.clientID = reccnt;
		r.clid = this.clientID;
		r.clcase = this.clientCase;
		r.conumber = this.contactNumber;
		r.copen = this.caseOpen;
		recset.add(r);
	}
	
	// Edit current record
	public void update() {
		for (int i = 0; i < recset.size(); i++) {
			if (recset.get(i).clid == this.clientID) {
				recset.get(i).clcase = this.clientCase;
				recset.get(i).conumber = this.contactNumber;
				recset.get(i).copen = this.caseOpen;
				break;
			}
		}
	}
	
	// Delete current record
	public void clear() {
		for (int i = 0; i < recset.size(); i++) {
			if (recset.get(i).clid == clientID) {
				recset.remove(i);
				break;
			}
		}
	}
	
	// Creating list for combo. ?b == true -> output all 
	public String[] ids(boolean b) {
		String S = "";
		for (int i = 0; i < recset.size(); i++) {
			if (b) {
				S = S + String.valueOf(recset.get(i).clid) + ",";
			} else if (recset.get(i).copen) {
				S = S + String.valueOf(recset.get(i).clid) + ",";
			}
		}
		return S.split(",");
	}
	
	// Creating list for Tasks combo. ?b == true -> output all 
	public String[] cls(boolean b) {
		String S = "";
		for (int i = 0; i < recset.size(); i++) {
			if (b) {
				S = S + recset.get(i).clcase + ",";
			} else if (recset.get(i).copen) {
				S = S + recset.get(i).clcase + ",";
			}
		}
		return S.split(",");
	}
	
	// Show record fields in textboxes
	public void loadToTextField() {
		if (!recset.isEmpty()) {
			this.clientID = recset.get(0).clid;
			this.clientCase = recset.get(0).clcase;
			this.contactNumber = recset.get(0).conumber;
			this.caseOpen = recset.get(0).copen;
		} else {
			this.clientID = 0;
			this.clientCase = "";
			this.contactNumber = "";
			this.caseOpen = false;
		}
	}
	
	// Remove all records
	public void clearAll() {
		recset.clear();
		reccnt = 0;
	}
	
	
	public static void main(String[] args)  {

	}
}
