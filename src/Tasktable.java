import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


public class Tasktable {
	protected int TaskID;
	protected String date;
	protected String clientCase;
	protected String Description;
	protected float Duration;
	
	protected Date today;
	protected SimpleDateFormat DATE_FORMAT;
	
	protected int reccnt;
	protected int curpos;
	
	// Recordset
	protected ArrayList<Record> recset;
	protected ArrayList<Record> filteredrecset;
	
	// File of data
	private File file;
	private String currentdir = "";
	
	// CreatedFlag
	private boolean Created;
	
	protected class Record {
		int tid;
		String dt;
		String clcase;
		String desc;
		float dur;
		
		public Record() {
			
		}
		
		public Record(int tid, String dt, String clcase, String desc, float dur) {
			this.tid = tid;
			this.dt = dt;
			this.clcase = clcase;
			this.desc = desc;
			this.dur = dur;
		}
		
		public String toString() {
			return Integer.toString(tid) + "," + dt + "," + clcase + "," + desc + "," + dur + "\n"; 
		}
	}
	
	public Tasktable(String name) {
		// Connect to data file
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
		// Create sets of records
		recset = new ArrayList<Record>();
		filteredrecset = new ArrayList<Record>();
		
		// Record counter
		reccnt = 0;
		// current record number
		curpos = 0;
		
		// Set up date format
		today = new Date(); //calendar on current date
		DATE_FORMAT = new SimpleDateFormat("dd/MM/yy");

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
				BufferedReader br = new BufferedReader(new FileReader(currentdir + "\\Tasktable.txt"));
				String curLine;
				while ((curLine = br.readLine()) != null) {
					String[] fields;
					fields = curLine.split(",");
					Record r = new Record();
					if (fields.length > 1) {
						r.tid = (Integer.parseInt(fields[0].trim()));
						r.dt = fields[1];
						r.clcase = fields[2];
						r.desc = fields[3];
						r.dur = Float.parseFloat(fields[4]);
						//System.out.println(r.copen);
						recset.add(r);
						reccnt = r.tid; 
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
				// Opening file for writing
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
	
	// Record List, that contains records for given client case
	public void filter() {
		filteredrecset.clear();
		for (int i = 0; i < recset.size(); i++) {
			if (recset.get(i).clcase.equals(this.clientCase)) {
				filteredrecset.add(recset.get(i));
			}
		}
	}
	
	// Get current record
	public void view() {
		boolean find = false;
		for (int i = 0; i < filteredrecset.size(); i++) {
			if (filteredrecset.get(i).tid == this.TaskID) {
				this.date = filteredrecset.get(i).dt;
				this.clientCase = filteredrecset.get(i).clcase;
				this.Description = filteredrecset.get(i).desc;
				this.Duration = filteredrecset.get(i).dur;
				find = true;
				break;
			}	
		}
		if (!find) {
				this.date = "";
				this.clientCase = "";
				this.Description = "";
				this.Duration = 0;
			}
	}
	
	// Insert new record
		public void insert() {
			Record r = new Record();
			this.reccnt++; 
			this.TaskID = reccnt;
			this.date = DATE_FORMAT.format(today);
			r.dt = this.date;
			r.tid = this.TaskID;
			r.clcase = this.clientCase;
			r.desc = this.Description;
			r.dur = this.Duration;
			filteredrecset.add(r);
			recset.add(r);
		}
		
		// Edit current record
		public void update() {
			// Update filtered record list
			for (int i = 0; i < filteredrecset.size(); i++) {
				if (filteredrecset.get(i).tid == this.TaskID) {
					filteredrecset.get(i).dt = this.date;
					filteredrecset.get(i).clcase = this.clientCase;
					filteredrecset.get(i).desc = this.Description;
					filteredrecset.get(i).dur = this.Duration;
					break;
				}
			}
			//update main record list
			for (int i = 0; i < recset.size(); i++) {
				if (recset.get(i).tid == this.TaskID) {
					recset.get(i).dt = this.date;
					recset.get(i).clcase = this.clientCase;
					recset.get(i).desc = this.Description;
					recset.get(i).dur = this.Duration;
					break;
				}
			}
		}
		
		// Delete current record
		public void clear() {
			for (int i = 0; i < filteredrecset.size(); i++) {
				if (filteredrecset.get(i).tid == this.TaskID) {
					filteredrecset.remove(i);
					//System.out.println("Removing" + filteredrecset.get(i).tid);
					break;
				}
			} 
			for (int i = 0; i < recset.size(); i++) {
				if (recset.get(i).tid == this.TaskID) {
					//System.out.println("Removing" + recset.get(i).tid);
					recset.remove(i);
					break;
				}
			} 
		}
		
		// Show record fields in textboxes
		public void loadToTextField() {
			if (!filteredrecset.isEmpty()) {
				this.TaskID = filteredrecset.get(0).tid;
				this.date = filteredrecset.get(0).dt;
				this.clientCase = filteredrecset.get(0).clcase;
				this.Description = filteredrecset.get(0).desc;
				this.Duration = filteredrecset.get(0).dur;
			} else {
				this.TaskID = 0;
				this.date = "";
				this.clientCase = "";
				this.Description = "";
				this.Duration = 0;
			}
		}
		
		// Move forward in recset
		public void next() {
			if (!filteredrecset.isEmpty()) {
				curpos++;
				if (curpos <= filteredrecset.size() - 1) {
					this.TaskID = filteredrecset.get(curpos).tid;
					//view();
				} else curpos = filteredrecset.size();
			} else {
				this.TaskID = 0;
				this.date = "";
				this.clientCase = "";
				this.Description = "";
				this.Duration = 0;
			}
		}
		
		// Move backward in recset
		public void prev() {
			if (!filteredrecset.isEmpty()) {
				if (curpos > filteredrecset.size() - 1) curpos = filteredrecset.size() - 1;
				curpos--;
				if (curpos >= 0) {
					this.TaskID = filteredrecset.get(curpos).tid;
					//view();
				} else curpos = 0;
			} else {
				this.TaskID = 0;
				this.date = "";
				this.clientCase = "";
				this.Description = "";
				this.Duration = 0;
			}
		}
		
		// Cumulative time
		public float totalTime() {
			float htotal = 0f;
			for (int i = 0; i < filteredrecset.size(); i++) {
				htotal = htotal + filteredrecset.get(i).dur;
			}
			return htotal;
		}
		
		// Remove all records
		public void clearAll() {
			recset.clear();
			filteredrecset.clear();
			reccnt = 0;
			curpos = 0;
		}
		
		public static void main(String[] args) {
			
		}
		
}
