package j2p.J2P;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import j2p.J2P.connections.BoardConnection;
import j2p.J2P.connections.Connection;
import j2p.J2P.connections.ConnectionPool;
import j2p.J2P.connections.IssueConnection;
import j2p.J2P.connections.PrioritiesConnection;
import j2p.J2P.connections.SprintConnection;
import j2p.J2P.connections.TaskLengthConnection;
import j2p.J2P.connections.TaskTypeConnection;
import j2p.J2P.customfields.JListGenerator;
import j2p.J2P.customfields.SortedComboBoxModel;
import j2p.J2P.objects.BoardObject;
import j2p.J2P.objects.CustomFieldObject;
import j2p.J2P.objects.IssueObject;
import j2p.J2P.objects.PriorityObject;
import j2p.J2P.objects.SprintObject;
import j2p.J2P.objects.TaskObject;
import j2p.J2P.translators.Json2Object;
import j2p.J2P.translators.List2Excel;
import j2p.J2P.translators.List2PPT;

public class Main {
	
	// JFrame
	private JFrame frmJirapresentation;
	private JTextField urlField;
	private JTextField userField;
	private JPasswordField passField;
	@SuppressWarnings("rawtypes")
	private JList jList 			   = null;
	private JButton btnGetSprints 	   = null;
	private JButton btnSelectAll 	   = null;
	private JButton btnDeselectCurrent = null;
	private JComboBox slctTrackMethod  = null;
	private JTextField txtFldNewOption;
	JPanel panel4					   = null;
	JPanel panel3					   = null;
	JPanel panel2					   = null;
	JPanel panel1					   = null;
	
	
	// Custom variables
	private int majVersion   	 = 0;
	private int minVersion   	 = 0;
	private long buildNum    	 = 0;
	private String url 		 	 = "";
	private String user 	 	 = "";
	private String pass 	 	 = "";
	private boolean devBuild	 = false;
	private boolean includeWknds = false;
	
	// Custom Objects
	private Connection connection  = null;
	private Json2Object j2o 	   = new Json2Object();
	private JListGenerator listGen = new JListGenerator();
	private ConnectionPool cp	   = new ConnectionPool();
	
	// Lists
	private List<Integer> boardIds  		 = new ArrayList<Integer>();
	private List<Integer> sprintIds			 = new ArrayList<Integer>();
	private List<Integer> issueIds  		 = new ArrayList<Integer>();
	private List<BoardObject> boards 		 = new ArrayList<BoardObject>();
	private List<SprintObject> sprints 		 = new ArrayList<SprintObject>();
	private List<IssueObject> issues 		 = new ArrayList<IssueObject>();
	private List<PriorityObject> priorities  = new ArrayList<PriorityObject>();
	private List<TaskObject> taskTypes 		 = new ArrayList<TaskObject>();
	private List<JRadioButton> exportOptions = new ArrayList<JRadioButton>();
	private List<JRadioButton> incldWknds    = new ArrayList<JRadioButton>();
	
