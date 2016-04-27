import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.Font;
import javax.swing.JComboBox;
import java.awt.Color;
import javax.swing.JToggleButton;
import java.awt.Checkbox;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class CGui {

	private JFrame frmClientCaseTable;
	private JTextField tfCC;
	private JTextField tfCN;
	private Choice choice;
	private Checkbox checkbox_CaseOpen;
	private Checkbox checkbox_ShowAll;
	
	private ClientCasetable ctable;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CGui window = new CGui(new  ClientCasetable("ClientCasetable.txt"));
					window.frmClientCaseTable.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws IOException 
	 */
	public CGui(ClientCasetable ctable) throws IOException {
		this.ctable = ctable;
		initialize();
	}
	
	/**
	 * Description of the method view_actionPerformed.
	 */
	void view_actionPerformed() {
		ctable.view();
		tfCC.setText(ctable.clientCase);
		tfCN.setText(ctable.contactNumber);
		checkbox_CaseOpen.setState(ctable.caseOpen);
	}
	
	/**
	 * Description of the method inseert_actionPerformed.
	 */
	void insert_actionPerformed() {
		ctable.clientCase = tfCC.getText();
		ctable.contactNumber = tfCN.getText();
		checkbox_CaseOpen.setState(true);
		ctable.caseOpen = checkbox_CaseOpen.getState();
		ctable.insert();
		//Renew info on the userform
		choice.removeAll();
		String[] S = this.ctable.ids(checkbox_ShowAll.getState());
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		choice.select(String.valueOf(ctable.clientID));
		if (!choice.getSelectedItem().equals("")) {
    		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
    	} else {
    		ctable.clientID = 0;
    	}
		view_actionPerformed();
	}
	
	/**
	 * Description of the method update_actionPerformed.
	 */
	void update_actionPerformed() {
		ctable.clientCase = tfCC.getText();
		ctable.contactNumber = tfCN.getText();
		ctable.caseOpen = checkbox_CaseOpen.getState();
		ctable.update();
	}
	
	/**
	 * Description of the method clear_actionPerformed.
	 */
	void clear_actionPerformed() {
		// Determine selected ID
		if (!choice.getSelectedItem().equals("")) {
    		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
    	} else {
    		ctable.clientID = 0;
    	}		
		// Clearing record
		ctable.clear();
		// Renew info on the userform
		choice.removeAll();
		String[] S = this.ctable.ids(checkbox_ShowAll.getState());
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		choice.select(String.valueOf(ctable.clientID));
		if (!choice.getSelectedItem().equals("")) {
		    ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
		} else {
		    ctable.clientID = 0;
		}
		view_actionPerformed();
	}
	
	/**
	 * Description of the method close_actionPerformed.
	 */
	void close_actionPerformed() {

	} 

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frmClientCaseTable = new JFrame();
		frmClientCaseTable.setTitle("Client Case Table");
		frmClientCaseTable.setBounds(100, 100, 450, 230);
		frmClientCaseTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClientCaseTable.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ClientID");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel.setBounds(10, 11, 58, 14);
		frmClientCaseTable.getContentPane().add(lblNewLabel);
		
		
		JLabel lblClientCase = new JLabel("Client Case");
		lblClientCase.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClientCase.setBounds(173, 12, 68, 14);
		frmClientCaseTable.getContentPane().add(lblClientCase);
		
		tfCC = new JTextField();
		tfCC.setBounds(251, 9, 173, 20);
		frmClientCaseTable.getContentPane().add(tfCC);
		tfCC.setColumns(10);
		
		JLabel lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblContactNumber.setBounds(173, 64, 108, 14);
		frmClientCaseTable.getContentPane().add(lblContactNumber);
		
		tfCN = new JTextField();
		tfCN.setBounds(281, 62, 143, 20);
		frmClientCaseTable.getContentPane().add(tfCN);
		tfCN.setColumns(10);
		
		checkbox_CaseOpen = new Checkbox("Case Open");
		checkbox_CaseOpen.setFont(new Font("Dialog", Font.BOLD, 12));
		checkbox_CaseOpen.setBounds(281, 102, 132, 22);
		frmClientCaseTable.getContentPane().add(checkbox_CaseOpen);
		// Add item listener
		checkbox_CaseOpen.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent ie)
	        {
	        	if (!choice.getSelectedItem().equals("")) {
	        		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
	        	} else {
	        		ctable.clientID = 0;
	        	}
	        	update_actionPerformed();
	    		String[] S = ctable.ids(checkbox_ShowAll.getState());
	    		choice.removeAll();
	    		for (int i = 0; i < S.length; i++) {
	    			choice.add(S[i]);
	    		}
	    		if (!choice.getSelectedItem().equals("")) {
	        		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
	        	} else {
	        		ctable.clientID = 0;
	        	}
	    		view_actionPerformed();
	        }
	    });
		
		checkbox_ShowAll = new Checkbox("Show all");
		checkbox_ShowAll.setFont(new Font("Dialog", Font.BOLD, 12));
		checkbox_ShowAll.setBounds(10, 102, 95, 22);
		frmClientCaseTable.getContentPane().add(checkbox_ShowAll);
		// Add item listener
		checkbox_ShowAll.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent ie)
	        {
	    		String[] S = ctable.ids(checkbox_ShowAll.getState());
	    		choice.removeAll();
	    		for (int i = 0; i < S.length; i++) {
	    			choice.add(S[i]);
	    		}
	    		if (!choice.getSelectedItem().equals("")) {
	        		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
	        	} else {
	        		ctable.clientID = 0;
	        	}
	    		view_actionPerformed();
	        }
	    });
		// Insert button;
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_actionPerformed();
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsert.setBounds(83, 158, 89, 23);
		frmClientCaseTable.getContentPane().add(btnInsert);
		
		// Update button;
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_actionPerformed();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(182, 158, 89, 23);
		frmClientCaseTable.getContentPane().add(btnUpdate);
		
		// Clear button;
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_actionPerformed();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBounds(281, 158, 89, 23);
		frmClientCaseTable.getContentPane().add(btnClear);
		
		choice = new Choice();
		choice.setBounds(74, 11, 89, 20);
		frmClientCaseTable.getContentPane().add(choice);
		// Add item listener
		choice.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent ie)
	        {
	        	if (!choice.getSelectedItem().equals("")) {
	        		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
	        	} else {
	        		ctable.clientID = 0;
	        	}
	        	view_actionPerformed();
	        }
	    });
		
		// After all we init database conn
		ctable.initializeDB();
		String[] S = this.ctable.ids(checkbox_ShowAll.getState());
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		if (!choice.getSelectedItem().equals("")) {
    		ctable.clientID = Integer.valueOf(choice.getSelectedItem());	
    	} else {
    		ctable.clientID = 0;
    	}
		view_actionPerformed();
		
		frmClientCaseTable.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				try {
					ctable.closeDB();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
}
