package gui;

import java.awt.*;
import javax.swing.*;

/**
 *
 * Task Editor Window
 *
 * Created on Mar 30, 2011, 4:29:02 PM
 *
 * @author coreyfaibish
 */
public class TaskEditorWindow extends JFrame {
	
	private JButton addTaskButton;
    private JButton addTaskButton1;
    private JRadioButton backUpToRadioButton;
    private ButtonGroup buttonGroup1;
    private ButtonGroup buttonGroup2;
    private JRadioButton choiceRadioButton;
    private JButton clearTaskButton;
    private JButton clearTaskButton1;
    private JRadioButton dropOffRadioButton;
    private JTextField dropOffTextField2;
    private JTextField dropOffTextField3;
    private JTextField dropOffTextField4;
    private JTextField dropOffTextField5;
    private JTextField dropOffTextField6;
    private JTextField dropOffTextField7;
    private JTextField hasBlockTextField;
    private JLabel jLabel1;
    private JLabel jLabel10;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JList jList1;
    private JList jList2;
    private JList jList3;
    private JMenu jMenu1;
    private JMenu jMenu2;
    private JMenuBar jMenuBar1;
    private JMenuItem jMenuItem1;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel3;
    private JRadioButton jRadioButton1;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JSeparator jSeparator1;
    private JRadioButton lookAtRadioButton;
    private JTextField lookAtTextField;
    private JRadioButton moveToRadioButton;
    private JRadioButton nextTaskRadioButton;
    private JTextField nextTaskTextField;
    private JTextField noBlockTextField;
    private JRadioButton pickUpBlockRadioButton;
    private JTextField pickUpBlockTextField;
    private JTextField taskNameTextField;
    private JButton updateTaskButton;
    private JButton updateTaskButton1;
    
	
    private static final long serialVersionUID = 1L;
	
