package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import shared.*;

/**
 * Task Editor Window
 *
 * Created on Mar 30, 2011, 4:29:02 PM
 *
 * @author Corey Faibish
 * @author Kevin Scroggins
 */
public class TaskEditorWindow extends JFrame implements ActionListener, ListSelectionListener, Updatable {
	
	private int m_moveToPositionIndex;
	private String m_moveToPositionPathName;
	private int m_backUpToPositionIndex;
	private String m_backUpToPositionPathName;
	private int m_lookAtPositionIndex;
	private String m_lookAtPositionPathName;
	private byte m_pickUpBlockID;
	private byte m_dropOffBlockAtLocationID;
	
	private JTextField m_dropOffBlockAtLocationTextField;
	private JTextField m_moveToPositionTextField;
	private JTextField m_backUpToPositionTextField;
	private JTextField m_moveToPathTextField;
	private JTextField m_backUpToPathTextField;
	private JTextField m_lookAtPathTextField;
	private JTextField m_lookAtPositionTextField;
	private JButton m_addObjectiveButton;
    private JButton m_addTaskButton;
    private JRadioButton m_backUpToPositionRadioButton;
    private ButtonGroup m_objectiveButtonGroup;
    private ButtonGroup m_taskButtonGroup;
    private JRadioButton m_choiceRadioButton;
    private JButton m_clearObjectiveButton;
    private JButton m_clearTaskButton;
    private JRadioButton m_dropOffBlockAtLocationRadioButton;
    private JTextField m_choiceHasBlockTextField;
    private JLabel m_robotListTitleLabel;
    private JLabel m_objectiveTitleLabel;
    private JLabel m_taskTitleLabel;
    private JLabel m_ofPathLabel3;
    private JLabel m_choiceHasBlockLabel;
    private JLabel m_choiceNoBlockLabel;
    private JLabel m_taskListTitleLabel;
    private JLabel m_objectiveListTitleLabel;
    private JLabel m_ofPathLabel1;
    private JLabel m_ofPathLabel2;
    private JLabel m_taskNameLabel;
    private JList m_robotList;
    private JList m_taskList;
    private JList m_objectiveList;
    private JPanel m_robotListPanel;
    private JPanel m_taskListPanel;
    private JPanel m_objectiveListPanel;
    private JRadioButton m_lastTaskRadioButton;
    private JScrollPane m_robotListScrollPane;
    private JScrollPane m_taskListScrollPane;
    private JScrollPane m_objectiveListScrollPane;
    private JSeparator m_objectiveTaskSeparator;
    private JRadioButton m_lookAtPositionRadioButton;
    private JRadioButton m_moveToPositionRadioButton;
    private JRadioButton m_nextTaskRadioButton;
    private JTextField m_nextTaskTextField;
    private JTextField m_choiceNoBlockTextField;
    private JRadioButton m_pickUpBlockRadioButton;
    private JTextField m_pickUpBlockTextField;
    private JTextField m_taskNameTextField;
    private JButton m_updateObjectiveButton;
    private JButton m_updateTaskButton;
    
    private boolean m_updating;
	
    private static final long serialVersionUID = 1L;
	
	public TaskEditorWindow() {
		setTitle("Task Editor Window");
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
        initComponents();
        initLayout();
        
        clearObjective();
        clearTask();
        update();
    }
    
