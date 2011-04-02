package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import task.*;
import path.Path;
import planner.*;
import shared.*;

/**
 * Task Editor Window
 *
 * Created on Mar 30, 2011, 4:29:02 PM
 *
 * @author Corey Faibish
 * @author Kevin Scroggins
 */
public class TaskEditorWindow extends JFrame implements ActionListener, ListSelectionListener, MouseListener, MouseMotionListener, Updatable {
	
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
    private JRadioButton m_choiceTaskRadioButton;
    private JButton m_clearObjectiveButton;
    private JButton m_clearTaskButton;
    private JRadioButton m_dropOffBlockAtLocationRadioButton;
    private JTextField m_choiceHasBlockTaskNameTextField;
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
    private JTextField m_nextTaskNameTextField;
    private JTextField m_choiceNoBlockTaskNameTextField;
    private JRadioButton m_pickUpBlockRadioButton;
    private JTextField m_pickUpBlockTextField;
    private JTextField m_taskNameTextField;
    private JButton m_updateObjectiveButton;
    private JButton m_updateTaskButton;
    
    private JPopupMenu m_taskPopupMenu;
    private JMenuItem m_taskMoveUp;
    private JMenuItem m_taskMoveDown;
    private JMenuItem m_taskMoveRemove;
    
    private JPopupMenu m_objectivePopupMenu;
    private JMenuItem m_objectiveMoveUp;
    private JMenuItem m_objectiveMoveDown;
    private JMenuItem m_objectiveMoveRemove;
    
    private Point m_mousePosition = new Point(0, 0);
    private boolean m_updating;
	
    private static final long serialVersionUID = 1L;
	
	public TaskEditorWindow() {
		setTitle("Task Editor Window");
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		addMouseMotionListener(this);
		
		initPopupMenus();
        initComponents();
        initLayout();
        
        clearObjective();
        clearTask();
        update();
    }
	
