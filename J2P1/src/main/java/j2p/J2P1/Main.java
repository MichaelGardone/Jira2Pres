package j2p.J2P1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import j2p.J2P1.connections.BoardConnection;
import j2p.J2P1.connections.Connection;
import j2p.J2P1.connections.ConnectionPool;
import j2p.J2P1.models.SearchableModel;
import j2p.J2P1.objects.BoardObject;
import j2p.J2P1.objects.IssueObject;
import j2p.J2P1.translators.Json2Object;

public class Main {
	
	private int majVer = 0;
	private int minVer = 0;
	private long buildNum = 0;
	
	private List<BoardObject> boards = new ArrayList<BoardObject>();
	private List<IssueObject> issues = new ArrayList<IssueObject>();
	
	private Connection connection = null;
	private ConnectionPool cp = new ConnectionPool();
	private Json2Object j2o = new Json2Object();
	
	private JList boardList = null;
	private JList sprintList = null;
	private JList issuesList = null;
	
	private JFrame frmJirapresentation;
	private JTextField txtFldURL;
	private JTextField txtFldUser;
	private JLabel lblVersion;
	private JLabel lblVer;
	private JLabel lblBuildNum;
	private JLabel lblBuild;
	private JButton btnChangelog;
	private JButton btnReportIssue;
	private JButton btnGithub;
	private JButton btnDonate;
	private JPanel panel;
	private JPanel versionInfoPanel;
	private JPanel panel_1;
	private JLabel lblSelectBoard;
	private JButton btnGetSprints;
	private JLabel lblSelectedBoards;
	private JLabel lblBoardList;
	private JButton btnSelectAll;
	private JButton btnDeselectAll;
	private JScrollPane spBoards;
	private JLabel lblSearchForBoards;
	private JComboBox cbSearchBoards;
	private JTextField txtFldSearchBoards;
	private JPanel panel_2;
	private JLabel lblSelectSprints;
	private JButton btnGetIssues;
	private JButton btnSelectAll2;
	private JButton btnDeselectAll2;
	private JLabel lblSelectedSprints;
	private JLabel lblSprintList;
	private JScrollPane spSprints;
	private JLabel lblSearchForSprints;
	private JComboBox cbSprintSearch;
	private JTextField txtFldSprintSearch;
	private JLabel lblFilterOutOpen;
	private JRadioButton rdbtnYes;
	private JRadioButton rdbtnNo;
	private JPanel panel_3;
	private JButton btnBack;
	private JButton button;
	private JLabel lblSelectIssues;
	private JButton btnGenerateReportFrom;
	private JButton btnGenerateReportFrom_1;
	private JLabel lblReportOptions;
	private JCheckBox chckbxNewCheckBox;
	private JCheckBox chckbxCreatePowerpointppt;
	private JComboBox comboBox;
	private JScrollPane spIssueList;
	private JLabel lblIncludeOpen;
	private JRadioButton rdbtnYes_1;
	private JRadioButton rdbtnNo_1;
	private JLabel lblIncludeInprogress;
	private JRadioButton rdbtnYes_2;
	private JRadioButton rdbtnNo_2;
	private JPasswordField pfFldPass;
	
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
		//fileLoad();
		
		frmJirapresentation = new JFrame();
		frmJirapresentation.getContentPane().setBackground(new Color(0, 153, 255));
		frmJirapresentation.getContentPane().setLayout(null);
		frmJirapresentation.setBackground(new Color(0, 153, 255));
		frmJirapresentation.setFont(new Font("SansSerif", Font.PLAIN, 16));
		frmJirapresentation.setTitle("JIRA2Presentation");
		frmJirapresentation.setMinimumSize(new Dimension(1200, 800));
		frmJirapresentation.setPreferredSize(new Dimension(1200, 800));
		frmJirapresentation.setBounds(100, 100, 1200, 800);
		frmJirapresentation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel_1 = new JPanel();
		panel_1.setVisible(false);
		
		panel = new JPanel();
		panel.setBackground(new Color(85, 141, 252));
		panel.setBounds(0, 0, frmJirapresentation.getWidth(), frmJirapresentation.getHeight());
		frmJirapresentation.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTabbedPane mainPane = new JTabbedPane(JTabbedPane.TOP);
		mainPane.setPreferredSize(new Dimension(650, 600));
		mainPane.setBorder(null);
		mainPane.setBackground(new Color(0, 153, 255));
		mainPane.setBounds(420, 110, 650, 600);
		panel.add(mainPane);
		
