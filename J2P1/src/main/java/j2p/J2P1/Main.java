package j2p.J2P1;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JTextField;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.EtchedBorder;

public class Main {

	private JFrame frmJirapresentation;
	private JTextField txtFldURL;
	private JTextField txtFldUser;

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
		frmJirapresentation = new JFrame();
		frmJirapresentation.getContentPane().setBackground(new Color(0, 153, 255));
		frmJirapresentation.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("JIRA URL");
		lblNewLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(150, 80, 100, 20);
		frmJirapresentation.getContentPane().add(lblNewLabel);
		
		txtFldURL = new JTextField();
		lblNewLabel.setLabelFor(txtFldURL);
		txtFldURL.setHorizontalAlignment(SwingConstants.LEFT);
		txtFldURL.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldURL.setBounds(100, 110, 200, 25);
		frmJirapresentation.getContentPane().add(txtFldURL);
		txtFldURL.setColumns(10);
		
		JLabel lblJiraUser = new JLabel("JIRA Username");
		lblJiraUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblJiraUser.setFont(new Font("SansSerif", Font.BOLD, 16));
		lblJiraUser.setBounds(130, 170, 140, 20);
		frmJirapresentation.getContentPane().add(lblJiraUser);
		
		txtFldUser = new JTextField();
		txtFldUser.setHorizontalAlignment(SwingConstants.LEFT);
		txtFldUser.setFont(new Font("SansSerif", Font.PLAIN, 14));
		txtFldUser.setColumns(10);
		txtFldUser.setBounds(100, 200, 200, 25);
		frmJirapresentation.getContentPane().add(txtFldUser);
		frmJirapresentation.setBackground(new Color(0, 153, 255));
		frmJirapresentation.setFont(new Font("SansSerif", Font.PLAIN, 16));
		frmJirapresentation.setTitle("JIRA2Presentation");
		frmJirapresentation.setMinimumSize(new Dimension(1200, 800));
		frmJirapresentation.setPreferredSize(new Dimension(1200, 800));
		frmJirapresentation.setBounds(100, 100, 1200, 800);
		frmJirapresentation.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 153, 255));
		panel.setBounds(0, 0, frmJirapresentation.getWidth(), frmJirapresentation.getHeight());
		frmJirapresentation.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTabbedPane mainPane = new JTabbedPane(JTabbedPane.TOP);
		mainPane.setPreferredSize(new Dimension(650, 600));
		mainPane.setBorder(null);
		mainPane.setBackground(new Color(0, 153, 255));
		mainPane.setBounds(420, 110, 650, 600);
		panel.add(mainPane);
		
		JPanel versionInfoPanel = new JPanel();
		mainPane.addTab("Version Info", null, versionInfoPanel, null);
		mainPane.setEnabledAt(0, true);
		mainPane.setBackgroundAt(0, new Color(0, 153, 255));
		versionInfoPanel.setLayout(null);
		
		JLabel lblVersion = new JLabel("Version");
		lblVersion.setHorizontalAlignment(SwingConstants.CENTER);
		lblVersion.setFont(new Font("SansSerif", Font.ITALIC, 18));
		lblVersion.setBounds(275, 150, 75, 20);
		versionInfoPanel.add(lblVersion);
		
		JLabel lblVer = new JLabel("0.0");
		lblVer.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblVer.setHorizontalAlignment(SwingConstants.CENTER);
		lblVer.setBounds(275, 180, 75, 20);
		versionInfoPanel.add(lblVer);
		
		JLabel lblNewLabel_1 = new JLabel("Build");
		lblNewLabel_1.setFont(new Font("SansSerif", Font.ITALIC, 18));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(275, 230, 70, 20);
		versionInfoPanel.add(lblNewLabel_1);
		
		JLabel lblBuild = new JLabel("0");
		lblBuild.setHorizontalAlignment(SwingConstants.CENTER);
		lblBuild.setFont(new Font("SansSerif", Font.PLAIN, 16));
		lblBuild.setBounds(275, 260, 75, 20);
		versionInfoPanel.add(lblBuild);
		
		JPanel aboutThisPanel = new JPanel();
		mainPane.addTab("About the Project", null, aboutThisPanel, null);
		mainPane.setEnabledAt(1, true);
	}
}