	// Hashmaps
	private HashMap<Integer, Integer> boardSprintDict = new HashMap<Integer, Integer>();
	private HashMap<String, CustomFieldObject>cfoDict = new HashMap<String, CustomFieldObject>();
	private JTextField txtFldNumDays;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main window = new Main();
					window.frmJirapresentation.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Main() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			File file = new File("version.txt");
			FileReader reader = new FileReader(file.toString());
			BufferedReader br = new BufferedReader(reader);
			majVersion = Integer.parseInt(br.readLine());
			minVersion = Integer.parseInt(br.readLine());
			buildNum   = Integer.parseInt(br.readLine());
			if(devBuild) {
				buildNum++;
				List<String> lines = Files.readAllLines(file.toPath());
				lines.set(2, buildNum+"");
				Files.write(file.toPath(), lines);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frmJirapresentation = new JFrame();
		frmJirapresentation.getContentPane().setBackground(new Color(0, 153, 255));
		frmJirapresentation.setTitle("JIRA2Presentation");
		frmJirapresentation.setBounds(100, 100, 1200, 800);
		frmJirapresentation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJirapresentation.getContentPane().setLayout(null);
		frmJirapresentation.setResizable(false); // TODO: Make the UI scale when resizing and have a minimum size
		
		panel4 = new JPanel();
		panel4.setBackground(new Color(0, 153, 255));
		panel4.setBounds(0, 0, 1200, 800);
		frmJirapresentation.getContentPane().add(panel4);
		panel4.setLayout(null);
		panel4.setVisible(false);
		
		final JRadioButton rbQuarterlyReport = new JRadioButton("Quarterly Report (Small)");
		rbQuarterlyReport.setSelected(true);
		rbQuarterlyReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rbQuarterlyReport.isSelected()) {
					for(JRadioButton rbd : exportOptions) {
						if(rbd.getText() != rbQuarterlyReport.getText()) rbd.setSelected(false);
					}
				}
			}
		});
		rbQuarterlyReport.setToolTipText("Generate a quick report for a larger presentation.");
		rbQuarterlyReport.setFont(new Font("SansSerif", Font.PLAIN, 13));
		rbQuarterlyReport.setBackground(new Color(0, 153, 255));
		rbQuarterlyReport.setBounds(972, 361, 179, 25);
		exportOptions.add(rbQuarterlyReport);
		panel4.add(rbQuarterlyReport);
		
		final JRadioButton rbSprintReport = new JRadioButton("Sprint Report");
		rbSprintReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rbSprintReport.isSelected()) {
					for(JRadioButton rbd : exportOptions) {
						if(rbd.getText() != rbSprintReport.getText()) rbd.setSelected(false);
					}
				}
			}
		});
		rbSprintReport.setToolTipText("Report the issues of the sprint.\r\n");
		rbSprintReport.setFont(new Font("SansSerif", Font.PLAIN, 13));
		rbSprintReport.setBackground(new Color(0, 153, 255));
		rbSprintReport.setBounds(972, 323, 162, 25);
		exportOptions.add(rbSprintReport);
		panel4.add(rbSprintReport);
		
		final JLabel label = new JLabel("Export Options (PPT)");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("SansSerif", Font.BOLD, 16));
		label.setBounds(972, 292, 162, 22);
		panel4.add(label);
		
		final JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(320, 150, 600, 530);
		panel4.add(scrollPane_2);
		
		final JCheckBox chckbxExportAsXlsx = new JCheckBox("Export as .XLSX");
		chckbxExportAsXlsx.setSelected(true);
		chckbxExportAsXlsx.setFont(new Font("SansSerif", Font.PLAIN, 13));
		chckbxExportAsXlsx.setBackground(new Color(0, 153, 255));
		chckbxExportAsXlsx.setBounds(972, 179, 130, 25);
		panel4.add(chckbxExportAsXlsx);
		
		final JCheckBox chckbxExportAsPpt = new JCheckBox("Export as .PPT");
		chckbxExportAsPpt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!chckbxExportAsPpt.isSelected()) {
					for(JRadioButton jrb : exportOptions) {
						jrb.setEnabled(false);
					}
					label.setEnabled(false);
				} else {
					for(JRadioButton jrb : exportOptions) {
						jrb.setEnabled(true);
					}
					label.setEnabled(true);
				}
			}
		});
		chckbxExportAsPpt.setSelected(true);
		chckbxExportAsPpt.setFont(new Font("SansSerif", Font.PLAIN, 13));
		chckbxExportAsPpt.setBackground(new Color(0, 153, 255));
		chckbxExportAsPpt.setBounds(972, 219, 130, 25);
		panel4.add(chckbxExportAsPpt);
		
		JLabel lblExportAs = new JLabel("Export As");
		lblExportAs.setHorizontalAlignment(SwingConstants.CENTER);
		lblExportAs.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblExportAs.setBounds(972, 154, 162, 21);
		panel4.add(lblExportAs);
		
		JLabel lblIncludeWeekends = new JLabel("Include Weekends?");
		lblIncludeWeekends.setHorizontalAlignment(SwingConstants.CENTER);
		lblIncludeWeekends.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblIncludeWeekends.setBounds(39, 396, 150, 20);
		panel4.add(lblIncludeWeekends);
		
		final JRadioButton rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnYes.isSelected()) {
					for(JRadioButton rbd : incldWknds) {
						if(rbd.getText() != rdbtnYes.getText()) rbd.setSelected(false);
					}
					txtFldNumDays.setEnabled(false);
					includeWknds = true;
				}
			}
		});
		rdbtnYes.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnYes.setBackground(new Color(0, 153, 255));
		rdbtnYes.setBounds(40, 425, 127, 25);
		incldWknds.add(rdbtnYes);
		panel4.add(rdbtnYes);
		
		final JRadioButton rdbtnNo = new JRadioButton("No");
		rdbtnNo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNo.isSelected()) {
					for(JRadioButton rbd : incldWknds) {
						if(rbd.getText() != rdbtnNo.getText()) rbd.setSelected(false);
					}
					txtFldNumDays.setEnabled(true);
					includeWknds = false;
				}
			}
		});
		rdbtnNo.setSelected(true);
		rdbtnNo.setBackground(new Color(0, 153, 255));
		rdbtnNo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnNo.setBounds(40, 455, 127, 25);
		incldWknds.add(rdbtnNo);
		panel4.add(rdbtnNo);
		
		JLabel lblHowManyWeekend = new JLabel("How many weekend days intersect?");
		lblHowManyWeekend.setHorizontalAlignment(SwingConstants.CENTER);
		lblHowManyWeekend.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblHowManyWeekend.setBounds(12, 490, 275, 20);
		panel4.add(lblHowManyWeekend);
		
		txtFldNumDays = new JTextField();
		txtFldNumDays.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldNumDays.setText("-1");
		txtFldNumDays.setBounds(40, 523, 116, 22);
		panel4.add(txtFldNumDays);
		txtFldNumDays.setColumns(10);
		
		final JButton btnGenerateReport = new JButton("Generate Report");
		btnGenerateReport.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnYes.isSelected() || (rdbtnNo.isSelected() && (txtFldNumDays.getText() != "" || txtFldNumDays.getText() != " ") && 
						Integer.parseInt(txtFldNumDays.getText()) >= 0)) {
					if(chckbxExportAsPpt.isSelected()) {
						List2PPT l2p = new List2PPT(sprints, issues, taskTypes, priorities, includeWknds,
								Integer.parseInt((txtFldNumDays.getText())));
						l2p.loadFileLocation();
						if(rbSprintReport.isSelected()) l2p.writeReport(0);
						else if(rbQuarterlyReport.isSelected()) l2p.writeReport(1);
					}
					if(chckbxExportAsXlsx.isSelected()) {
						List2Excel l2e = new List2Excel(issues, priorities, taskTypes);
						l2e.loadFileLocation();
						l2e.getStatistics();
						l2e.populateSheet("Report");
					}
				} else {
					if(Integer.parseInt(txtFldNumDays.getText()) >= 0) {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    "You have forgotten to input the number of weekend days are in your sprint!"
							    + " It is a negative number.",
							    "Issue trying to create report!",
							    JOptionPane.ERROR_MESSAGE);
					} else if (txtFldNumDays.getText() == "" || txtFldNumDays.getText() == " ") {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    "You have forgotten to input the number of weekend days are in your sprint!",
							    "Issue trying to create report!",
							    JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    "Unknown error -- Please submit this to the Issues GitHub page for J2P. \n"
							    + "Link: https://github.com/MichaelGardone/Jira2Pres/issues",
							    "Issue trying to create report!",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnGenerateReport.setToolTipText("Generate a report from the selected options and issues.");
		btnGenerateReport.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnGenerateReport.setEnabled(false);
		btnGenerateReport.setBounds(87, 228, 162, 25);
		panel4.add(btnGenerateReport);
		
		JButton btnGenerateReportFrom = new JButton("Generate Report From All");
		btnGenerateReportFrom.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(rdbtnYes.isSelected() || (rdbtnNo.isSelected() && (txtFldNumDays.getText() != "" || txtFldNumDays.getText() != " ") && 
						Integer.parseInt(txtFldNumDays.getText()) >= 0)) {
					if(chckbxExportAsPpt.isSelected()) {
						List2PPT l2p = new List2PPT(sprints, issues, taskTypes, priorities, includeWknds,
								Integer.parseInt((txtFldNumDays.getText())));
						l2p.loadFileLocation();
						if(rbSprintReport.isSelected()) l2p.writeReport(0);
						else if(rbQuarterlyReport.isSelected()) l2p.writeReport(1);
					}
					if(chckbxExportAsXlsx.isSelected()) {
						List2Excel l2e = new List2Excel(issues, priorities, taskTypes);
						l2e.loadFileLocation();
						l2e.getStatistics();
						l2e.populateSheet("Report");
					}
				} else {
					if(Integer.parseInt(txtFldNumDays.getText()) >= 0) {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    "You have forgotten to input the number of weekend days are in your sprint!"
							    + " It is a negative number.",
							    "Issue trying to create report!",
							    JOptionPane.ERROR_MESSAGE);
					} else if (txtFldNumDays.getText() == "" || txtFldNumDays.getText() == " ") {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    "You have forgotten to input the number of weekend days are in your sprint!",
							    "Issue trying to create report!",
							    JOptionPane.ERROR_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    "Unknown error -- Please submit this to the Issues GitHub page for J2P. \n"
							    + "Link: https://github.com/MichaelGardone/Jira2Pres/issues",
							    "Issue trying to create report!",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		btnGenerateReportFrom.setToolTipText("Generate a report from all of the issues.");
		btnGenerateReportFrom.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnGenerateReportFrom.setBounds(65, 178, 210, 25);
		panel4.add(btnGenerateReportFrom);
		
		SortedComboBoxModel scbm = new SortedComboBoxModel(new String[] { "Story Points" } );
		
		panel3 = new JPanel();
		panel3.setBackground(new Color(0, 153, 255));
		panel3.setBounds(0, 0, 1200, 800);
		frmJirapresentation.getContentPane().add(panel3);
		panel3.setLayout(null);
		panel3.setVisible(false);
		
		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(450, 150, 600, 530);
		panel3.add(scrollPane_1);
		
		JButton btnDslctCrrnt_2 = new JButton("Deselect Current");
		btnDslctCrrnt_2.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnDslctCrrnt_2.setEnabled(false);
		btnDslctCrrnt_2.setBounds(160, 150, 162, 25);
		panel3.add(btnDslctCrrnt_2);
		
		JButton btnSlctAll = new JButton("Select All");
		btnSlctAll.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnSlctAll.setEnabled(false);
		btnSlctAll.setBounds(160, 190, 162, 25);
		panel3.add(btnSlctAll);
		
		final JButton btnGetIssues = new JButton("Get Issues");
		btnGetIssues.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(sprintIds.size() > 0 && slctTrackMethod.getSelectedItem() != null) {
					connection.shutDown();
					String result = "";
					int c = 0;
					for(int i : sprintIds) {
						if(!cp.doesConnectionExist("Sprint " + i)) {
							connection = new IssueConnection(url, user, pass);
						} else {
							connection = cp.getConnection("Sprint " + i);
						}
						result = connection.connect(boardSprintDict.get(i), i);
						if(result == "Success") {
							cp.addToPool("Sprint " + i, connection);
							SprintObject obj = null;
							for(SprintObject o : sprints) { if(o.getId() == i) { obj = o; break; } }
							issues.addAll(j2o.jsonToIssue(new StringReader(connection.getResult()),
									cfoDict.get(slctTrackMethod.getSelectedItem().toString()).getIdAsString(),
									obj));
						} else {
							JOptionPane.showMessageDialog(frmJirapresentation,
								    result + "\nError occured in sprint " + i + "! Will not be adding this one to the sprint list...",
								    "Issue trying to connect!",
								    JOptionPane.ERROR_MESSAGE);
						}
						connection.shutDown();
						c++;
					}
					jList = listGen.generateIssueList(issues, issueIds, btnGenerateReport);
					scrollPane_2.setViewportView(jList);
					panel3.setVisible(false);
					panel4.setVisible(true);
				} else if (sprintIds.size() <= 0 && slctTrackMethod.getSelectedItem() == "0") {
					JOptionPane.showMessageDialog(frmJirapresentation,
						    "You have not selected any Sprints or how you tracked your Sprints.",
						    "Incorrect Parameters",
						    JOptionPane.ERROR_MESSAGE);
				} else if (sprintIds.size() > 0 && slctTrackMethod.getSelectedItem() == "0") {
					JOptionPane.showMessageDialog(frmJirapresentation,
						    "You have not selected how you tracked your Sprints.",
						    "Incorrect Parameter",
						    JOptionPane.ERROR_MESSAGE);
				} else if (sprintIds.size() < 0 && slctTrackMethod.getSelectedItem() != "0") {
					JOptionPane.showMessageDialog(frmJirapresentation,
						    "You have not selected any Sprints.",
						    "Incorrect Parameter",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnGetIssues.setToolTipText("Get issues from the selected sprints.");
		btnGetIssues.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnGetIssues.setEnabled(false);
		btnGetIssues.setBounds(160, 228, 162, 25);
		panel3.add(btnGetIssues);
		
		JLabel lblStoryPointField = new JLabel("Story Point Field Id");
		lblStoryPointField.setToolTipText("");
		lblStoryPointField.setHorizontalAlignment(SwingConstants.CENTER);
		lblStoryPointField.setForeground(new Color(0, 0, 0));
		lblStoryPointField.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
		lblStoryPointField.setBounds(160, 280, 162, 25);
		panel3.add(lblStoryPointField);
		
		txtFldNewOption = new JTextField();
		txtFldNewOption.setFont(new Font("SansSerif", Font.PLAIN, 16));
		txtFldNewOption.setBounds(37, 423, 218, 33);
		panel3.add(txtFldNewOption);
		txtFldNewOption.setColumns(10);
		
		JButton btnAddOption = new JButton("Add Option");
		btnAddOption.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(txtFldNewOption.getText() != "") {
					slctTrackMethod.addItem(txtFldNewOption.getText());
					txtFldNewOption.setText("");
				}
			}
		});
		btnAddOption.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnAddOption.setBounds(275, 428, 114, 25);
		panel3.add(btnAddOption);
		slctTrackMethod = new JComboBox(scbm);
		slctTrackMethod.setBackground(new Color(0, 153, 255));
		slctTrackMethod.setFont(new Font("SansSerif", Font.PLAIN, 16));
		slctTrackMethod.setBounds(160, 320, 167, 33);
		panel3.add(slctTrackMethod);
		
		JLabel lblAddAnotherField = new JLabel("Add another field here");
		lblAddAnotherField.setHorizontalAlignment(SwingConstants.CENTER);
		lblAddAnotherField.setFont(new Font("SansSerif", Font.ITALIC, 16));
		lblAddAnotherField.setBounds(144, 394, 175, 16);
		panel3.add(lblAddAnotherField);
		
		panel2 = new JPanel();
		panel2.setBackground(new Color(0, 153, 255));
		panel2.setBounds(0, 0, 1200, 800);
		frmJirapresentation.getContentPane().add(panel2);
		panel2.setLayout(null);
		panel2.setVisible(false);
		
		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(320, 150, 600, 530);
		panel2.add(scrollPane);
		
		btnDeselectCurrent = new JButton("Deselect Current");
		btnDeselectCurrent.setEnabled(false);
		btnDeselectCurrent.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Implement the deselect all feature
			}
		});
		btnDeselectCurrent.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnDeselectCurrent.setBounds(87, 150, 162, 25);
		panel2.add(btnDeselectCurrent);
		
		btnSelectAll = new JButton("Select All");
		btnSelectAll.setEnabled(false);
		btnSelectAll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Implement the select all feature
			}
		});
		btnSelectAll.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnSelectAll.setBounds(87, 190, 162, 25);
		panel2.add(btnSelectAll);
		
		btnGetSprints = new JButton("Get Sprints");
		btnGetSprints.setEnabled(false);
		btnGetSprints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(boardIds.size() > 0) {
					connection.shutDown();
					String result = "";
					for(int i : boardIds) {
						if(!cp.doesConnectionExist("Board " + i)) {
							connection = new SprintConnection(url, user, pass);
						} else {
							connection = cp.getConnection("Board " + i);
						}
						result = connection.connect(i);
						if(result == "Success") {
							cp.addToPool("Board " + i, connection);
							for(BoardObject bo : boards) {
								if(bo.getId() == i) {
									sprints.addAll(j2o.jsonToSprintObj(new StringReader(connection.getResult()), bo));
								}
							}
							for(SprintObject so : sprints) {
								boardSprintDict.put(so.getId(), i);
							}
						} else {
							JOptionPane.showMessageDialog(frmJirapresentation,
								    result + "\nError occured in sprint " + i + "! Will not be adding this one to the sprint list...",
								    "Issue trying to connect!",
								    JOptionPane.ERROR_MESSAGE);
						}
						connection.shutDown();
					}
					jList = listGen.generateSprintList(sprints, sprintIds, btnGetIssues);
					scrollPane_1.setViewportView(jList);
					panel2.setVisible(false);
					panel3.setVisible(true);
				}
			}
			
		});
		btnGetSprints.setToolTipText("Get all Sprints related to the selected boards");
		btnGetSprints.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnGetSprints.setBounds(87, 228, 162, 25);
		panel2.add(btnGetSprints);
		
		panel1 = new JPanel();
		panel1.setBackground(new Color(0, 153, 255));
		panel1.setBounds(0, 0, 1200, 800);
		frmJirapresentation.getContentPane().add(panel1);
		panel1.setLayout(null);
		
		JLabel lblInsertLinkHere = new JLabel("Please insert the link to JIRA here.");
		lblInsertLinkHere.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsertLinkHere.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblInsertLinkHere.setBounds(458, 159, 301, 16);
		panel1.add(lblInsertLinkHere);
		
		urlField = new JTextField();
		urlField.setToolTipText("Along this format: https://example:port/");
		urlField.setFont(new Font("SansSerif", Font.PLAIN, 20));
		urlField.setColumns(10);
		urlField.setBounds(425, 186, 359, 40);
		panel1.add(urlField);
		
		JLabel lblInsertUser = new JLabel("Please insert your username to JIRA here.");
		lblInsertUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsertUser.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblInsertUser.setBounds(415, 276, 379, 21);
		panel1.add(lblInsertUser);
		
		userField = new JTextField();
		userField.setToolTipText("example or example@example.com");
		userField.setFont(new Font("SansSerif", Font.PLAIN, 20));
		userField.setColumns(10);
		userField.setBounds(426, 310, 358, 40);
		panel1.add(userField);
		
		JLabel lblInsertPass = new JLabel("Please insert your password to JIRA here.");
		lblInsertPass.setHorizontalAlignment(SwingConstants.CENTER);
		lblInsertPass.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblInsertPass.setBounds(415, 393, 379, 21);
		panel1.add(lblInsertPass);
		
		passField = new JPasswordField();
		passField.setToolTipText("$up3r$3r31P@ssword");
		passField.setBounds(426, 427, 358, 40);
		panel1.add(passField);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(urlField.getText() != "" && userField.getText() != "" && passField.getPassword().length != 0) {
					connection = new PrioritiesConnection(urlField.getText(), "", new char[] {});
					String result = connection.connect();
					if(result != "Success") {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    result,
							    "Issue trying to connect for priorities!",
							    JOptionPane.ERROR_MESSAGE);
					}
					priorities = j2o.jsonToPriority(new StringReader(connection.getResult()));
					cp.addToPool("Priorities", connection);
					connection.shutDown();
					connection = new TaskTypeConnection(urlField.getText(), userField.getText(), passField.getPassword());
					result = connection.connect();
					if(result != "Success") {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    result,
							    "Issue trying to connect for task types!",
							    JOptionPane.ERROR_MESSAGE);
					}
					taskTypes = j2o.jsonToTaskType(new StringReader(connection.getResult()));
					cp.addToPool("Task Types", connection);
					connection.shutDown();
					connection = new TaskLengthConnection(urlField.getText(), userField.getText(), passField.getPassword());
					result = connection.connect();
					if(result != "Success") {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    result,
							    "Issue trying to connect for custom fields!",
							    JOptionPane.ERROR_MESSAGE);
					}
					cfoDict = j2o.jsonToCFObj(new StringReader(connection.getResult()));
					cp.addToPool("Custom Fields", connection);
					connection.shutDown();
					connection = new BoardConnection(urlField.getText(), userField.getText(), passField.getPassword());
					result = connection.connect();
					if(result == "Success") {
						url = connection.getUrl();
						user = connection.getUser();
						pass = connection.getPass();
						cp.addToPool("Boards", connection);
						boards = j2o.jsonToBoard(new StringReader(connection.getResult()));
						jList = listGen.generateBoardList(boards, boardIds, btnGetSprints);
						scrollPane.setViewportView(jList);
						panel1.setVisible(false);
						panel2.setVisible(true);
						passField.setText("");
						urlField.setText("");
						userField.setText("");
					} else {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    result,
							    "Issue trying to connect!",
							    JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(frmJirapresentation,
						    "One (or more) of the fields isn't filled out. J2P refuses to process request.",
						    "Fields not filled out!",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnConnect.setFont(new Font("SansSerif", Font.PLAIN, 16));
		btnConnect.setBounds(650, 506, 100, 25);
		panel1.add(btnConnect);
		
		JLabel lblPleaseInsertThe = new JLabel("Please insert the link to JIRA here.");
		lblPleaseInsertThe.setBounds(458, 159, 301, 16);
		panel1.add(lblPleaseInsertThe);
		lblPleaseInsertThe.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseInsertThe.setFont(new Font("SansSerif", Font.BOLD, 18));
		
		JButton btnClear = new JButton("Clear");
		btnClear.setBounds(468, 506, 100, 25);
		panel1.add(btnClear);
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				passField.setText("");
				urlField.setText("");
				userField.setText("");
			}
		});
		btnClear.setFont(new Font("SansSerif", Font.PLAIN, 16));
		
		JLabel lblVer = new JLabel("Ver: " + majVersion + "." + minVersion + "." + buildNum);
		lblVer.setFont(new Font("SansSerif", Font.ITALIC, 14));
		lblVer.setBounds(12, 725, 183, 21);
		panel1.add(lblVer);
	}
	
	public void logout() {
		cp.clearPool();
	}
}