	public TaskEditorWindow() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		
        initComponents();
        initLayout();
    }
    
    private void initComponents() {
        buttonGroup1 = new ButtonGroup();
        buttonGroup2 = new ButtonGroup();
        jScrollPane1 = new JScrollPane();
        jList1 = new JList();
        jLabel1 = new JLabel();
        jScrollPane2 = new JScrollPane();
        jList2 = new JList();
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        pickUpBlockTextField = new JTextField();
        dropOffTextField2 = new JTextField();
        backUpToRadioButton = new JRadioButton();
        dropOffTextField6 = new JTextField();
        dropOffTextField3 = new JTextField();
        dropOffTextField4 = new JTextField();
        dropOffRadioButton = new JRadioButton();
        jLabel8 = new JLabel();
        dropOffTextField5 = new JTextField();
        lookAtRadioButton = new JRadioButton();
        pickUpBlockRadioButton = new JRadioButton();
        moveToRadioButton = new JRadioButton();
        lookAtTextField = new JTextField();
        jLabel7 = new JLabel();
        clearTaskButton = new JButton();
        updateTaskButton = new JButton();
        addTaskButton = new JButton();
        jLabel10 = new JLabel();
        jLabel12 = new JLabel();
        dropOffTextField7 = new JTextField();
        jPanel3 = new JPanel();
        jRadioButton1 = new JRadioButton();
        noBlockTextField = new JTextField();
        nextTaskRadioButton = new JRadioButton();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        hasBlockTextField = new JTextField();
        choiceRadioButton = new JRadioButton();
        nextTaskTextField = new JTextField();
        taskNameTextField = new JTextField();
        jLabel9 = new JLabel();
        addTaskButton1 = new JButton();
        updateTaskButton1 = new JButton();
        clearTaskButton1 = new JButton();
        jLabel11 = new JLabel();
        jSeparator1 = new JSeparator();
        jScrollPane3 = new JScrollPane();
        jList3 = new JList();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jMenuBar1 = new JMenuBar();
        jMenu1 = new JMenu();
        jMenuItem1 = new JMenuItem();
        jMenu2 = new JMenu();

        jLabel1.setText("Robots");

        pickUpBlockTextField.setEditable(false);
        pickUpBlockTextField.setText("Z");

        dropOffTextField2.setEditable(false);
        dropOffTextField2.setText("Z");

        buttonGroup1.add(backUpToRadioButton);
        backUpToRadioButton.setText("Back Up To Position");

        dropOffTextField6.setEditable(false);
        dropOffTextField6.setText("Y");

        dropOffTextField3.setEditable(false);
        dropOffTextField3.setText("X");

        dropOffTextField4.setEditable(false);
        dropOffTextField4.setText("X");

        buttonGroup1.add(dropOffRadioButton);
        dropOffRadioButton.setText("Drop Off Block At Location");

        jLabel8.setText("of path");

        dropOffTextField5.setEditable(false);
        dropOffTextField5.setText("Y");

        buttonGroup1.add(lookAtRadioButton);
        lookAtRadioButton.setText("Look At Position");

        buttonGroup1.add(pickUpBlockRadioButton);
        pickUpBlockRadioButton.setText("Pick Up Block");

        buttonGroup1.add(moveToRadioButton);
        moveToRadioButton.setText("Move To Position");

        lookAtTextField.setEditable(false);
        lookAtTextField.setText("X");

        jLabel7.setText("of path");

        clearTaskButton.setText("Clear");

        updateTaskButton.setText("Update");

        addTaskButton.setText("Add");

        jLabel10.setText("Objective");

        jLabel12.setText("of path");

        dropOffTextField7.setEditable(false);
        dropOffTextField7.setText("Y");
        
        buttonGroup2.add(jRadioButton1);
        jRadioButton1.setText("Last Task");

        noBlockTextField.setText("Task C2");

        buttonGroup2.add(nextTaskRadioButton);
        nextTaskRadioButton.setText("Next Task");

        jLabel3.setText("Has Block");

        jLabel4.setText("No Block");

        hasBlockTextField.setText("Task C1");

        buttonGroup2.add(choiceRadioButton);
        choiceRadioButton.setText("Choice");

        nextTaskTextField.setText("Task B");

        taskNameTextField.setText("TaskName");

        jLabel9.setText("TaskName");

        addTaskButton1.setText("Add");

        updateTaskButton1.setText("Update");

        clearTaskButton1.setText("Clear");

        jLabel11.setText("Task");

        jLabel5.setText("Tasks");

        jLabel6.setText("Objectives");

        jMenu1.setText("File");

        jMenuItem1.setText("Save");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);
        
        jList1.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);
        
        jList2.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);
        
        jList3.setModel(new AbstractListModel() {
			private static final long serialVersionUID = 1L;
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5", "Back Up To 0 of 1", "Pick Up Block 2" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(jList3);
    }
    
    private void initLayout() {
    	GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(jLabel10, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(addTaskButton, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateTaskButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearTaskButton, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                            .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(pickUpBlockRadioButton)
                                    .addComponent(dropOffRadioButton))
                                .addGap(27, 27, 27)
                                .addComponent(dropOffTextField2, GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(moveToRadioButton)
                                            .addComponent(backUpToRadioButton))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(dropOffTextField3, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
                                            .addComponent(dropOffTextField4, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)))
                                    .addGroup(GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                        .addComponent(lookAtRadioButton)
                                        .addGap(26, 26, 26)
                                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                            .addComponent(pickUpBlockTextField, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)
                                            .addComponent(lookAtTextField, GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE))))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel7))))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(dropOffTextField7, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(dropOffTextField6, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                            .addComponent(dropOffTextField5, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {dropOffTextField2, dropOffTextField3, dropOffTextField4});

        jPanel2Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addTaskButton, clearTaskButton, updateTaskButton});

        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(jLabel10)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(moveToRadioButton)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(backUpToRadioButton))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(dropOffTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(dropOffTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(dropOffTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(dropOffTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(lookAtRadioButton)
                    .addComponent(lookAtTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(dropOffTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(pickUpBlockRadioButton)
                    .addComponent(pickUpBlockTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(dropOffRadioButton)
                    .addComponent(dropOffTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addTaskButton)
                    .addComponent(updateTaskButton)
                    .addComponent(clearTaskButton))
                .addContainerGap())
        );

        jPanel2Layout.linkSize(SwingConstants.VERTICAL, new Component[] {dropOffTextField3, dropOffTextField4, dropOffTextField5, dropOffTextField6});
		
        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(choiceRadioButton)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 346, GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(45, 45, 45)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                    .addComponent(noBlockTextField, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                                    .addComponent(hasBlockTextField, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel9, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nextTaskRadioButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                    .addComponent(nextTaskTextField, GroupLayout.Alignment.TRAILING)
                                    .addComponent(taskNameTextField, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE)))))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(addTaskButton1, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(updateTaskButton1, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(clearTaskButton1, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        jPanel3Layout.linkSize(SwingConstants.HORIZONTAL, new Component[] {addTaskButton1, clearTaskButton1, updateTaskButton1});

        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(taskNameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nextTaskRadioButton)
                    .addComponent(nextTaskTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(choiceRadioButton)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(hasBlockTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(noBlockTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(addTaskButton1)
                    .addComponent(updateTaskButton1)
                    .addComponent(clearTaskButton1))
                .addContainerGap(18, Short.MAX_VALUE))
        );
		
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel11, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
            .addComponent(jSeparator1, GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jLabel11)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addGap(24, 24, 24))
        );
		
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane3, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))))
                .addContainerGap())
        );

        pack();
    }
    
}
