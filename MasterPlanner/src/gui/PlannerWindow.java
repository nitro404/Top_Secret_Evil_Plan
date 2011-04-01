package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import planner.*;
import settings.*;
import shared.*;

public class PlannerWindow extends JFrame implements ActionListener, WindowListener, Updatable {
	
	private Font m_consoleFont;
    private JTextArea m_consoleText;
    private JScrollPane m_consoleScrollPane;
	
	private boolean m_updating;
	
	private JMenuBar m_menuBar;
	
	private JMenu m_fileMenu;
	private JMenuItem m_fileConnectMenuItem;
	private JMenuItem m_fileDisconnectMenuItem;
	private JMenuItem m_fileStartSimulationMenuItem;
	private JMenuItem m_fileExitMenuItem;
    
	private JMenu m_editMenu;
	private JMenuItem m_taskEditorMenuItem; 
	private JMenu m_editModeMenu;
	private JRadioButtonMenuItem[] m_editModeMenuItem;
	private ButtonGroup m_editModeButtonGroup;
	private JMenu m_editColourMenu;
	private JMenuItem m_editColourSelectedMenuItem;
	private JMenuItem m_editColourMissingMenuItem;
	private JMenuItem m_editColourVertexMenuItem;
	private JMenuItem m_editColourEdgeMenuItem;
	private JMenuItem m_editColourRobotMenuItem;
	private JMenuItem m_editColourBlockMenuItem;
	private JMenuItem m_editColourPotMenuItem;
	private JMenuItem m_editColourDropOffLocationMenuItem;
	private JMenuItem m_editColourResetAllMenuItem;
	private JMenuItem m_editUpdateTrackerImageMenuItem;
	
	private JMenu m_settingsMenu;
	private JCheckBoxMenuItem m_settingsAutoConnectOnStartupMenuItem;
	private JCheckBoxMenuItem m_settingsTakeWebcamSnapshotOnStartupMenuItem;
	private JCheckBoxMenuItem m_useStaticStationImagesMenuItem;
	private JMenuItem m_staticStationImageFileNameFormatMenuItem;
	private JCheckBoxMenuItem m_settingsAutoScrollConsoleWindowMenuItem;
	private JMenu m_settingsSignalsMenu;
	private JRadioButtonMenuItem[] m_settingsSignalsMenuItem;
	private ButtonGroup m_settingsSignalsButtonGroup;
	private JMenuItem m_settingsNumberOfTrackersMenuItem;
	private JMenuItem m_settingsTimeLimitMenuItem;
	private JMenuItem m_settingsWebcamResolutionMenuItem;
	private JMenuItem m_settingsSaveMenuItem;
	
	private JMenu m_helpMenu;
	private JMenuItem m_helpAboutMenuItem;
	
	private JTable blocksTable4;
    private JTable blocksTable5;
    private JLabel consoleLabel;
    private JLabel currentTimeLabel;
    private JTextField currentTimeTextField;
    private JTextField estimatedPoseTextField1;
    private JTextField estimatedPoseTextField2;
    private JTextField estimatedPoseTextField3;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel11;
    private JPanel jPanel15;
    private JPanel jPanel16;
    private JPanel jPanel17;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane12;
    private JScrollPane jScrollPane13;
    private JSeparator jSeparator1;
    private JSeparator jSeparator10;
    private JSeparator jSeparator11;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JSeparator jSeparator7;
    private JSeparator jSeparator8;
    private JSeparator jSeparator9;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPane2;
    private JSplitPane jSplitPane3;
    private JTable jTable1;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    private JLabel maxTimeLabel;
    private JTextField maxTimeTextField;
    private JTextField poseTextField1;
    private JTextField poseTextField2;
    private JTextField poseTextField3;
    private JLabel robotIDLabel5;
    private JLabel robotIDLabel6;
    private JLabel robotIDLabel7;
    private JTextField robotIDTextField1;
    private JTextField robotIDTextField2;
    private JTextField robotIDTextField3;
    private JLabel robotInfoLabel5;
    private JLabel robotInfoLabel6;
    private JLabel robotInfoLabel7;
    private JPanel robotInfoPanel2;
    private JPanel robotInfoPanel3;
    private JPanel robotInfoPanel4;
    private JLabel robotNameLabel5;
    private JLabel robotNameLabel6;
    private JLabel robotNameLabel7;
    private JTextField robotNameTextField1;
    private JTextField robotNameTextField2;
    private JTextField robotNameTextField3;
    private JTabbedPane robotTabs;
    private JLabel spawnPoseLabel1;
    private JLabel spawnPoseLabel2;
    private JLabel spawnPoseLabel3;
    private JTextField spawnPoseTextField1;
    private JTextField spawnPoseTextField2;
    private JTextField spawnPoseTextField3;
    private JLabel stateLabel5;
    private JLabel stateLabel6;
    private JLabel stateLabel7;
    private JTextField stateTextField1;
    private JTextField stateTextField2;
    private JTextField stateTextField3;
    private JCheckBox taskCheckBox1;
    private JCheckBox taskCheckBox2;
    private JCheckBox taskCheckBox3;
    private JLabel tasksCompleteLabel1;
    private JLabel tasksCompleteLabel2;
    private JLabel tasksCompleteLabel3;
    private JLabel tasksLabel1;
    private JLabel tasksLabel2;
    private JLabel tasksLabel3;
    private JLabel timeElapsedLabel;
    private JTextField timeElapsedTextField;
    private JPanel timePanel;
    private JLabel timeRemainingLabel;
    private JTextField timeRemainingTextField;
    private JLabel titleLabel;
    private JLabel totalTasksCompleteLabel;
    private JLabel totalTasksCompleteLabel1;
    private JLabel totalTasksCompleteLabel2;
	
	private static final long serialVersionUID = 1L;
	
	public PlannerWindow() {
		setTitle("Planner Window");
		setSize(640, 905);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addWindowListener(this);
		
		m_updating = false;
		
		initMenu();
		initComponents();
		initLayout();
	}
	