		versionInfoPanel = new JPanel();
		mainPane.addTab("Version Info", null, versionInfoPanel, null);
		mainPane.setEnabledAt(0, true);
		mainPane.setBackgroundAt(0, new Color(0, 153, 255));
		versionInfoPanel.setLayout(null);
		
		lblVersion = new JLabel("Version");
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setFont(new Font("SansSerif", Font.ITALIC, 18));
		lblVersion.setBounds(275, 150, 75, 20);
		versionInfoPanel.add(lblVersion);
		
		lblVer = new JLabel(majVer + "." + minVer);
		lblVer.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setBounds(275, 180, 75, 20);
		versionInfoPanel.add(lblVer);
		
		lblBuildNum = new JLabel("Build");
		lblBuildNum.setFont(new Font("SansSerif", Font.ITALIC, 18));
		lblBuildNum.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuildNum.setBounds(275, 230, 70, 20);
		versionInfoPanel.add(lblBuildNum);
		
		lblBuild = new JLabel(buildNum+"");
		lblBuild.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuild.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblBuild.setBounds(275, 260, 75, 20);
		versionInfoPanel.add(lblBuild);
		
		btnChangelog = new JButton("Changelog");
		btnChangelog.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnChangelog.setBounds(35, 380, 120, 25);
		versionInfoPanel.add(btnChangelog);
		
		btnReportIssue = new JButton("Report Issue");
		btnReportIssue.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnReportIssue.setBounds(185, 380, 120, 25);
		versionInfoPanel.add(btnReportIssue);
		
		btnGithub = new JButton("GitHub");
		btnGithub.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnGithub.setBounds(335, 380, 120, 25);
		versionInfoPanel.add(btnGithub);
		
		btnDonate = new JButton("Donate");
		btnDonate.setFont(new Font("SansSerif", Font.PLAIN, 14));
		btnDonate.setBounds(485, 380, 120, 25);
		versionInfoPanel.add(btnDonate);
		
		JPanel aboutThisPanel = new JPanel();
		mainPane.addTab("About the Project", null, aboutThisPanel, null);
		aboutThisPanel.setLayout(null);
		
		JLabel lblAboutTheProject = new JLabel("About the Project");
		lblAboutTheProject.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
		lblAboutTheProject.setBounds(12, 15, 140, 16);
		aboutThisPanel.add(lblAboutTheProject);
		
		JLabel lblTodo = new JLabel("TODO");
		lblTodo.setFont(new Font("SansSerif", Font.ITALIC, 14));
		lblTodo.setBounds(12, 42, 621, 141);
		aboutThisPanel.add(lblTodo);
		