	private void initPopupMenus() {
	    m_taskPopupMenu = new JPopupMenu();
	    m_taskMoveUp = new JMenuItem("Move Task Up");
	    m_taskMoveDown = new JMenuItem("Move Task Down");
	    m_taskMoveRemove = new JMenuItem("Remove Task");
	    m_taskMoveUp.addActionListener(this);
	    m_taskMoveDown.addActionListener(this);
	    m_taskMoveRemove.addActionListener(this);
	    m_taskPopupMenu.add(m_taskMoveUp);
	    m_taskPopupMenu.add(m_taskMoveDown);
	    m_taskPopupMenu.add(m_taskMoveRemove);
	    
	    m_objectivePopupMenu = new JPopupMenu();
	    m_objectiveMoveUp = new JMenuItem("Move Objective Up");
	    m_objectiveMoveDown = new JMenuItem("Move Objective Down");
	    m_objectiveMoveRemove = new JMenuItem("Remove Objective");
	    m_objectiveMoveUp.addActionListener(this);
	    m_objectiveMoveDown.addActionListener(this);
	    m_objectiveMoveRemove.addActionListener(this);
	    m_objectivePopupMenu.add(m_objectiveMoveUp);
	    m_objectivePopupMenu.add(m_objectiveMoveDown);
	    m_objectivePopupMenu.add(m_objectiveMoveRemove);
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
        m_nextTaskNameTextField = new JTextField();
        m_choiceTaskRadioButton = new JRadioButton("Choice");
        m_choiceHasBlockLabel = new JLabel("Has Block");
        m_choiceHasBlockTaskNameTextField = new JTextField();
        m_choiceNoBlockLabel = new JLabel("No Block");
        m_choiceNoBlockTaskNameTextField = new JTextField();
        m_taskButtonGroup.add(m_lastTaskRadioButton);
        m_taskButtonGroup.add(m_nextTaskRadioButton);
        m_taskButtonGroup.add(m_choiceTaskRadioButton);
        m_lastTaskRadioButton.addActionListener(this);
        m_nextTaskRadioButton.addActionListener(this);
        m_choiceTaskRadioButton.addActionListener(this);
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
        m_taskList.addMouseListener(this);
        m_objectiveList.addMouseListener(this);
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
                            .addComponent(m_choiceTaskRadioButton)
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
                                    .addComponent(m_choiceNoBlockTaskNameTextField, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                                    .addComponent(m_choiceHasBlockTaskNameTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(m_taskNameLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(m_nextTaskRadioButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(m_nextTaskNameTextField, GroupLayout.Alignment.TRAILING)
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
                    .addComponent(m_nextTaskNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(m_choiceTaskRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_choiceHasBlockTaskNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(m_choiceHasBlockLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(m_choiceNoBlockTaskNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
    
    private Objective createObjective() {
    	Objective newObjective = null;
    	
    	if(m_moveToPositionRadioButton.isSelected()) {
    		Path p = SystemManager.pathSystem.getPath(m_moveToPositionPathName);
    		if(p == null || m_moveToPositionIndex < 0 || m_moveToPositionIndex >= p.numberOfVertices()) {
    			JOptionPane.showMessageDialog(this, "Please select position to move to.", "No Position Selected", JOptionPane.ERROR_MESSAGE);
    			return null;
    		}
    		
    		newObjective = new ObjectiveMoveToPosition(m_moveToPositionPathName, m_moveToPositionIndex);
    	}
    	else if(m_backUpToPositionRadioButton.isSelected()) {
    		Path p = SystemManager.pathSystem.getPath(m_backUpToPositionPathName);
    		if(p == null || m_backUpToPositionIndex < 0 || m_backUpToPositionIndex >= p.numberOfVertices()) {
    			JOptionPane.showMessageDialog(this, "Please select position to back up to.", "No Position Selected", JOptionPane.ERROR_MESSAGE);
    			return null;
    		}
    		
    		newObjective = new ObjectiveBackUpToPosition(m_backUpToPositionPathName, m_backUpToPositionIndex);
    	}
    	else if(m_lookAtPositionRadioButton.isSelected()) {
    		Path p = SystemManager.pathSystem.getPath(m_lookAtPositionPathName);
    		if(p == null || m_lookAtPositionIndex < 0 || m_lookAtPositionIndex >= p.numberOfVertices()) {
    			JOptionPane.showMessageDialog(this, "Please select position to look at.", "No Position Selected", JOptionPane.ERROR_MESSAGE);
    			return null;
    		}
    		
    		newObjective = new ObjectiveLookAtPosition(m_lookAtPositionPathName, m_lookAtPositionIndex);
    	}
    	else if(m_pickUpBlockRadioButton.isSelected()) {
    		if(m_pickUpBlockID < 0 || m_pickUpBlockID >= SystemManager.blockSystem.numberOfBlocks()) {
    			JOptionPane.showMessageDialog(this, "Please select a block to pick up.", "No Block Selected", JOptionPane.ERROR_MESSAGE);
    			return null;
    		}
    		
    		newObjective = new ObjectivePickUpBlock(m_pickUpBlockID);
    	}
    	else if(m_dropOffBlockAtLocationRadioButton.isSelected()) {
    		if(m_dropOffBlockAtLocationID < 0 || m_dropOffBlockAtLocationID >= SystemManager.blockSystem.numberOfDropOffLocations()) {
    			JOptionPane.showMessageDialog(this, "Please select a drop off location.", "No Drop Off Location Selected", JOptionPane.ERROR_MESSAGE);
    			return null;
    		}
    		
    		newObjective = new ObjectiveDropOffBlock(m_dropOffBlockAtLocationID);
    	}
    	
    	return newObjective;
    }
    
    private Task createTask() {
    	Task newTask = null;
    	
    	String taskName = m_taskNameTextField.getText().trim();
		if(taskName.length() == 0) {
			JOptionPane.showMessageDialog(this, "Please enter a name for the task.", "No Task Name", JOptionPane.ERROR_MESSAGE);
			return null;
		}
		
		if(m_lastTaskRadioButton.isSelected()) {
			newTask = new Task(taskName);
		}
		else if(m_nextTaskRadioButton.isSelected()) {
			String nextTaskName = m_nextTaskNameTextField.getText().trim();
			
			if(nextTaskName.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter a name for the next task.", "No Next Task Name", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
			newTask = new Task(taskName, nextTaskName);
		}
		else if(m_choiceTaskRadioButton.isSelected()) {
			String nextTaskName = m_choiceHasBlockTaskNameTextField.getText().trim();
			String altTaskName = m_choiceNoBlockTaskNameTextField.getText().trim();
			
			if(nextTaskName.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter a name for the next task (robot has a block).", "No Next Task Name", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			if(altTaskName.length() == 0) {
				JOptionPane.showMessageDialog(this, "Please enter a name for the alternate next task (robot has no block).", "No Alternate Task Name", JOptionPane.ERROR_MESSAGE);
				return null;
			}
			
			newTask = new Task(taskName, nextTaskName, altTaskName);
		}
		
		if(newTask != null) {
			newTask.setRobotID((byte) m_robotList.getSelectedIndex());
		}
		
		return newTask;
    }
    
	public void valueChanged(ListSelectionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_robotList) {
			update();
		}
		else if(e.getSource() == m_taskList) {
			if(m_robotList.getSelectedIndex() < 0 || m_robotList.getSelectedIndex() >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(m_taskList.getSelectedIndex() < 0 || m_taskList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).numberOfTasks()) { return; }
			
			Task t = SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex());
			
			m_taskNameTextField.setText(t.getTaskName());
			m_nextTaskNameTextField.setText("");
	        m_choiceHasBlockTaskNameTextField.setText("");
	        m_choiceNoBlockTaskNameTextField.setText("");
			
			if(t.getNextTaskType() == NextTaskType.Last) {
				m_lastTaskRadioButton.setSelected(true);
			}
			else if(t.getNextTaskType() == NextTaskType.Normal) {
				m_nextTaskRadioButton.setSelected(true);
				m_nextTaskNameTextField.setText(t.getNextTaskName());
			}
			else if(t.getNextTaskType() == NextTaskType.Choice) {
				m_choiceTaskRadioButton.setSelected(true);
				m_choiceHasBlockTaskNameTextField.setText(t.getNextTaskName());
		        m_choiceNoBlockTaskNameTextField.setText(t.getAltTaskName());
			}
	        
			update();
		}
		else if(e.getSource() == m_objectiveList) {
			if(m_robotList.getSelectedIndex() < 0 || m_robotList.getSelectedIndex() >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(m_taskList.getSelectedIndex() < 0 || m_taskList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).numberOfTasks()) { return; }
			if(m_objectiveList.getSelectedIndex() < 0 || m_objectiveList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex()).numberOfObjectives()) { return; }
			
			clearObjective();
			
			Objective o = SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex()).getObjective(m_objectiveList.getSelectedIndex());
			if(o instanceof ObjectiveMoveToPosition) {
				ObjectiveMoveToPosition o2 = (ObjectiveMoveToPosition) o;
				m_moveToPositionRadioButton.setSelected(true);
				m_moveToPositionIndex = o2.getPositionIndex();
				m_moveToPositionPathName = o2.getPathName();
			}
			else if(o instanceof ObjectiveBackUpToPosition) {
				ObjectiveBackUpToPosition o2 = (ObjectiveBackUpToPosition) o;
				m_backUpToPositionRadioButton.setSelected(true);
				m_backUpToPositionIndex = o2.getPositionIndex();
				m_backUpToPositionPathName = o2.getPathName();
			}
			else if(o instanceof ObjectiveLookAtPosition) {
				ObjectiveLookAtPosition o2 = (ObjectiveLookAtPosition) o;
				m_lookAtPositionRadioButton.setSelected(true);
				m_lookAtPositionIndex = o2.getPositionIndex();
				m_lookAtPositionPathName = o2.getPathName();
			}
			else if(o instanceof ObjectivePickUpBlock) {
				ObjectivePickUpBlock o2 = (ObjectivePickUpBlock) o;
				m_pickUpBlockRadioButton.setSelected(true);
				m_pickUpBlockID = o2.getBlockID();
			}
			else if(o instanceof ObjectiveDropOffBlock) {
				ObjectiveDropOffBlock o2 = (ObjectiveDropOffBlock) o;
				m_dropOffBlockAtLocationRadioButton.setSelected(true);
				m_dropOffBlockAtLocationID = o2.getDropOffLocationID();
			}
			
			update();
		}
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { }
	
	public void mouseReleased(MouseEvent e) {
		if(SystemManager.client.isConnected()) { return; }
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(e.getSource() == m_taskList && m_taskList.getSelectedIndex() >= 0) {
				m_taskPopupMenu.show(this, m_mousePosition.x, m_mousePosition.y);
			}
			else if(e.getSource() == m_objectiveList && m_objectiveList.getSelectedIndex() >= 0) {
				m_objectivePopupMenu.show(this, m_mousePosition.x, m_mousePosition.y);
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) { }
	
	public void mouseMoved(MouseEvent e) {
		if(e != null) { m_mousePosition = e.getPoint(); }
	}
	
	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		int robotIndex = m_robotList.getSelectedIndex();
		int taskIndex = m_taskList.getSelectedIndex();
		int objectiveIndex = m_objectiveList.getSelectedIndex();
		
		if(e.getSource() == m_moveToPositionRadioButton) { }
		else if(e.getSource() == m_backUpToPositionRadioButton) { }
		else if(e.getSource() == m_lookAtPositionRadioButton) { }
		else if(e.getSource() == m_pickUpBlockRadioButton) { }
		else if(e.getSource() == m_dropOffBlockAtLocationRadioButton) { }
		else if(e.getSource() == m_addObjectiveButton) {
			if(m_robotList.getSelectedIndex() < 0 || m_robotList.getSelectedIndex() >= SystemManager.robotSystem.numberOfRobots()) {
				JOptionPane.showMessageDialog(this, "Please select a robot first.", "No Robot Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(m_taskList.getSelectedIndex() < 0 || m_taskList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).numberOfTasks()) {
				JOptionPane.showMessageDialog(this, "Please select a task first.", "No Task Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Objective newObjective = createObjective();
			if(newObjective == null) { return; }
			
			SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex()).addObjective(newObjective);
			
			clearObjective();
			update();
		}
		else if(e.getSource() == m_updateObjectiveButton) {
			if(m_robotList.getSelectedIndex() < 0 || m_robotList.getSelectedIndex() >= SystemManager.robotSystem.numberOfRobots()) {
				JOptionPane.showMessageDialog(this, "Please select a robot first.", "No Robot Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(m_taskList.getSelectedIndex() < 0 || m_taskList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).numberOfTasks()) {
				JOptionPane.showMessageDialog(this, "Please select a task first.", "No Task Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(m_objectiveList.getSelectedIndex() < 0 || m_objectiveList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex()).numberOfObjectives()) {
				JOptionPane.showMessageDialog(this, "Please select an objective first.", "No Objective Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Objective newObjective = createObjective();
			if(newObjective == null) { return; }
			
			SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex()).setObjective(m_objectiveList.getSelectedIndex(), newObjective);
			
			clearObjective();
			update();
		}
		else if(e.getSource() == m_clearObjectiveButton) {
			clearObjective();
			update();
		}
		else if(e.getSource() == m_lastTaskRadioButton) { }
		else if(e.getSource() == m_nextTaskRadioButton) { }
		else if(e.getSource() == m_choiceTaskRadioButton) { }
		else if(e.getSource() == m_addTaskButton) {
			if(m_robotList.getSelectedIndex() < 0 || m_robotList.getSelectedIndex() >= SystemManager.robotSystem.numberOfRobots()) {
				JOptionPane.showMessageDialog(this, "Please select a robot first.", "No Robot Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Task newTask = createTask();
			if(newTask == null) { return; }
			
			SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).addTask(newTask);
			
			clearTask();
			update();
		}
		else if(e.getSource() == m_updateTaskButton) {
			if(m_robotList.getSelectedIndex() < 0 || m_robotList.getSelectedIndex() >= SystemManager.robotSystem.numberOfRobots()) {
				JOptionPane.showMessageDialog(this, "Please select a robot first.", "No Robot Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(m_taskList.getSelectedIndex() < 0 || m_taskList.getSelectedIndex() >= SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).numberOfTasks()) {
				JOptionPane.showMessageDialog(this, "Please select a task first.", "No Task Selected", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Task newTask = createTask();
			newTask.setObjectives(SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).getTask(m_taskList.getSelectedIndex()).getObjectives());
			if(newTask == null) { return; }
			
			SystemManager.taskManager.getTaskList(m_robotList.getSelectedIndex()).setTask(m_taskList.getSelectedIndex(), newTask);
			
			clearTask();
			update();
		}
		else if(e.getSource() == m_clearTaskButton) {
			clearTask();
			update();
		}
		else if(e.getSource() == m_objectiveMoveUp) {
			if(robotIndex < 0 || robotIndex >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(taskIndex < 0 || taskIndex >= SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks()) { return; }
			if(objectiveIndex < 0 || objectiveIndex >= SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).numberOfObjectives()) { return; }
			
			if(objectiveIndex == 0) { return; }
			
			Objective temp = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).getObjective(objectiveIndex);
			Objective temp2 = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).getObjective(objectiveIndex - 1);
			SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).setObjective(objectiveIndex, temp2);
			SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).setObjective(objectiveIndex - 1, temp);
			
			m_objectiveList.setSelectedIndex(m_objectiveList.getSelectedIndex() - 1);
			
			update();
		}
		else if(e.getSource() == m_objectiveMoveDown) {
			if(robotIndex < 0 || robotIndex >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(taskIndex < 0 || taskIndex >= SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks()) { return; }
			if(objectiveIndex < 0 || objectiveIndex >= SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).numberOfObjectives()) { return; }
			
			if(objectiveIndex == SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).numberOfObjectives() - 1) { return; }
			
			Objective temp = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).getObjective(objectiveIndex);
			Objective temp2 = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).getObjective(objectiveIndex + 1);
			SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).setObjective(objectiveIndex, temp2);
			SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).setObjective(objectiveIndex + 1, temp);
			
			m_objectiveList.setSelectedIndex(m_objectiveList.getSelectedIndex() + 1);
			
			update();
		}
		else if(e.getSource() == m_objectiveMoveRemove) {
			if(robotIndex < 0 || robotIndex >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(taskIndex < 0 || taskIndex >= SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks()) { return; }
			if(objectiveIndex < 0 || objectiveIndex >= SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).numberOfObjectives()) { return; }
			
			SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex).removeObjective(objectiveIndex);
			
			m_objectiveList.clearSelection();
			
			update();
		}
		else if(e.getSource() == m_taskMoveUp) {
			if(robotIndex < 0 || robotIndex >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(taskIndex < 0 || taskIndex >= SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks()) { return; }
			
			if(taskIndex == 0) { return; }
			
			Task temp = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex);
			Task temp2 = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex - 1);
			SystemManager.taskManager.getTaskList(robotIndex).setTask(taskIndex, temp2);
			SystemManager.taskManager.getTaskList(robotIndex).setTask(taskIndex - 1, temp);
			
			m_taskList.setSelectedIndex(m_taskList.getSelectedIndex() - 1);
			
			update();
		}
		else if(e.getSource() == m_taskMoveDown) {
			if(robotIndex < 0 || robotIndex >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(taskIndex < 0 || taskIndex >= SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks()) { return; }
			
			if(taskIndex == SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks() - 1) { return; }
			
			Task temp = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex);
			Task temp2 = SystemManager.taskManager.getTaskList(robotIndex).getTask(taskIndex + 1);
			SystemManager.taskManager.getTaskList(robotIndex).setTask(taskIndex, temp2);
			SystemManager.taskManager.getTaskList(robotIndex).setTask(taskIndex + 1, temp);
			
			m_taskList.setSelectedIndex(m_taskList.getSelectedIndex() + 1);
			
			update();
		}
		else if(e.getSource() == m_taskMoveRemove) {
			if(robotIndex < 0 || robotIndex >= SystemManager.robotSystem.numberOfRobots()) { return; }
			if(taskIndex < 0 || taskIndex >= SystemManager.taskManager.getTaskList(robotIndex).numberOfTasks()) { return; }
			
			SystemManager.taskManager.getTaskList(robotIndex).removeTask(taskIndex);
			
			m_objectiveList.clearSelection();
			m_taskList.clearSelection();
			
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
        m_nextTaskNameTextField.setText("");
        m_choiceHasBlockTaskNameTextField.setText("");
        m_choiceNoBlockTaskNameTextField.setText("");
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
		
    	// update robot table
    	int selectedRobotIndex = m_robotList.getSelectedIndex();
    	int numberOfRobots = SystemManager.robotSystem.numberOfRobots();
    	String[] robots = new String[numberOfRobots];
    	for(int i=0;i<robots.length;i++) {
    		robots[i] = "Robot " + (i+1);
    	}
    	m_robotList.setListData(robots);
    	if(selectedRobotIndex >= 0 && selectedRobotIndex < numberOfRobots) {
    		m_robotList.setSelectedIndex(selectedRobotIndex);
    	}
    	else {
    		m_robotList.clearSelection();
    	}
    	
    	// update task table
    	int selectedTaskIndex = m_taskList.getSelectedIndex();
    	int numberOfTasks = 0;
    	if(selectedRobotIndex < 0 || selectedRobotIndex >= SystemManager.taskManager.numberOfTaskLists() ||
    	   SystemManager.taskManager.getTaskList(selectedRobotIndex).numberOfTasks() == 0) {
    		m_taskList.setListData(new Object[] { });
    		m_taskList.clearSelection();
    	}
    	else {
    		TaskList t = SystemManager.taskManager.getTaskList(selectedRobotIndex);
    		String[] tasks = new String[t.numberOfTasks()];
        	for(int i=0;i<tasks.length;i++) {
        		tasks[i] = t.getTask(i).getTaskName();
        	}
        	m_taskList.setListData(tasks);
        	numberOfTasks = SystemManager.taskManager.getTaskList(selectedRobotIndex).numberOfTasks();
        	if(selectedTaskIndex >= 0 && selectedTaskIndex < numberOfTasks) {
        		m_taskList.setSelectedIndex(selectedTaskIndex);
        	}
        	else {
        		m_taskList.clearSelection();
        	}
    	}
    	
    	// update objective table
    	int selectedObjectiveIndex = m_objectiveList.getSelectedIndex();
    	int numberOfObjectives = 0;
    	if(selectedRobotIndex < 0 || selectedRobotIndex >= SystemManager.taskManager.numberOfTaskLists() ||
    	   SystemManager.taskManager.getTaskList(selectedRobotIndex).numberOfTasks() == 0 ||
    	   selectedTaskIndex < 0 || selectedTaskIndex >= SystemManager.taskManager.getTaskList(selectedRobotIndex).numberOfTasks() ||
    	   SystemManager.taskManager.getTaskList(selectedRobotIndex).getTask(selectedTaskIndex).numberOfObjectives() == 0) {
    		m_objectiveList.setListData(new Object[] { });
    		m_objectiveList.clearSelection();
    	}
    	else {
    		Task t = SystemManager.taskManager.getTaskList(selectedRobotIndex).getTask(selectedTaskIndex);
    		String[] objectives = new String[t.numberOfObjectives()];
        	for(int i=0;i<objectives.length;i++) {
        		objectives[i] = t.getObjective(i).toString();
        	}
        	m_objectiveList.setListData(objectives);
        	numberOfObjectives = SystemManager.taskManager.getTaskList(selectedRobotIndex).getTask(selectedTaskIndex).numberOfObjectives();
        	if(selectedObjectiveIndex >= 0 && selectedObjectiveIndex < numberOfObjectives) {
        		m_objectiveList.setSelectedIndex(selectedObjectiveIndex);
        	}
        	else {
        		m_objectiveList.clearSelection();
        	}
    	}
		
		m_updating = false;
	}
	
}