    private void initComponents() {
    	// objective components
    	m_objectiveButtonGroup = new ButtonGroup();
    	m_objectiveTitleLabel = new JLabel("Objective");
    	m_moveToPositionRadioButton = new JRadioButton("Move to Position");
    	m_backUpToPositionRadioButton = new JRadioButton("Back Up to Position");
    	m_lookAtPositionRadioButton = new JRadioButton("Look at Position");
    	m_pickUpBlockRadioButton = new JRadioButton("Pick Up Block");
        m_dropOffBlockAtLocationRadioButton = new JRadioButton("Drop Off Block at Location");
    	m_ofPathLabel1 = new JLabel("of Path");
    	m_ofPathLabel2 = new JLabel("of Path");
        m_ofPathLabel3 = new JLabel("of Path");
        m_objectiveButtonGroup.add(m_moveToPositionRadioButton);
        m_objectiveButtonGroup.add(m_backUpToPositionRadioButton);
        m_objectiveButtonGroup.add(m_lookAtPositionRadioButton);
        m_objectiveButtonGroup.add(m_pickUpBlockRadioButton);
        m_objectiveButtonGroup.add(m_dropOffBlockAtLocationRadioButton);
        m_moveToPositionRadioButton.addActionListener(this);
        m_backUpToPositionRadioButton.addActionListener(this);
        m_lookAtPositionRadioButton.addActionListener(this);
        m_pickUpBlockRadioButton.addActionListener(this);
        m_dropOffBlockAtLocationRadioButton.addActionListener(this);
    	
    	m_moveToPositionTextField = new JTextField();
    	m_moveToPathTextField = new JTextField();
    	m_backUpToPositionTextField = new JTextField();
    	m_backUpToPathTextField = new JTextField();
    	m_lookAtPositionTextField = new JTextField();
    	m_lookAtPathTextField = new JTextField();
    	m_pickUpBlockTextField = new JTextField();
    	m_dropOffBlockAtLocationTextField = new JTextField();
        m_moveToPositionTextField.setEditable(false);
        m_moveToPathTextField.setEditable(false);
        m_backUpToPositionTextField.setEditable(false);
        m_backUpToPathTextField.setEditable(false);
        m_lookAtPositionTextField.setEditable(false);
        m_lookAtPathTextField.setEditable(false);
        m_pickUpBlockTextField.setEditable(false);
        m_dropOffBlockAtLocationTextField.setEditable(false);
        
        m_addObjectiveButton = new JButton("Add");
        m_updateObjectiveButton = new JButton("Update");
        m_clearObjectiveButton = new JButton("Clear");
        m_addObjectiveButton.addActionListener(this);
        m_updateObjectiveButton.addActionListener(this);
        m_clearObjectiveButton.addActionListener(this);
        
        m_objectiveTaskSeparator = new JSeparator();
        
        // task components
        m_taskButtonGroup = new ButtonGroup();
        m_taskTitleLabel = new JLabel("Task");
        m_taskNameLabel = new JLabel("Task Name:");
        m_taskNameTextField = new JTextField();
        m_lastTaskRadioButton = new JRadioButton("Last Task");
        m_nextTaskRadioButton = new JRadioButton("Next Task");
        m_nextTaskTextField = new JTextField();
        m_choiceRadioButton = new JRadioButton("Choice");
        m_choiceHasBlockLabel = new JLabel("Has Block");
        m_choiceHasBlockTextField = new JTextField();
        m_choiceNoBlockLabel = new JLabel("No Block");
        m_choiceNoBlockTextField = new JTextField();
        m_taskButtonGroup.add(m_lastTaskRadioButton);
        m_taskButtonGroup.add(m_nextTaskRadioButton);
        m_taskButtonGroup.add(m_choiceRadioButton);
        m_lastTaskRadioButton.addActionListener(this);
        m_nextTaskRadioButton.addActionListener(this);
        m_choiceRadioButton.addActionListener(this);
        m_addTaskButton = new JButton("Add");
        m_updateTaskButton = new JButton("Update");
        m_clearTaskButton = new JButton("Clear");
        m_addTaskButton.addActionListener(this);
        m_updateTaskButton.addActionListener(this);
        m_clearTaskButton.addActionListener(this);
        
        // list components
        m_taskListTitleLabel = new JLabel("Tasks");
        m_objectiveListTitleLabel = new JLabel("Objectives");
        m_robotListTitleLabel = new JLabel("Robots");
        m_robotList = new JList();
        m_taskList = new JList();
        m_objectiveList = new JList();
        m_robotListScrollPane = new JScrollPane();
        m_taskListScrollPane = new JScrollPane();
        m_objectiveListScrollPane = new JScrollPane();
        m_robotListPanel = new JPanel();
        m_taskListPanel = new JPanel();
        m_objectiveListPanel = new JPanel();
        m_robotListScrollPane.setViewportView(m_robotList);
        m_taskListScrollPane.setViewportView(m_taskList);
        m_objectiveListScrollPane.setViewportView(m_objectiveList);
        m_robotList.addListSelectionListener(this);
        m_taskList.addListSelectionListener(this);
        m_objectiveList.addListSelectionListener(this);
    }
    
