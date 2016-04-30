import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.Checkbox;
import javax.swing.JButton;
import java.awt.Choice;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import java.awt.Color;

public class CGui {

	public JFrame frmClientCaseTable;
	private JTextField tfCN;
	private Checkbox checkbox_CaseOpen;
	private Checkbox checkbox_ShowAll;
	private JTextPane tfCC;
	private Choice choice;
	private JLabel stateLabel;
	private static CGui window;
	
	private ClientCasetable ctable;
	private JTextField tfID;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					window = new CGui(new  ClientCasetable("ClientCasetable.txt"));
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
		tfID.setText(String.valueOf(ctable.clientID));
		tfCN.setText(ctable.contactNumber);
		tfCC.setText(ctable.clientCase);
		checkbox_CaseOpen.setState(ctable.caseOpen);
	}
	
	/**
	 * Description of the method inseert_actionPerformed.
	 */
	void insert_actionPerformed() {
		ctable.clientCase = " "; 
		ctable.contactNumber = ""; 
		checkbox_CaseOpen.setState(true);
		ctable.caseOpen = checkbox_CaseOpen.getState();
		ctable.insert();
		//Renew info on the user form
		choice.add(" ");
		//Move to the last position in choice
		choice.select(choice.getItemCount() - 1);
		stateLabel.setText("Record inserted. ID=" + ctable.clientID);
		view_actionPerformed();
	}
	
	/**
	 * Description of the method update_actionPerformed.
	 */
	void update_actionPerformed() {
		ctable.clientID = Integer.valueOf(tfID.getText());
		ctable.clientCase = tfCC.getText().replaceAll("\n|\r\n|,", " ");
		ctable.contactNumber = tfCN.getText();
		ctable.caseOpen = checkbox_CaseOpen.getState();
		ctable.update();
		//Renew info on the user form
		int pos = choice.getSelectedIndex();
		choice.removeAll();
		String[] S = ctable.cls(checkbox_ShowAll.getState());
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		if ((pos > 0) && (pos < choice.getItemCount())) choice.select(pos);
		stateLabel.setText("Record updated. ID=" + ctable.clientID);
	}
	
	/**
	 * Description of the method clear_actionPerformed.
	 */
	void clear_actionPerformed() {
		// Determine selected ID
		ctable.clientID = Integer.valueOf(tfID.getText());
		// Clearing record
		stateLabel.setText("Record deleted. ID=" + ctable.clientID);
		ctable.clear();
		// Renew info on the user form
		choice.removeAll();
		String[] S = this.ctable.cls(checkbox_ShowAll.getState());
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		if (choice.getItemCount() > 0) {
			choice.select(0);
			ctable.clientCase = choice.getSelectedItem();
			
		} else {
			ctable.loadToTextField();
		}
		view_actionPerformed();
	}

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 	 */
	private void initialize() throws IOException {
		frmClientCaseTable = new JFrame();
		frmClientCaseTable.setTitle("Client Case Table");
		frmClientCaseTable.setBounds(100, 100, 450, 338);
		frmClientCaseTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmClientCaseTable.getContentPane().setLayout(null);
		// Insert button;
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_actionPerformed();
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsert.setBounds(20, 252, 89, 23);
		frmClientCaseTable.getContentPane().add(btnInsert);
		
		// Update button;
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_actionPerformed();
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(119, 252, 89, 23);
		frmClientCaseTable.getContentPane().add(btnUpdate);
		
		// Clear button;
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_actionPerformed();
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBounds(218, 252, 89, 23);
		frmClientCaseTable.getContentPane().add(btnClear);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setBounds(10, 97, 422, 144);
		frmClientCaseTable.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ClientID");
		lblNewLabel.setBounds(10, 11, 58, 14);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		
		JLabel lblClientCase = new JLabel("Client Case");
		lblClientCase.setBounds(116, 11, 68, 14);
		panel.add(lblClientCase);
		lblClientCase.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(111, 31, 301, 51);
		panel.add(scrollPane);
		
		tfCC = new JTextPane();
		scrollPane.setViewportView(tfCC);
		
		JLabel lblContactNumber = new JLabel("Contact Number");
		lblContactNumber.setBounds(116, 93, 108, 14);
		panel.add(lblContactNumber);
		lblContactNumber.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		tfCN = new JTextField();
		tfCN.setBounds(111, 111, 143, 20);
		panel.add(tfCN);
		tfCN.setColumns(10);
		
		checkbox_CaseOpen = new Checkbox("Case Open");
		checkbox_CaseOpen.setBounds(280, 111, 132, 22);
		panel.add(checkbox_CaseOpen);
		checkbox_CaseOpen.setFont(new Font("Dialog", Font.BOLD, 12));
		
		tfID = new JTextField();
		tfID.setEditable(false);
		tfID.setBackground(Color.YELLOW);
		tfID.setBounds(10, 31, 86, 20);
		panel.add(tfID);
		tfID.setColumns(10);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(10, 11, 422, 75);
		frmClientCaseTable.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel label = new JLabel("Client Case");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		label.setBounds(10, 11, 79, 14);
		panel_1.add(label);
		
		choice = new Choice();
		choice.setBounds(10, 31, 330, 20);
		panel_1.add(choice);
		// Add item listener
		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				ctable.clientCase = choice.getSelectedItem();
				//ctable.loadToTextField();
				view_actionPerformed();
				stateLabel.setText(""); 
			}
		});
		
		checkbox_ShowAll = new Checkbox("Show all");
		checkbox_ShowAll.setBounds(346, 29, 66, 22);
		panel_1.add(checkbox_ShowAll);
		checkbox_ShowAll.setFont(new Font("Dialog", Font.BOLD, 12));
		
		stateLabel = new JLabel("");
		stateLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		stateLabel.setBounds(10, 284, 422, 14);
		frmClientCaseTable.getContentPane().add(stateLabel);
		
		//Hide form
		JButton btnHide = new JButton("Hide");
		btnHide.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				frmClientCaseTable.setVisible(false);
				
			}
		});
		btnHide.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnHide.setBounds(343, 253, 89, 23);
		frmClientCaseTable.getContentPane().add(btnHide);
		
		// Add item listener
		checkbox_ShowAll.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent ie)
	        {
	    		String[] S = ctable.cls(checkbox_ShowAll.getState());
	    		choice.removeAll();
	    		for (int i = 0; i < S.length; i++) {
	    			choice.add(S[i]);
	    		}
	    		ctable.clientCase = choice.getSelectedItem();
	    		view_actionPerformed();
	        }
	    });
		
		// Add item listener
		checkbox_CaseOpen.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent ie)
	        {	
	        	ctable.clientCase = choice.getSelectedItem();	
	        	update_actionPerformed();
	    		String[] S = ctable.cls(checkbox_ShowAll.getState());
	    		int pos = choice.getSelectedIndex();
	    		choice.removeAll();
	    		for (int i = 0; i < S.length; i++) {
	    			choice.add(S[i]);
	    		}
	    		if ((pos > 0) && (pos < choice.getItemCount())) choice.select(pos);
	    		ctable.clientCase = choice.getSelectedItem();
	    		view_actionPerformed();
	        }
	    });
		
		// After all we init database conn
		
		ctable.initializeDB();
		String[] S = ctable.cls(checkbox_ShowAll.getState());
		choice.removeAll();
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		ctable.clientCase = choice.getSelectedItem();
		view_actionPerformed();
		
		frmClientCaseTable.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				try {
					ctable.closeDB();
				} catch (IOException e1) {
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
				try {
					ctable.closeDB();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
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