		JLabel lblAboutMe = new JLabel("About Me");
		lblAboutMe.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 16));
		lblAboutMe.setBounds(12, 200, 140, 16);
		aboutThisPanel.add(lblAboutMe);
		
		JLabel label_1 = new JLabel("TODO");
		label_1.setFont(new Font("SansSerif", Font.ITALIC, 14));
		label_1.setBounds(12, 225, 621, 141);
		aboutThisPanel.add(label_1);
		mainPane.setEnabledAt(1, true);
		
		JLabel lblNewLabel = new JLabel("JIRA URL");
		lblNewLabel.setBounds(150, 110, 100, 20);
		panel.add(lblNewLabel);
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setLabelFor(txtFldURL);
		
		txtFldURL = new JTextField();
		txtFldURL.setBounds(100, 140, 200, 25);
		panel.add(txtFldURL);
		txtFldURL.setHorizontalAlignment(SwingConstants.LEFT);
		txtFldURL.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldURL.setColumns(10);
		
		JLabel lblJiraUser = new JLabel("JIRA Username");
		lblJiraUser.setBounds(130, 200, 140, 20);
		panel.add(lblJiraUser);
		lblJiraUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblJiraUser.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		txtFldUser = new JTextField();
		txtFldUser.setBounds(100, 230, 200, 25);
		panel.add(txtFldUser);
		txtFldUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtFldUser.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldUser.setColumns(10);
		
		JLabel lblJiraPassword = new JLabel("JIRA Password");
		lblJiraPassword.setHorizontalAlignment(SwingConstants.CENTER);
		lblJiraPassword.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblJiraPassword.setBounds(130, 290, 140, 20);
		panel.add(lblJiraPassword);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(pfFldPass.getPassword().length <= 0 || txtFldURL.getText() == "" || txtFldUser.getText() == "") {
					JOptionPane.showMessageDialog(frmJirapresentation,
						    "You appear to have forgotten to fill out a field!",
						    "Empty field(s)!",
						    JOptionPane.ERROR_MESSAGE);
				} else {
					connection = new BoardConnection(txtFldURL.getText(), txtFldUser.getText(), pfFldPass.getPassword());
					String result = connection.connect();
					if(result == "") {
						boards = j2o.jsonToBoard(new StringReader(connection.getResult()));
						panel.setEnabled(false);
						panel.setVisible(false);
						panel_1.setVisible(true);
						connection.shutDown();
						cp.addToPool("Boards", connection);
						DefaultListModel dlm = new DefaultListModel();
						for(BoardObject b : boards) {
							dlm.addElement(b.getName());
						}
						boardList = new JList(dlm);
						spBoards = new JScrollPane(boardList);
						spBoards.setBounds(380, 150, 500, 550);
						panel_1.add(spBoards);
						panel_1.repaint();
					} else {
						JOptionPane.showMessageDialog(frmJirapresentation,
							    result,
							    "Issue trying to connect!",
							    JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});
		
		pfFldPass = new JPasswordField();
		pfFldPass.setFont(new Font("SansSerif", Font.PLAIN, 14));
		pfFldPass.setBounds(100, 320, 200, 25);
		panel.add(pfFldPass);
		btnConnect.setBounds(130, 380, 140, 25);
		panel.add(btnConnect);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pfFldPass.setText("");
				txtFldURL.setText("");
				txtFldUser.setText("");
			}
		});
		btnClear.setBounds(130, 460, 140, 25);
		panel.add(btnClear);
		
		panel_1.setBackground(new Color(85, 141, 252));
		panel_1.setBounds(0, 0, frmJirapresentation.getWidth(), frmJirapresentation.getHeight());
		frmJirapresentation.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		lblSelectBoard = new JLabel("Select Boards");
		lblSelectBoard.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 24));
		lblSelectBoard.setBounds(20, 40, 170, 30);
		panel_1.add(lblSelectBoard);
		
		btnGetSprints = new JButton("Get Sprints");
		btnGetSprints.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(boardList.getSelectedValuesList().get(0));
			}
		});
		btnGetSprints.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnGetSprints.setBounds(70, 150, 160, 25);
		panel_1.add(btnGetSprints);
		
		btnSelectAll = new JButton("Select All");
		btnSelectAll.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnSelectAll.setBounds(70, 200, 160, 25);
		panel_1.add(btnSelectAll);
		
		btnDeselectAll = new JButton("Deselect All");
		btnDeselectAll.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnDeselectAll.setBounds(70, 250, 160, 25);
		panel_1.add(btnDeselectAll);
		
		lblSelectedBoards = new JLabel("Selected Boards:");
		lblSelectedBoards.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblSelectedBoards.setBounds(70, 450, 150, 20);
		panel_1.add(lblSelectedBoards);
		
		lblBoardList = new JLabel("N/A");
		lblBoardList.setVerticalAlignment(SwingConstants.TOP);
		lblBoardList.setFont(new Font("SansSerif", Font.ITALIC, 16));
		lblBoardList.setBounds(70, 500, 300, 200);
		panel_1.add(lblBoardList);
		
		lblSearchForBoards = new JLabel("Search for Boards");
		lblSearchForBoards.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchForBoards.setToolTipText("This feature has not been built yet");
		lblSearchForBoards.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblSearchForBoards.setBounds(960, 150, 150, 20);
		panel_1.add(lblSearchForBoards);
		
		cbSearchBoards = new JComboBox();
		cbSearchBoards.setEnabled(false);
		cbSearchBoards.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cbSearchBoards.setModel(new DefaultComboBoxModel(new String[] {"By Name", "By Id"}));
		cbSearchBoards.setBounds(960, 260, 150, 22);
		panel_1.add(cbSearchBoards);
		
		txtFldSearchBoards = new JTextField();
		txtFldSearchBoards.setEnabled(false);
		txtFldSearchBoards.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldSearchBoards.setBounds(935, 200, 200, 25);
		panel_1.add(txtFldSearchBoards);
		txtFldSearchBoards.setColumns(10);
		
		panel_2 = new JPanel();
		panel_2.setVisible(false);
		
		button = new JButton("Back");
		button.setFont(new Font("SansSerif", Font.ITALIC, 16));
		button.setBounds(70, 300, 160, 25);
		panel_1.add(button);
		panel_2.setBackground(new Color(85, 141, 252));
		panel_2.setBounds(0, 0, frmJirapresentation.getWidth(), frmJirapresentation.getHeight());
		frmJirapresentation.getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		lblSelectSprints = new JLabel("Select Sprints");
		lblSelectSprints.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 24));
		lblSelectSprints.setBounds(20, 40, 170, 30);
		panel_2.add(lblSelectSprints);
		
		btnGetIssues = new JButton("Get Issues");
		btnGetIssues.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnGetIssues.setBounds(70, 150, 160, 25);
		panel_2.add(btnGetIssues);
		
		btnSelectAll2 = new JButton("Select All");
		btnSelectAll2.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnSelectAll2.setBounds(70, 200, 160, 25);
		panel_2.add(btnSelectAll2);
		
		btnDeselectAll2 = new JButton("Deselect All");
		btnDeselectAll2.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnDeselectAll2.setBounds(70, 250, 160, 25);
		panel_2.add(btnDeselectAll2);
		
		lblSelectedSprints = new JLabel("Selected Sprints:");
		lblSelectedSprints.setFont(new Font("SansSerif", Font.BOLD, 18));
		lblSelectedSprints.setBounds(70, 450, 140, 20);
		panel_2.add(lblSelectedSprints);
		
		lblSprintList = new JLabel("N/A");
		lblSprintList.setVerticalAlignment(SwingConstants.TOP);
		lblSprintList.setFont(new Font("SansSerif", Font.ITALIC, 16));
		lblSprintList.setBounds(70, 500, 300, 200);
		panel_2.add(lblSprintList);
		
		spSprints = new JScrollPane();
		spSprints.setBounds(380, 150, 500, 550);
		panel_2.add(spSprints);
		
		lblSearchForSprints = new JLabel("Search for Sprints");
		lblSearchForSprints.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearchForSprints.setToolTipText("This feature has not been built yet");
		lblSearchForSprints.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblSearchForSprints.setBounds(960, 150, 150, 20);
		panel_2.add(lblSearchForSprints);
		
		cbSprintSearch = new JComboBox();
		cbSprintSearch.setModel(new DefaultComboBoxModel(new String[] {"By Name", "By Id", "By Start Date", "By End Date"}));
		cbSprintSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));
		cbSprintSearch.setEnabled(false);
		cbSprintSearch.setBounds(960, 260, 150, 22);
		panel_2.add(cbSprintSearch);
		
		txtFldSprintSearch = new JTextField();
		txtFldSprintSearch.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldSprintSearch.setEnabled(false);
		txtFldSprintSearch.setColumns(10);
		txtFldSprintSearch.setBounds(935, 200, 200, 25);
		panel_2.add(txtFldSprintSearch);
		
		lblFilterOutOpen = new JLabel("Include Open?");
		lblFilterOutOpen.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterOutOpen.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblFilterOutOpen.setBounds(975, 350, 125, 20);
		panel_2.add(lblFilterOutOpen);
		
		rdbtnYes = new JRadioButton("Yes");
		rdbtnYes.setBackground(new Color(85, 141, 252));
		rdbtnYes.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnYes.setBounds(975, 380, 127, 25);
		panel_2.add(rdbtnYes);
		
		rdbtnNo = new JRadioButton("No");
		rdbtnNo.setBackground(new Color(85, 141, 252));
		rdbtnNo.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnNo.setSelected(true);
		rdbtnNo.setBounds(975, 410, 127, 25);
		panel_2.add(rdbtnNo);
		
		btnBack = new JButton("Back");
		btnBack.setFont(new Font("SansSerif", Font.ITALIC, 16));
		btnBack.setBounds(70, 300, 160, 25);
		panel_2.add(btnBack);
		
		panel_3 = new JPanel();
		panel_3.setVisible(false);
		panel_3.setBackground(new Color(85, 141, 252));
		panel_3.setBounds(0, 0, frmJirapresentation.getWidth(), frmJirapresentation.getHeight());
		frmJirapresentation.getContentPane().add(panel_3);
		panel_3.setLayout(null);
		
		lblSelectIssues = new JLabel("Select Issues");
		lblSelectIssues.setBounds(20, 40, 160, 32);
		lblSelectIssues.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 24));
		panel_3.add(lblSelectIssues);
		
		btnGenerateReportFrom = new JButton("Generate Report");
		btnGenerateReportFrom.setFont(new Font("Tahoma", Font.ITALIC, 14));
		btnGenerateReportFrom.setBounds(70, 150, 225, 25);
		panel_3.add(btnGenerateReportFrom);
		
		btnGenerateReportFrom_1 = new JButton("Generate Report From Selected");
		btnGenerateReportFrom_1.setFont(new Font("Tahoma", Font.ITALIC, 14));
		btnGenerateReportFrom_1.setBounds(70, 200, 230, 25);
		panel_3.add(btnGenerateReportFrom_1);
		
		lblReportOptions = new JLabel("Report Options");
		lblReportOptions.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblReportOptions.setBounds(70, 260, 125, 20);
		panel_3.add(lblReportOptions);
		
		chckbxNewCheckBox = new JCheckBox("Create Excel (.xls)");
		chckbxNewCheckBox.setSelected(true);
		chckbxNewCheckBox.setBackground(new Color(85, 141, 252));
		chckbxNewCheckBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
		chckbxNewCheckBox.setBounds(70, 300, 130, 25);
		panel_3.add(chckbxNewCheckBox);
		
		chckbxCreatePowerpointppt = new JCheckBox("Create PowerPoint (.ppt)");
		chckbxCreatePowerpointppt.setSelected(true);
		chckbxCreatePowerpointppt.setFont(new Font("SansSerif", Font.PLAIN, 12));
		chckbxCreatePowerpointppt.setBackground(new Color(85, 141, 252));
		chckbxCreatePowerpointppt.setBounds(70, 340, 170, 25);
		panel_3.add(chckbxCreatePowerpointppt);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Sprint Report", "Quarter Report (Short)"}));
		comboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
		comboBox.setBounds(70, 374, 170, 22);
		panel_3.add(comboBox);
		
		spIssueList = new JScrollPane();
		spIssueList.setBounds(380, 150, 500, 550);
		panel_3.add(spIssueList);
		
		lblIncludeOpen = new JLabel("Include Open?");
		lblIncludeOpen.setHorizontalAlignment(SwingConstants.CENTER);
		lblIncludeOpen.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblIncludeOpen.setBounds(960, 155, 120, 20);
		panel_3.add(lblIncludeOpen);
		
		rdbtnYes_1 = new JRadioButton("Yes");
		rdbtnYes_1.setBackground(new Color(85, 141, 252));
		rdbtnYes_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnYes_1.setSelected(true);
		rdbtnYes_1.setBounds(960, 184, 127, 25);
		panel_3.add(rdbtnYes_1);
		
		rdbtnNo_1 = new JRadioButton("No");
		rdbtnNo_1.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnNo_1.setBackground(new Color(85, 141, 252));
		rdbtnNo_1.setBounds(960, 214, 127, 25);
		panel_3.add(rdbtnNo_1);
		
		lblIncludeInprogress = new JLabel("Include In-Progress?");
		lblIncludeInprogress.setHorizontalAlignment(SwingConstants.CENTER);
		lblIncludeInprogress.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblIncludeInprogress.setBounds(960, 260, 160, 20);
		panel_3.add(lblIncludeInprogress);
		
		rdbtnYes_2 = new JRadioButton("Yes");
		rdbtnYes_2.setSelected(true);
		rdbtnYes_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnYes_2.setBackground(new Color(85, 141, 252));
		rdbtnYes_2.setBounds(960, 289, 127, 25);
		panel_3.add(rdbtnYes_2);
		
		rdbtnNo_2 = new JRadioButton("No");
		rdbtnNo_2.setFont(new Font("SansSerif", Font.PLAIN, 14));
		rdbtnNo_2.setBackground(new Color(85, 141, 252));
		rdbtnNo_2.setBounds(960, 319, 127, 25);
		panel_3.add(rdbtnNo_2);
	}

	private void fileLoad() {
		Properties properties = new Properties();
		URL resource = getClass().getResource("/resc/version.txt");
	    try {
			properties.load(new FileReader(new File(resource.getFile())));
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("Version: " + properties.get("majorversion"));
	}
}