    private void initLayout() {
    	GroupLayout jPanel2Layout = new GroupLayout(m_taskListPanel);
        m_taskListPanel.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(m_objectiveTitleLabel, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(m_addObjectiveButton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(m_updateObjectiveButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_clearObjectiveButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(m_pickUpBlockRadioButton)
                                    .addComponent(m_dropOffBlockAtLocationRadioButton))
                                .addGap(27, 27, 27)
                                .addComponent(m_dropOffBlockAtLocationTextField, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(m_moveToPositionRadioButton)
                                            .addComponent(m_backUpToPositionRadioButton))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(m_moveToPositionTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                            .addComponent(m_backUpToPositionTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
                                    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(m_lookAtPositionRadioButton)
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(m_pickUpBlockTextField, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                            .addComponent(m_lookAtPositionTextField, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(m_ofPathLabel3)
                                    .addComponent(m_ofPathLabel2)
                                    .addComponent(m_ofPathLabel1))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(m_lookAtPathTextField, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(m_backUpToPathTextField, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(m_moveToPathTextField, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {m_dropOffBlockAtLocationTextField, m_moveToPositionTextField, m_backUpToPositionTextField});

        jPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {m_addObjectiveButton, m_clearObjectiveButton, m_updateObjectiveButton});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(m_objectiveTitleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(m_moveToPositionRadioButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(m_backUpToPositionRadioButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(m_moveToPositionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_ofPathLabel1)
                            .addComponent(m_moveToPathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(m_backUpToPositionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(m_ofPathLabel2)
                            .addComponent(m_backUpToPathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_lookAtPositionRadioButton)
                    .addComponent(m_lookAtPositionTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_ofPathLabel3)
                    .addComponent(m_lookAtPathTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_pickUpBlockRadioButton)
                    .addComponent(m_pickUpBlockTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_dropOffBlockAtLocationRadioButton)
                    .addComponent(m_dropOffBlockAtLocationTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_addObjectiveButton)
                    .addComponent(m_updateObjectiveButton)
                    .addComponent(m_clearObjectiveButton))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {m_moveToPositionTextField, m_backUpToPositionTextField, m_moveToPathTextField, m_backUpToPathTextField});
		
        GroupLayout jPanel3Layout = new GroupLayout(m_objectiveListPanel);
        m_objectiveListPanel.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(m_choiceRadioButton)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(m_lastTaskRadioButton)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 346, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(m_choiceHasBlockLabel)
                                    .addComponent(m_choiceNoBlockLabel))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(m_choiceNoBlockTextField, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                                    .addComponent(m_choiceHasBlockTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(m_taskNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(m_nextTaskRadioButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(m_nextTaskTextField, GroupLayout.Alignment.TRAILING)
                                    .addComponent(m_taskNameTextField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(m_addTaskButton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(m_updateTaskButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(m_clearTaskButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {m_addTaskButton, m_clearTaskButton, m_updateTaskButton});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_taskNameLabel)
                    .addComponent(m_taskNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_lastTaskRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_nextTaskRadioButton)
                    .addComponent(m_nextTaskTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_choiceRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_choiceHasBlockTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_choiceHasBlockLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_choiceNoBlockTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_choiceNoBlockLabel))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_addTaskButton)
                    .addComponent(m_updateTaskButton)
                    .addComponent(m_clearTaskButton))
                .addContainerGap(18, Short.MAX_VALUE))
        );
		
        GroupLayout jPanel1Layout = new GroupLayout(m_robotListPanel);
        m_robotListPanel.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(m_taskTitleLabel, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
            .addComponent(m_objectiveTaskSeparator, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_objectiveListPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_taskListPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(m_taskListPanel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(m_objectiveTaskSeparator, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(m_taskTitleLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(m_objectiveListPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
		
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(m_robotListTitleLabel)
                    .addComponent(m_robotListScrollPane, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(m_taskListTitleLabel)
                    .addComponent(m_taskListScrollPane, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(m_objectiveListTitleLabel)
                    .addComponent(m_objectiveListScrollPane, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_robotListPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(m_robotListPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(m_robotListTitleLabel)
                            .addComponent(m_taskListTitleLabel)
                            .addComponent(m_objectiveListTitleLabel))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(m_objectiveListScrollPane)
                            .addComponent(m_robotListScrollPane, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                            .addComponent(m_taskListScrollPane, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }
    
	public void valueChanged(ListSelectionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_robotList) {
			
		}
		else if(e.getSource() == m_taskList) {
			
		}
		else if(e.getSource() == m_objectiveList) {
			
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_moveToPositionRadioButton) {
			
		}
		else if(e.getSource() == m_backUpToPositionRadioButton) {
			
		}
		else if(e.getSource() == m_lookAtPositionRadioButton) {
			
		}
		else if(e.getSource() == m_pickUpBlockRadioButton) {
			
		}
		else if(e.getSource() == m_dropOffBlockAtLocationRadioButton) {
			
		}
		else if(e.getSource() == m_addObjectiveButton) {
			
		}
		else if(e.getSource() == m_updateObjectiveButton) {
			clearObjective();
			update();
		}
		else if(e.getSource() == m_clearObjectiveButton) {
			clearObjective();
			update();
		}
		else if(e.getSource() == m_lastTaskRadioButton) {
			
		}
		else if(e.getSource() == m_nextTaskRadioButton) {
			
		}
		else if(e.getSource() == m_choiceRadioButton) {
			
		}
		else if(e.getSource() == m_addTaskButton) {
			
		}
		else if(e.getSource() == m_updateTaskButton) {
			clearTask();
			update();
		}
		else if(e.getSource() == m_clearTaskButton) {
			clearTask();
			update();
		}
	}
	
	public void clearObjective() {
		m_moveToPositionRadioButton.setSelected(true);
		m_moveToPositionIndex = -1;
		m_moveToPositionPathName = "";
		m_backUpToPositionIndex = -1;
		m_backUpToPositionPathName = "";
		m_lookAtPositionIndex = -1;
		m_lookAtPositionPathName = "";
		m_pickUpBlockID = -1;
		m_dropOffBlockAtLocationID = -1;
	}
	
	public void clearTask() {
		m_lastTaskRadioButton.setSelected(true);
		m_taskNameTextField.setText("");
        m_nextTaskTextField.setText("");
        m_choiceHasBlockTextField.setText("");
        m_choiceNoBlockTextField.setText("");
	}
	
	public void update() {
		m_updating = true;
		
		m_moveToPositionTextField.setText((m_moveToPositionIndex < 0) ? "" : Integer.toString(m_moveToPositionIndex));
    	m_moveToPathTextField.setText(m_moveToPositionPathName);
    	m_backUpToPositionTextField.setText((m_backUpToPositionIndex < 0) ? "" : Integer.toString(m_backUpToPositionIndex));
    	m_backUpToPathTextField.setText(m_backUpToPositionPathName);
    	m_lookAtPositionTextField.setText((m_lookAtPositionIndex < 0) ? "" : Integer.toString(m_lookAtPositionIndex));
    	m_lookAtPathTextField.setText(m_lookAtPositionPathName);
    	m_pickUpBlockTextField.setText((m_pickUpBlockID < 0) ? "" : Byte.toString(m_pickUpBlockID));
    	m_dropOffBlockAtLocationTextField.setText((m_dropOffBlockAtLocationID < 0) ? "" : Byte.toString(m_dropOffBlockAtLocationID));
		
        /*
        jList1.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "111111 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        jList2.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "222222 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        jList3.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "3333 1", "Item 2", "Item 3", "Item 4", "Item 5", "Back Up To 0 of 1", "Pick Up Block 2" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        */
		
		m_updating = false;
	}
    
}
