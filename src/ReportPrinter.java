import java.io.*;
import java.text.SimpleDateFormat;

public class ReportPrinter {
	
	// tasctable
	private Tasktable tt;
	
	// File of data
	private File file;
	private String currentdir = "";
	
	// CreatedFlag
	private boolean Created;
	
	// The constructor 
	public ReportPrinter(Tasktable tt) throws IOException {
		this.tt = tt;
		fileCreator("Report.txt");
	}
	
	public void fileCreator (String name) throws IOException {
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
	}
	
	// Clearing the data file
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
	
	// Make report file
	public void print() throws Exception {
		final float hcost = 0.5f; //Price for working hours
		float htotal = 0f;
		ClearDataFile();
		if (Created) {
			// Opening file for rewrite
			DataOutputStream out = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file, false)));
			SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yy:HH:mm:SS");
			out.writeBytes("Report created: " + format1.format(tt.today) + "\n" );
			out.writeBytes("Client Case: " + tt.filteredrecset.get(0).clcase + "\n" );
			out.writeBytes(String.format("%6s%15s%50s%10s%n", "TaskID", "date","Description", "Duration"));
			out.writeBytes(String.format("%6s%15s%50s%10s%n", "------", "----", "-----------", "--------"));
			for (int i = 0; i < tt.filteredrecset.size(); i++) {
				out.writeBytes(String.format("%6s%15s%50s%10.2f%n", tt.filteredrecset.get(i).tid, tt.filteredrecset.get(i).dt, 
				tt.filteredrecset.get(i).desc,
				tt.filteredrecset.get(i).dur));
				htotal = htotal + tt.filteredrecset.get(i).dur;
			}
			out.writeBytes("--------------------------------------------------------------------------------" + "\n" );
			out.writeBytes("Total time: " + htotal + "\n");
			out.writeBytes("Total costs: " + htotal * hcost + "\n");
			out.close();
		}
				
	}
	
	// Path to file
	public String getPath() {
		return currentdir + "Report.txt";
	}
}