	private void initMenu() {
	    m_menuBar = new JMenuBar();
        
        m_fileMenu = new JMenu("File");
        m_fileConnectMenuItem = new JMenuItem("Connect");
    	m_fileDisconnectMenuItem = new JMenuItem("Disconnect");
        m_fileStartSimulationMenuItem = new JMenuItem("Start Simulation");
        m_fileExitMenuItem = new JMenuItem("Exit");
        
        m_editMenu = new JMenu("Edit");
        m_taskEditorMenuItem = new JMenuItem("Task Editor");
    	m_editModeMenu = new JMenu("Editing Mode");
    	m_editModeMenuItem = new JRadioButtonMenuItem[EditMode.displayEditModes.length];
    	for(int i=0;i<EditMode.displayEditModes.length;i++) {
    		m_editModeMenuItem[i] = new JRadioButtonMenuItem(EditMode.displayEditModes[i]);
    	}
    	m_editColourMenu = new JMenu("Colours");
    	m_editColourSelectedMenuItem = new JMenuItem("Selected Colour");
    	m_editColourMissingMenuItem = new JMenuItem("Missing Colour");
    	m_editColourVertexMenuItem = new JMenuItem("Vertex Colour");
    	m_editColourEdgeMenuItem = new JMenuItem("Edge Colour");
    	m_editColourRobotMenuItem = new JMenuItem("Robot Colour");
    	m_editColourBlockMenuItem = new JMenuItem("Block Colour");
    	m_editColourPotMenuItem = new JMenuItem("Pot Colour");
    	m_editColourDropOffLocationMenuItem = new JMenuItem("Drop Off Location Colour");
    	m_editColourResetAllMenuItem = new JMenuItem("Reset All Colours");
    	m_editUpdateTrackerImageMenuItem = new JMenuItem("Update Tracker Image");
        
        m_settingsMenu = new JMenu("Settings");
        m_settingsAutoConnectOnStartupMenuItem = new JCheckBoxMenuItem("Auto-connect on Startup");
        m_settingsTakeWebcamSnapshotOnStartupMenuItem = new JCheckBoxMenuItem("Take Webcam Snapshot on Startup");
        m_useStaticStationImagesMenuItem = new JCheckBoxMenuItem("Use Static Station Images");
    	m_staticStationImageFileNameFormatMenuItem = new JMenuItem("Static Station Image File Name Format");
        m_settingsAutoScrollConsoleWindowMenuItem = new JCheckBoxMenuItem("Auto-scroll Console Window");
        m_settingsSignalsMenu = new JMenu("Signal Debugging");
        m_settingsSignalsMenuItem = new JRadioButtonMenuItem[SignalDebugLevel.signalDebugLevels.length];
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i] = new JRadioButtonMenuItem(SignalDebugLevel.signalDebugLevels[i]); 
        }
        m_settingsNumberOfTrackersMenuItem = new JMenuItem("Number of Trackers");
    	m_settingsTimeLimitMenuItem = new JMenuItem("Time Limit");
    	m_settingsWebcamResolutionMenuItem = new JMenuItem("Webcam Resolution");
        m_settingsSaveMenuItem = new JMenuItem("Save Settings");
        
        m_helpMenu = new JMenu("Help");
        m_helpAboutMenuItem = new JMenuItem("About");
        
        m_settingsAutoConnectOnStartupMenuItem.setSelected(SettingsManager.defaultAutoConnectOnStartup);
        m_settingsTakeWebcamSnapshotOnStartupMenuItem.setSelected(SettingsManager.defaultTakeWebcamSnapshotOnStartup);
        m_useStaticStationImagesMenuItem.setSelected(SettingsManager.defaultUseStaticStationImages);
        m_settingsAutoScrollConsoleWindowMenuItem.setSelected(SettingsManager.defaultAutoScrollConsoleWindow);
        m_editModeMenuItem[0].setSelected(true);
        m_settingsSignalsMenuItem[0].setSelected(true);
		m_fileDisconnectMenuItem.setEnabled(false);
		m_fileStartSimulationMenuItem.setEnabled(false);
        
        m_editModeButtonGroup = new ButtonGroup();
        m_settingsSignalsButtonGroup = new ButtonGroup();
        
        m_fileConnectMenuItem.addActionListener(this);
        m_fileDisconnectMenuItem.addActionListener(this);
        m_fileStartSimulationMenuItem.addActionListener(this);
        m_fileExitMenuItem.addActionListener(this);
        m_taskEditorMenuItem.addActionListener(this);
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeMenuItem[i].addActionListener(this);
        }
    	m_editColourSelectedMenuItem.addActionListener(this);
    	m_editColourMissingMenuItem.addActionListener(this);
    	m_editColourVertexMenuItem.addActionListener(this);
    	m_editColourEdgeMenuItem.addActionListener(this);
    	m_editColourRobotMenuItem.addActionListener(this);
    	m_editColourBlockMenuItem.addActionListener(this);
    	m_editColourPotMenuItem.addActionListener(this);
    	m_editColourDropOffLocationMenuItem.addActionListener(this);
    	m_editColourResetAllMenuItem.addActionListener(this);
        m_editUpdateTrackerImageMenuItem.addActionListener(this);
        m_settingsAutoConnectOnStartupMenuItem.addActionListener(this);
        m_settingsTakeWebcamSnapshotOnStartupMenuItem.addActionListener(this);
        m_useStaticStationImagesMenuItem.addActionListener(this);
    	m_staticStationImageFileNameFormatMenuItem.addActionListener(this);
        m_settingsAutoScrollConsoleWindowMenuItem.addActionListener(this);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenuItem[i].addActionListener(this);
        }
        m_settingsNumberOfTrackersMenuItem.addActionListener(this);
    	m_settingsTimeLimitMenuItem.addActionListener(this);
    	m_settingsWebcamResolutionMenuItem.addActionListener(this);
        m_settingsSaveMenuItem.addActionListener(this);
        m_helpAboutMenuItem.addActionListener(this);
        
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeButtonGroup.add(m_editModeMenuItem[i]);
        }
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsButtonGroup.add(m_settingsSignalsMenuItem[i]);
        }
        
        m_fileMenu.add(m_fileConnectMenuItem);
        m_fileMenu.add(m_fileDisconnectMenuItem);
        m_fileMenu.add(m_fileStartSimulationMenuItem);
        m_fileMenu.add(m_fileExitMenuItem);
        
        m_editMenu.add(m_taskEditorMenuItem);
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeMenu.add(m_editModeMenuItem[i]);
        }
        m_editMenu.add(m_editModeMenu);
		m_editColourMenu.add(m_editColourSelectedMenuItem);
		m_editColourMenu.add(m_editColourMissingMenuItem);
		m_editColourMenu.add(m_editColourVertexMenuItem);
		m_editColourMenu.add(m_editColourEdgeMenuItem);
		m_editColourMenu.add(m_editColourRobotMenuItem);
		m_editColourMenu.add(m_editColourBlockMenuItem);
		m_editColourMenu.add(m_editColourPotMenuItem);
		m_editColourMenu.add(m_editColourDropOffLocationMenuItem);
		m_editColourMenu.add(m_editColourResetAllMenuItem);
        m_editMenu.add(m_editColourMenu);
        m_editMenu.add(m_editUpdateTrackerImageMenuItem);
        
        m_settingsMenu.add(m_settingsAutoConnectOnStartupMenuItem);
        m_settingsMenu.add(m_settingsTakeWebcamSnapshotOnStartupMenuItem);
        m_settingsMenu.add(m_useStaticStationImagesMenuItem);
    	m_settingsMenu.add(m_staticStationImageFileNameFormatMenuItem);
        m_settingsMenu.add(m_settingsAutoScrollConsoleWindowMenuItem);
        for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
        	m_settingsSignalsMenu.add(m_settingsSignalsMenuItem[i]);
        }
        m_settingsMenu.add(m_settingsSignalsMenu);
        m_settingsMenu.add(m_settingsNumberOfTrackersMenuItem);
        m_settingsMenu.add(m_settingsTimeLimitMenuItem);
        m_settingsMenu.add(m_settingsWebcamResolutionMenuItem);
        m_settingsMenu.add(m_settingsSaveMenuItem);
        
        m_helpMenu.add(m_helpAboutMenuItem);

        m_menuBar.add(m_fileMenu);
        m_menuBar.add(m_editMenu);
        m_menuBar.add(m_settingsMenu);
        m_menuBar.add(m_helpMenu);

        setJMenuBar(m_menuBar);
    }
	
    private void initComponents() {
    	m_consoleFont = new Font("Verdana", Font.PLAIN, 14);
    	m_consoleText = new JTextArea();
    	m_consoleText.setFont(m_consoleFont);
    	m_consoleText.setEditable(false);
    	m_consoleText.setColumns(20);
        m_consoleText.setRows(5);
        m_consoleScrollPane = new JScrollPane();
        m_consoleScrollPane.setViewportView(m_consoleText);
    	
    	robotTabs = new JTabbedPane();
        jSplitPane2 = new JSplitPane();
        jPanel1 = new JPanel();
        taskCheckBox1 = new JCheckBox();
        tasksLabel1 = new JLabel();
        jSeparator1 = new JSeparator();
        jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jPanel2 = new JPanel();
        jPanel15 = new JPanel();
        robotInfoPanel2 = new JPanel();
        robotIDLabel5 = new JLabel();
        robotIDTextField1 = new JTextField();
        robotNameLabel5 = new JLabel();
        robotNameTextField1 = new JTextField();
        stateLabel5 = new JLabel();
        robotInfoLabel5 = new JLabel();
        stateTextField1 = new JTextField();
        tasksCompleteLabel1 = new JLabel();
        jSeparator8 = new JSeparator();
        jLabel1 = new JLabel();
        jTextField1 = new JTextField();
        jLabel2 = new JLabel();
        poseTextField1 = new JTextField();
        jLabel3 = new JLabel();
        estimatedPoseTextField1 = new JTextField();
        spawnPoseLabel1 = new JLabel();
        spawnPoseTextField1 = new JTextField();
        jSplitPane1 = new JSplitPane();
        jPanel3 = new JPanel();
        taskCheckBox2 = new JCheckBox();
        tasksLabel2 = new JLabel();
        jSeparator3 = new JSeparator();
        jPanel16 = new JPanel();
        robotInfoPanel3 = new JPanel();
        robotIDLabel6 = new JLabel();
        robotIDTextField2 = new JTextField();
        robotNameLabel6 = new JLabel();
        robotNameTextField2 = new JTextField();
        stateLabel6 = new JLabel();
        robotInfoLabel6 = new JLabel();
        stateTextField2 = new JTextField();
        tasksCompleteLabel2 = new JLabel();
        jSeparator9 = new JSeparator();
        jLabel4 = new JLabel();
        jTextField2 = new JTextField();
        jLabel5 = new JLabel();
        poseTextField2 = new JTextField();
        jLabel6 = new JLabel();
        estimatedPoseTextField2 = new JTextField();
        spawnPoseLabel2 = new JLabel();
        spawnPoseTextField2 = new JTextField();
        jSplitPane3 = new JSplitPane();
        jPanel11 = new JPanel();
        taskCheckBox3 = new JCheckBox();
        tasksLabel3 = new JLabel();
        jSeparator5 = new JSeparator();
        jPanel17 = new JPanel();
        robotInfoPanel4 = new JPanel();
        robotIDLabel7 = new JLabel();
        robotIDTextField3 = new JTextField();
        robotNameLabel7 = new JLabel();
        robotNameTextField3 = new JTextField();
        stateLabel7 = new JLabel();
        robotInfoLabel7 = new JLabel();
        stateTextField3 = new JTextField();
        tasksCompleteLabel3 = new JLabel();
        jSeparator10 = new JSeparator();
        jLabel7 = new JLabel();
        jTextField3 = new JTextField();
        jLabel8 = new JLabel();
        poseTextField3 = new JTextField();
        jLabel9 = new JLabel();
        estimatedPoseTextField3 = new JTextField();
        spawnPoseLabel3 = new JLabel();
        spawnPoseTextField3 = new JTextField();
        titleLabel = new JLabel();
        jSeparator2 = new JSeparator();
        consoleLabel = new JLabel();
        timePanel = new JPanel();
        currentTimeLabel = new JLabel();
        timeElapsedLabel = new JLabel();
        currentTimeTextField = new JTextField();
        maxTimeLabel = new JLabel();
        timeRemainingLabel = new JLabel();
        timeElapsedTextField = new JTextField();
        maxTimeTextField = new JTextField();
        timeRemainingTextField = new JTextField();
        jPanel6 = new JPanel();
        jScrollPane12 = new JScrollPane();
        blocksTable4 = new JTable();
        jLabel10 = new JLabel();
        jLabel11 = new JLabel();
        jScrollPane13 = new JScrollPane();
        blocksTable5 = new JTable();
        totalTasksCompleteLabel = new JLabel();
        jSeparator4 = new JSeparator();
        jSeparator6 = new JSeparator();
        jSeparator7 = new JSeparator();
        jLabel12 = new JLabel();
        jSeparator11 = new JSeparator();
        totalTasksCompleteLabel1 = new JLabel();
        totalTasksCompleteLabel2 = new JLabel();

        taskCheckBox1.setFont(new Font("Verdana", 0, 11));
        taskCheckBox1.setText("Task #1");

        tasksLabel1.setFont(new Font("Verdana", 3, 11));
        tasksLabel1.setText("Tasks");

        jSplitPane2.setLeftComponent(jPanel1);

        jTable1.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jSplitPane2.setRightComponent(jScrollPane1);

        robotIDLabel5.setFont(new Font("Verdana", 0, 11));
        robotIDLabel5.setText("Robot ID:");

        robotIDTextField1.setEditable(false);

        robotNameLabel5.setFont(new Font("Verdana", 0, 11));
        robotNameLabel5.setText("Robot Name:");

        robotNameTextField1.setEditable(false);

        stateLabel5.setFont(new Font("Verdana", 0, 11));
        stateLabel5.setText("State:");

        robotInfoLabel5.setFont(new Font("Verdana", 3, 12));
        robotInfoLabel5.setText("Robot Info");

        stateTextField1.setEditable(false);

        tasksCompleteLabel1.setFont(new Font("Courier New", 0, 11));
        tasksCompleteLabel1.setText("X / Y Tasks Complete (100%)");

        jLabel1.setFont(new Font("Verdana", 0, 11));
        jLabel1.setText("Robot #:");

        jTextField1.setEditable(false);

        jLabel2.setFont(new Font("Verdana", 0, 11));
        jLabel2.setText("Actual Pose:");

        poseTextField1.setEditable(false);

        jLabel3.setFont(new Font("Verdana", 0, 11));
        jLabel3.setText("Estimated Pose:");

        estimatedPoseTextField1.setEditable(false);

        spawnPoseLabel1.setFont(new Font("Verdana", 0, 11));
        spawnPoseLabel1.setText("Spawn Pose:");

        spawnPoseTextField1.setEditable(false);

        jSplitPane2.setRightComponent(jPanel2);

        robotTabs.addTab("Robot 1", jSplitPane2);

        taskCheckBox2.setFont(new Font("Verdana", 0, 11));
        taskCheckBox2.setText("Task #1");

        tasksLabel2.setFont(new Font("Verdana", 3, 11));
        tasksLabel2.setText("Tasks");

        jSplitPane1.setLeftComponent(jPanel3);

        robotIDLabel6.setFont(new Font("Verdana", 0, 11));
        robotIDLabel6.setText("Robot ID:");

        robotIDTextField2.setEditable(false);

        robotNameLabel6.setFont(new Font("Verdana", 0, 11));
        robotNameLabel6.setText("Robot Name:");

        robotNameTextField2.setEditable(false);

        stateLabel6.setFont(new Font("Verdana", 0, 11));
        stateLabel6.setText("State:");

        robotInfoLabel6.setFont(new Font("Verdana", 3, 12));
        robotInfoLabel6.setText("Robot Info");

        stateTextField2.setEditable(false);

        tasksCompleteLabel2.setFont(new Font("Courier New", 0, 11));
        tasksCompleteLabel2.setText("X / Y Tasks Complete (100%)");

        jLabel4.setFont(new Font("Verdana", 0, 11));
        jLabel4.setText("Robot #:");

        jTextField2.setEditable(false);

        jLabel5.setFont(new Font("Verdana", 0, 11));
        jLabel5.setText("Actual Pose:");

        poseTextField2.setEditable(false);

        jLabel6.setFont(new Font("Verdana", 0, 11));
        jLabel6.setText("Estimated Pose:");

        estimatedPoseTextField2.setEditable(false);

        spawnPoseLabel2.setFont(new Font("Verdana", 0, 11));
        spawnPoseLabel2.setText("Spawn Pose:");

        spawnPoseTextField2.setEditable(false);

        jSplitPane1.setRightComponent(jPanel16);

        robotTabs.addTab("Robot 2", jSplitPane1);

        taskCheckBox3.setFont(new Font("Verdana", 0, 11));
        taskCheckBox3.setText("Task #1");

        tasksLabel3.setFont(new Font("Verdana", 3, 11));
        tasksLabel3.setText("Tasks");

        jSplitPane3.setLeftComponent(jPanel11);

        robotIDLabel7.setFont(new Font("Verdana", 0, 11));
        robotIDLabel7.setText("Robot ID:");

        robotIDTextField3.setEditable(false);

        robotNameLabel7.setFont(new Font("Verdana", 0, 11));
        robotNameLabel7.setText("Robot Name:");

        robotNameTextField3.setEditable(false);

        stateLabel7.setFont(new Font("Verdana", 0, 11));
        stateLabel7.setText("State:");

        robotInfoLabel7.setFont(new Font("Verdana", 3, 12));
        robotInfoLabel7.setText("Robot Info");

        stateTextField3.setEditable(false);

        tasksCompleteLabel3.setFont(new Font("Courier New", 0, 11));
        tasksCompleteLabel3.setText("X / Y Tasks Complete (100%)");

        jLabel7.setFont(new Font("Verdana", 0, 11));
        jLabel7.setText("Robot #:");

        jTextField3.setEditable(false);

        jLabel8.setFont(new Font("Verdana", 0, 11));
        jLabel8.setText("Actual Pose:");

        poseTextField3.setEditable(false);

        jLabel9.setFont(new Font("Verdana", 0, 11));
        jLabel9.setText("Estimated Pose:");

        estimatedPoseTextField3.setEditable(false);

        spawnPoseLabel3.setFont(new Font("Verdana", 0, 11));
        spawnPoseLabel3.setText("Spawn Pose:");

        spawnPoseTextField3.setEditable(false);

        jSplitPane3.setRightComponent(jPanel17);

        robotTabs.addTab("Robot 3", jSplitPane3);

        titleLabel.setBackground(new Color(0, 0, 0));
        titleLabel.setFont(new Font("Bernard MT Condensed", 1, 48));
        titleLabel.setForeground(new Color(255, 0, 0));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Robot #1 Advanced Planner");

        jSeparator2.setForeground(new Color(51, 51, 51));
        jSeparator2.setFont(new Font("Tahoma", 0, 36));

        consoleLabel.setFont(new Font("Verdana", 1, 12));
        consoleLabel.setText("Console:");

        currentTimeLabel.setFont(new Font("Verdana", 1, 11));
        currentTimeLabel.setText("Current Time:");

        timeElapsedLabel.setFont(new Font("Verdana", 1, 11));
        timeElapsedLabel.setText("Time Elapsed:");

        currentTimeTextField.setFont(new Font("Verdana", 1, 11));

        maxTimeLabel.setFont(new Font("Verdana", 1, 11));
        maxTimeLabel.setText("Max Time:");

        timeRemainingLabel.setFont(new Font("Verdana", 1, 11));
        timeRemainingLabel.setText("Time Remaining:");

        timeElapsedTextField.setFont(new Font("Verdana", 1, 11));

        maxTimeTextField.setFont(new Font("Verdana", 1, 11));

        timeRemainingTextField.setFont(new Font("Verdana", 1, 11));

        blocksTable4.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Position", "State"
            }
        ) {
			private static final long serialVersionUID = 1L;
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane12.setViewportView(blocksTable4);

        jLabel10.setFont(new Font("Verdana", 1, 11));
        jLabel10.setText("Pots:");

        jLabel11.setFont(new Font("Verdana", 1, 11));
        jLabel11.setText("Blocks:");

        blocksTable5.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "ID", "Position", "State"
            }
        ) {
			private static final long serialVersionUID = 1L;
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane13.setViewportView(blocksTable5);

        totalTasksCompleteLabel.setFont(new Font("Courier New", 1, 12));
        totalTasksCompleteLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalTasksCompleteLabel.setText("X / Y Pots Delivered (100%)");

        jLabel12.setFont(new Font("Verdana", 1, 12));
        jLabel12.setText("Total:");

        totalTasksCompleteLabel1.setFont(new Font("Courier New", 1, 12));
        totalTasksCompleteLabel1.setHorizontalAlignment(SwingConstants.CENTER);
        totalTasksCompleteLabel1.setText("X / Y Blocks Delivered (100%)");

        totalTasksCompleteLabel2.setFont(new Font("Courier New", 1, 12));
        totalTasksCompleteLabel2.setHorizontalAlignment(SwingConstants.CENTER);
        totalTasksCompleteLabel2.setText("X / Y Tasks Complete (100%)");
        
        
        currentTimeTextField.setEditable(false);
        robotIDTextField3.setEditable(false);
        robotNameTextField3.setEditable(false);
        stateTextField3.setEditable(false);
        robotIDTextField2.setEditable(false);
        robotNameTextField2.setEditable(false);
        stateTextField2.setEditable(false);
        robotIDTextField1.setEditable(false);
        robotNameTextField1.setEditable(false);
        stateTextField1.setEditable(false);
        timeElapsedTextField.setEditable(false);
        maxTimeTextField.setEditable(false);
        timeRemainingTextField.setEditable(false);
        currentTimeTextField.setText("XX:XX:XX");
        robotIDTextField3.setText("XX:XX:X3");
        robotNameTextField3.setText("XX:XX:XX");
        stateTextField3.setText("XX:XX:XX");
        robotIDTextField2.setText("XX:XX:X2");
        robotNameTextField2.setText("XX:XX:XX");
        stateTextField2.setText("XX:XX:XX");
        robotIDTextField1.setText("XX:XX:X1");
        robotNameTextField1.setText("XX:XX:XX");
        stateTextField1.setText("XX:XX:XX");
        timeElapsedTextField.setText("XX:XX:XX");
        maxTimeTextField.setText("XX:XX:XX");
        timeRemainingTextField.setText("XX:XX:XX");
    }
    
    private void initLayout() {
		GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(tasksLabel1, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(taskCheckBox1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))))
            .addComponent(jSeparator1, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(tasksLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(taskCheckBox1)
                .addContainerGap(238, Short.MAX_VALUE))
        );
		
		GroupLayout robotInfoPanel2Layout = new GroupLayout(robotInfoPanel2);
        robotInfoPanel2.setLayout(robotInfoPanel2Layout);
        robotInfoPanel2Layout.setHorizontalGroup(
            robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator8, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.TRAILING, robotInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoLabel5, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(robotInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(148, Short.MAX_VALUE))
            .addGroup(robotInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estimatedPoseTextField1, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, robotInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(tasksCompleteLabel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, robotInfoPanel2Layout.createSequentialGroup()
                        .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(robotNameLabel5)
                            .addComponent(stateLabel5)
                            .addComponent(jLabel2)
                            .addComponent(robotIDLabel5))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(robotIDTextField1, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(jTextField1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(stateTextField1, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(poseTextField1, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(robotNameTextField1, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(robotInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spawnPoseLabel1)
                .addGap(11, 11, 11)
                .addComponent(spawnPoseTextField1, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        robotInfoPanel2Layout.setVerticalGroup(
            robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(robotInfoPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoLabel5, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tasksCompleteLabel1)
                .addGap(16, 16, 16)
                .addComponent(jSeparator8, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(robotIDTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(robotIDLabel5))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(robotNameLabel5)
                    .addComponent(robotNameTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel5)
                    .addComponent(stateTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(poseTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(estimatedPoseTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(robotInfoPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(spawnPoseLabel1)
                    .addComponent(spawnPoseTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        GroupLayout jPanel15Layout = new GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jPanel15, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
		
		GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(tasksLabel2, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(taskCheckBox2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))))
            .addComponent(jSeparator3, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(tasksLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator3, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(taskCheckBox2)
                .addContainerGap(238, Short.MAX_VALUE))
        );
		
		GroupLayout robotInfoPanel3Layout = new GroupLayout(robotInfoPanel3);
        robotInfoPanel3.setLayout(robotInfoPanel3Layout);
        robotInfoPanel3Layout.setHorizontalGroup(
            robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator9, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.TRAILING, robotInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoLabel6, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(robotInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(148, Short.MAX_VALUE))
            .addGroup(robotInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estimatedPoseTextField2, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, robotInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(tasksCompleteLabel2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, robotInfoPanel3Layout.createSequentialGroup()
                        .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(robotNameLabel6)
                            .addComponent(stateLabel6)
                            .addComponent(jLabel5)
                            .addComponent(robotIDLabel6))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(robotIDTextField2, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(jTextField2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(stateTextField2, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(poseTextField2, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(robotNameTextField2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(robotInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spawnPoseLabel2)
                .addGap(11, 11, 11)
                .addComponent(spawnPoseTextField2, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        robotInfoPanel3Layout.setVerticalGroup(
            robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(robotInfoPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoLabel6, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tasksCompleteLabel2)
                .addGap(16, 16, 16)
                .addComponent(jSeparator9, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(robotIDTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(robotIDLabel6))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(robotNameLabel6)
                    .addComponent(robotNameTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel6)
                    .addComponent(stateTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(poseTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(estimatedPoseTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(robotInfoPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(spawnPoseLabel2)
                    .addComponent(spawnPoseTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        GroupLayout jPanel16Layout = new GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoPanel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
		
		GroupLayout jPanel11Layout = new GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(tasksLabel3, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(taskCheckBox3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(29, 29, 29))))
            .addComponent(jSeparator5, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(tasksLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator5, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addComponent(taskCheckBox3)
                .addContainerGap(238, Short.MAX_VALUE))
        );
		
		GroupLayout robotInfoPanel4Layout = new GroupLayout(robotInfoPanel4);
        robotInfoPanel4.setLayout(robotInfoPanel4Layout);
        robotInfoPanel4Layout.setHorizontalGroup(
            robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator10, GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
            .addGroup(GroupLayout.Alignment.TRAILING, robotInfoPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoLabel7, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(robotInfoPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addContainerGap(148, Short.MAX_VALUE))
            .addGroup(robotInfoPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(estimatedPoseTextField3, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(GroupLayout.Alignment.TRAILING, robotInfoPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(tasksCompleteLabel3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.LEADING, robotInfoPanel4Layout.createSequentialGroup()
                        .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(robotNameLabel7)
                            .addComponent(stateLabel7)
                            .addComponent(jLabel8)
                            .addComponent(robotIDLabel7))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(robotIDTextField3, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(jTextField3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(stateTextField3, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(poseTextField3, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                            .addComponent(robotNameTextField3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(robotInfoPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(spawnPoseLabel3)
                .addGap(11, 11, 11)
                .addComponent(spawnPoseTextField3, GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE)
                .addContainerGap())
        );
        robotInfoPanel4Layout.setVerticalGroup(
            robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(robotInfoPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoLabel7, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(tasksCompleteLabel3)
                .addGap(16, 16, 16)
                .addComponent(jSeparator10, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(robotIDTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(robotIDLabel7))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(robotNameLabel7)
                    .addComponent(robotNameTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(stateLabel7)
                    .addComponent(stateTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(poseTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(estimatedPoseTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(robotInfoPanel4Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(spawnPoseLabel3)
                    .addComponent(spawnPoseTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        GroupLayout jPanel17Layout = new GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(robotInfoPanel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
		
		GroupLayout timePanelLayout = new GroupLayout(timePanel);
        timePanel.setLayout(timePanelLayout);
        timePanelLayout.setHorizontalGroup(
            timePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(timePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addGroup(GroupLayout.Alignment.LEADING, timePanelLayout.createSequentialGroup()
                        .addComponent(currentTimeLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(currentTimeTextField))
                    .addGroup(timePanelLayout.createSequentialGroup()
                        .addComponent(timeElapsedLabel)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(timeElapsedTextField, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE)))
                .addGap(47, 47, 47)
                .addGroup(timePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(timeRemainingLabel)
                    .addComponent(maxTimeLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(timePanelLayout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addComponent(maxTimeTextField, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeRemainingTextField, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        timePanelLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTimeLabel, maxTimeLabel, timeElapsedLabel, timeRemainingLabel});

        timePanelLayout.linkSize(SwingConstants.HORIZONTAL, new java.awt.Component[] {currentTimeTextField, maxTimeTextField, timeElapsedTextField, timeRemainingTextField});

        timePanelLayout.setVerticalGroup(
            timePanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(timePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(timePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(currentTimeLabel)
                    .addComponent(currentTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(maxTimeLabel)
                    .addComponent(maxTimeTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(timePanelLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(timeElapsedLabel)
                    .addComponent(timeElapsedTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(timeRemainingLabel)
                    .addComponent(timeRemainingTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        timePanelLayout.linkSize(SwingConstants.VERTICAL, new java.awt.Component[] {currentTimeLabel, currentTimeTextField, maxTimeLabel, maxTimeTextField, timeElapsedLabel, timeElapsedTextField, timeRemainingLabel, timeRemainingTextField});

		GroupLayout jPanel6Layout = new GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane12, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jScrollPane13, GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel11)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(65, Short.MAX_VALUE))
        );
		
		GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 609, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 232, GroupLayout.PREFERRED_SIZE))
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jSeparator2, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
                        .addGap(10, 10, 10)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(robotTabs, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(281, 281, 281))
                    .addComponent(jSeparator7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(timePanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator11, GroupLayout.PREFERRED_SIZE, 621, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(totalTasksCompleteLabel2, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalTasksCompleteLabel1, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE)
                    .addComponent(totalTasksCompleteLabel, GroupLayout.PREFERRED_SIZE, 475, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(310, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 641, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(consoleLabel)
                .addContainerGap(784, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_consoleScrollPane, GroupLayout.PREFERRED_SIZE, 591, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(250, Short.MAX_VALUE))
        );

        layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {totalTasksCompleteLabel, totalTasksCompleteLabel1, totalTasksCompleteLabel2});

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(titleLabel, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(timePanel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator11, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jSeparator7, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(robotTabs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(30, 30, 30)))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(totalTasksCompleteLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalTasksCompleteLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(totalTasksCompleteLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(consoleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_consoleScrollPane, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(SwingConstants.VERTICAL, new Component[] {totalTasksCompleteLabel, totalTasksCompleteLabel1, totalTasksCompleteLabel2});

        //Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //setBounds((screenSize.width-638)/2, (screenSize.height-877)/2, 638, 877);
    }
	
	public void windowActivated(WindowEvent e) { }
	public void windowClosed(WindowEvent e) { }
	public void windowDeactivated(WindowEvent e) { }
	public void windowDeiconified(WindowEvent e) { }
	public void windowIconified(WindowEvent e) { }
	public void windowOpened(WindowEvent e) { }
	
	public void windowClosing(WindowEvent e) {
		if(e.getSource() == this) {
			SystemManager.settings.save();
			SystemManager.pathSystem.writeTo(SystemManager.settings.getPathDataFileName());
			SystemManager.taskManager.writeTo(SystemManager.settings.getTaskListFileName());
			dispose();
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_fileConnectMenuItem) {
			SystemManager.client.connect();
			m_fileConnectMenuItem.setEnabled(!SystemManager.client.isConnected());
			m_fileDisconnectMenuItem.setEnabled(SystemManager.client.isConnected());
			m_fileStartSimulationMenuItem.setEnabled(SystemManager.client.isConnected());
			m_editModeMenu.setEnabled(!SystemManager.client.isConnected());
		}
		else if(e.getSource() == m_fileDisconnectMenuItem) {
			SystemManager.client.disconnect();
			m_fileConnectMenuItem.setEnabled(true);
			m_fileDisconnectMenuItem.setEnabled(false);
			m_fileStartSimulationMenuItem.setEnabled(false);
			m_editModeMenu.setEnabled(true);
		}
		else if(e.getSource() == m_fileStartSimulationMenuItem) {
			SystemManager.start();
		}
		else if(e.getSource() == m_fileExitMenuItem) {
			System.exit(0);
		}
		else if(e.getSource() == m_taskEditorMenuItem) {
			SystemManager.showTaskEditorWindow();
		}
		else if(e.getSource() == m_editColourSelectedMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for selected items:", SystemManager.settings.getSelectedColour());
			SystemManager.settings.setSelectedColour(c);
		}
		if(e.getSource() == m_editColourMissingMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for missing items:", SystemManager.settings.getMissingColour());
			SystemManager.settings.setMissingColour(c);
		}
		else if(e.getSource() == m_editColourVertexMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for vertices:", SystemManager.settings.getVertexColour());
			SystemManager.settings.setVertexColour(c);
		}
		else if(e.getSource() == m_editColourEdgeMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for edges:", SystemManager.settings.getEdgeColour());
			SystemManager.settings.setEdgeColour(c);
		}
		else if(e.getSource() == m_editColourRobotMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for robots:", SystemManager.settings.getRobotColour());
			SystemManager.settings.setRobotColour(c);
		}
		else if(e.getSource() == m_editColourBlockMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for blocks:", SystemManager.settings.getBlockColour());
			SystemManager.settings.setBlockColour(c);
		}
		else if(e.getSource() == m_editColourPotMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for pots:", SystemManager.settings.getPotColour());
			SystemManager.settings.setPotColour(c);
		}
		else if(e.getSource() == m_editColourDropOffLocationMenuItem) {
			Color c = JColorChooser.showDialog(this, "Choose a colour for drop off locations:", SystemManager.settings.getDropOffLocationColour());
			SystemManager.settings.setDropOffLocationColour(c);
		}
		else if(e.getSource() == m_editColourResetAllMenuItem) {
			SystemManager.settings.resetAllColours();
		}
		else if(e.getSource() == m_editUpdateTrackerImageMenuItem) {
			SystemManager.updateLocalTrackerImage();
		}
		else if(e.getSource() == m_settingsAutoConnectOnStartupMenuItem) {
			SystemManager.settings.setAutoConnectOnStartup(m_settingsAutoConnectOnStartupMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsTakeWebcamSnapshotOnStartupMenuItem) {
			SystemManager.settings.setTakeWebcamSnapshotOnStartup(m_settingsTakeWebcamSnapshotOnStartupMenuItem.isSelected());
		}
		else if(e.getSource() == m_useStaticStationImagesMenuItem) {
			SystemManager.settings.setUseStaticStationImages(m_useStaticStationImagesMenuItem.isSelected());
		}
		else if(e.getSource() == m_staticStationImageFileNameFormatMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Enter the file name format for static tracker images (ie. \"Station.jpg\":\n(Images will be read as \"Station X.jpg\" where X is the tracker number.", SystemManager.settings.getStaticStationImageFileNameFormat());
			if(input == null) { return; }
			SystemManager.settings.setStaticStationImageFileNameFormat(input);
		}
		else if(e.getSource() == m_settingsAutoScrollConsoleWindowMenuItem) {
			SystemManager.settings.setAutoScrollConsoleWindow(m_settingsAutoScrollConsoleWindowMenuItem.isSelected());
		}
		else if(e.getSource() == m_settingsNumberOfTrackersMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter the number of trackers:", SystemManager.settings.getNumberOfTrackers());
			if(input == null) { return; }
			
			int numberOfTrackers = -1;
			try {
				numberOfTrackers = Integer.parseInt(input);
			}
			catch(NumberFormatException e2) { }
			
			if(SystemManager.settings.setNumberOfTrackers(numberOfTrackers)) {
				JOptionPane.showMessageDialog(this, "Successfully changed number of trackers to " + SystemManager.settings.getNumberOfTrackers() + ".", "Number of Trackers Changed", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Failed to change number of trackers.", "Number of Trackers Change Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == m_settingsTimeLimitMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter a new time limit (in minutes):", SystemManager.settings.getTimeLimit());
			if(input == null) { return; }
			
			int timeLimit = -1;
			try {
				timeLimit = Integer.parseInt(input);
			}
			catch(NumberFormatException e2) { }
			
			if(SystemManager.settings.setTimeLimit(timeLimit)) {
				JOptionPane.showMessageDialog(this, "Successfully changed time limit to " + SystemManager.settings.getTimeLimit() + " minutes.", "Time Limit Changed", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Failed to change time limit.", "Time Limit Change Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == m_settingsWebcamResolutionMenuItem) {
			String input = JOptionPane.showInputDialog(this, "Please enter a webcam resolution in the form \"width, height\":", SystemManager.settings.getWebcamResolution().width + ", " + SystemManager.settings.getWebcamResolution().height);
			if(input == null) { return; }
			
			if(SystemManager.settings.setWebcamResolution(input)) {
				JOptionPane.showMessageDialog(this, "Webcam resolution successfully changed to " + SystemManager.settings.getWebcamResolution().width + "x" + SystemManager.settings.getWebcamResolution().height + ".\nPlease restart the program for changes to take full effect.", "Webcam Resolution Changed", JOptionPane.INFORMATION_MESSAGE);
			}
			else {
				JOptionPane.showMessageDialog(this, "Failed to change webcam resolution.", "Webcam Resolution Change Failed", JOptionPane.ERROR_MESSAGE);
			}
		}
		else if(e.getSource() == m_settingsSaveMenuItem) {
			SystemManager.settings.save();
		}
		else if(e.getSource() == m_helpAboutMenuItem) {
			JOptionPane.showMessageDialog(this, "MasterPlanner Created by Kevin Scroggins (nitro404@hotmail.com).\nCreated for the COMP 4807 Final Project - April 4, 2011.", "About MasterPlanner", JOptionPane.INFORMATION_MESSAGE);
		}
		else {
			for(byte i=0;i<SignalDebugLevel.signalDebugLevels.length;i++) {
	        	if(e.getSource() == m_settingsSignalsMenuItem[i]) {
	        		SystemManager.settings.setSignalDebugLevel(i);
	        		return;
	        	}
	        }
			
			for(byte i=0;i<EditMode.displayEditModes.length;i++) {
				if(e.getSource() == m_editModeMenuItem[i]) {
					SystemManager.displayWindow.setEditMode(i);
					return;
				}
			}
		}
	}
	
	public void update() {
		m_updating = true;
		
		m_editModeMenuItem[SystemManager.displayWindow.getEditMode()].setSelected(true);
		m_settingsSignalsMenuItem[SystemManager.settings.getSignalDebugLevel()].setSelected(true);
		m_settingsAutoConnectOnStartupMenuItem.setSelected(SystemManager.settings.getAutoConnectOnStartup());
        m_settingsTakeWebcamSnapshotOnStartupMenuItem.setSelected(SystemManager.settings.getTakeWebcamSnapshotOnStartup());
		m_useStaticStationImagesMenuItem.setSelected(SystemManager.settings.getUseStaticStationImages());
        m_settingsAutoScrollConsoleWindowMenuItem.setSelected(SystemManager.settings.getAutoScrollConsoleWindow());
		m_fileConnectMenuItem.setEnabled(!SystemManager.client.isConnected());
		m_fileDisconnectMenuItem.setEnabled(SystemManager.client.isConnected());
		m_fileStartSimulationMenuItem.setEnabled(SystemManager.client.isConnected());
		m_editModeMenu.setEnabled(!SystemManager.client.isConnected());
		
		try {
			m_consoleText.setText(SystemManager.console.toString());
			if(SystemManager.settings.getAutoScrollConsoleWindow()) {
				m_consoleText.setCaretPosition(m_consoleText.getText().length());
				m_consoleText.scrollRectToVisible(new Rectangle(0, m_consoleText.getHeight() - 2, 1, 1));
			}
		}
		catch(Exception e) { }
		
		m_updating = false;
	}
	
}
