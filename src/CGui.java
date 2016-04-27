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
import java.io.IOException;
import java.awt.event.ActionEvent;

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
		
	
		
	}
	
	/**
	 * Description of the method inseert_actionPerformed.
	 */
	void insert_actionPerformed() {
		
	}
	
	/**
	 * Description of the method update_actionPerformed.
	 */
	void update_actionPerformed() {
		
	}
	
	/**
	 * Description of the method clear_actionPerformed.
	 */
	void clear_actionPerformed() {
		
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
		
		checkbox_ShowAll = new Checkbox("Show all");
		checkbox_ShowAll.setFont(new Font("Dialog", Font.BOLD, 12));
		checkbox_ShowAll.setBounds(10, 102, 95, 22);
		frmClientCaseTable.getContentPane().add(checkbox_ShowAll);
		
		JButton btnInsert = new JButton("Insert");
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsert.setBounds(83, 158, 89, 23);
		frmClientCaseTable.getContentPane().add(btnInsert);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(182, 158, 89, 23);
		frmClientCaseTable.getContentPane().add(btnUpdate);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBounds(281, 158, 89, 23);
		frmClientCaseTable.getContentPane().add(btnClear);
		
		choice = new Choice();
		choice.setBounds(74, 11, 89, 20);
		frmClientCaseTable.getContentPane().add(choice);
		
		// After all we init database conn
		ctable.initializeDB();
		String[] S = this.ctable.ids(true);
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
			System.out.println(S.length);
		}
		ctable.clientID = Integer.valueOf(choice.getSelectedItem());
		ctable.view();
		tfCC.setText(ctable.clientCase);
		tfCN.setText(ctable.contactNumber);
		checkbox_CaseOpen.setState(!ctable.caseOpen);
	}
}
