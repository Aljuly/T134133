import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Choice;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextField;
import javax.swing.JEditorPane;
import javax.swing.JButton;

public class TGui {

	private JFrame frmTaskTable;
	private JTextField tfID;
	private JTextField tfDate;
	private JTextField tfDur;
	private Choice choice;
	private JEditorPane dtrpnDesc;
	
	private ClientCasetable ctable;
	private Tasctable tt;
	private ReportPrinter rp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TGui window = new TGui();
					window.frmTaskTable.setVisible(true);
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
	public TGui() throws IOException {
		this.ctable = new ClientCasetable("ClientCasetable.txt");
		this.tt = new Tasctable("Tasktable.txt");
		rp = new ReportPrinter(tt);
		initialize();
	}
	
	/**
	 * Description of the method view_actionPerformed.
	 */
	void view_actionPerformed() {
		tt.view();
		tfID.setText(String.valueOf(tt.TaskID));
		tfDate.setText(tt.date);
		dtrpnDesc.setText(tt.Description);
		tfDur.setText(String.valueOf(tt.Duration));
	}
	
	/**
	 * Description of the method inseert_actionPerformed.
	 */
	void insert_actionPerformed() {
		tt.clientCase = choice.getSelectedItem();
		tt.Description = ""; //dtrpnDesc.getText();
		tt.Duration = 0f; //tfDur.getText().trim().equals("") ? 0f : Float.valueOf(tfDur.getText().trim());
		tt.insert();
		//Renew info on the userform
		view_actionPerformed();
	}
	
	/**
	 * Description of the method update_actionPerformed.
	 */
	void update_actionPerformed() {
		tt.Description = dtrpnDesc.getText().replaceAll("\n|\r\n", " ");
		tt.Duration = tfDur.getText().trim().equals("") ? 0f : Float.valueOf(tfDur.getText().trim());
		tt.date = tfDate.getText();
		tt.TaskID = Integer.valueOf(tfID.getText());
		tt.update();
	}
	
	/**
	 * Description of the method clear_actionPerformed.
	 */
	void clear_actionPerformed() {	
		// Clearing record
		tt.TaskID = Integer.valueOf(tfID.getText());
		tt.clear();
		// Renew info on the userform
		tt.loadToTextField();
		view_actionPerformed();
	}
	

	/**
	 * Initialize the contents of the frame.
	 * @throws IOException 
	 */
	private void initialize() throws IOException {
		frmTaskTable = new JFrame();
		frmTaskTable.setTitle("Task Table");
		frmTaskTable.setBounds(100, 100, 469, 369);
		frmTaskTable.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTaskTable.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel.setBounds(10, 11, 431, 72);
		frmTaskTable.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblClientCase = new JLabel("Client Case");
		lblClientCase.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblClientCase.setBounds(10, 11, 79, 14);
		panel.add(lblClientCase);
		
		choice = new Choice();
		choice.setBounds(10, 31, 411, 20);
		panel.add(choice);

		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.RAISED, null, null));
		panel_1.setBounds(10, 94, 431, 189);
		frmTaskTable.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblTaskid = new JLabel("TaskID");
		lblTaskid.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTaskid.setBounds(10, 11, 52, 14);
		panel_1.add(lblTaskid);
		
		tfID = new JTextField();
		tfID.setEditable(false);
		tfID.setBackground(Color.YELLOW);
		tfID.setBounds(61, 9, 86, 20);
		panel_1.add(tfID);
		tfID.setColumns(10);
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDate.setBounds(178, 11, 52, 14);
		panel_1.add(lblDate);
		
		tfDate = new JTextField();
		tfDate.setEditable(false);
		tfDate.setBounds(240, 9, 117, 20);
		panel_1.add(tfDate);
		tfDate.setColumns(10);
		
		JLabel lblDescription = new JLabel("Description");
		lblDescription.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescription.setBounds(10, 51, 79, 14);
		panel_1.add(lblDescription);
		
		dtrpnDesc = new JEditorPane();
		dtrpnDesc.setBounds(10, 75, 411, 57);
		panel_1.add(dtrpnDesc);
		
		JLabel lblDuration = new JLabel("Duration");
		lblDuration.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDuration.setBounds(165, 52, 65, 14);
		panel_1.add(lblDuration);
		
		tfDur = new JTextField();
		tfDur.setBounds(240, 49, 117, 20);
		panel_1.add(tfDur);
		tfDur.setColumns(10);
		
		// Prev button
		JButton btnPrev = new JButton("<");
		btnPrev.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnPrev.setBounds(163, 143, 53, 32);
		panel_1.add(btnPrev);
		btnPrev.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tt.prev();
				view_actionPerformed();
			}
		});
		
		// Next Button
		JButton btnNext = new JButton(">");
		btnNext.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNext.setBounds(226, 143, 52, 32);
		panel_1.add(btnNext);
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tt.next();
				view_actionPerformed();
			}
		});
		
		// Insert button
		JButton btnInsert = new JButton("Insert");
		btnInsert.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInsert.setBounds(33, 294, 89, 32);
		frmTaskTable.getContentPane().add(btnInsert);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				insert_actionPerformed();
			}
		});
		
		// Update Button
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnUpdate.setBounds(132, 294, 89, 32);
		frmTaskTable.getContentPane().add(btnUpdate);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update_actionPerformed();
			}
		});
		
		// Clear button
		JButton btnClear = new JButton("Clear");
		btnClear.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnClear.setBounds(231, 294, 89, 32);
		frmTaskTable.getContentPane().add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clear_actionPerformed();
			}
		});
		
		
		// Report button
		JButton btnReport = new JButton("Report");
		btnReport.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnReport.setBounds(330, 294, 89, 32);
		frmTaskTable.getContentPane().add(btnReport);
		btnReport.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rp.print();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		// Add item listener
		choice.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent ie)
			{
				tt.clientCase = choice.getSelectedItem();
				tt.filter();
				tt.loadToTextField();
				view_actionPerformed();
			}
		});
		
		// After all we init database conn
		ctable.initializeDB();
		tt.initializeDB();
		String[] S = ctable.cls(false);
		for (int i = 0; i < S.length; i++) {
			choice.add(S[i]);
		}
		tt.clientCase = choice.getSelectedItem();
		tt.filter();
		tt.loadToTextField();
		view_actionPerformed();
		
		// When form begin closing, we save table to file 
		frmTaskTable.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e) {
				try {
					tt.closeDB();
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
