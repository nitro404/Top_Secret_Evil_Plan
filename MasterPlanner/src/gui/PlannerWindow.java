package gui;

//done in netbeans and brought over to eclipse

public class PlannerWindow extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	
	public PlannerWindow(String robotNum) {
		initComponents();
		initHelper(robotNum);
	}

	//gui main, uncomment to test gui output
	
	
	 public static void main(String[] args) {
		PlannerWindow g = new PlannerWindow("3");
		g.setVisible(true);
	}
	

	private void initHelper(String robotNum) {
		consoleTextArea.setEditable(false);
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
		robotIDTextField3.setText("3");
		robotNameTextField3.setText("XX:XX:XX");
		stateTextField3.setText("XX:XX:XX");
		robotIDTextField2.setText("2");
		robotNameTextField2.setText("XX:XX:XX");
		stateTextField2.setText("XX:XX:XX");
		robotIDTextField1.setText("1");
		robotNameTextField1.setText("XX:XX:XX");
		stateTextField1.setText("XX:XX:XX");
		timeElapsedTextField.setText("XX:XX:XX");
		maxTimeTextField.setText("XX:XX:XX");
		timeRemainingTextField.setText("XX:XX:XX");

		//set title to include robotNum
		titleLabel.setText("Robot #" + robotNum + " Advanced Planner");
	}

	@SuppressWarnings("serial")
	private void initComponents() {

		robotTabs = new javax.swing.JTabbedPane();
		jSplitPane2 = new javax.swing.JSplitPane();
		jPanel1 = new javax.swing.JPanel();
		taskCheckBox1 = new javax.swing.JCheckBox();
		tasksLabel1 = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JSeparator();
		jScrollPane1 = new javax.swing.JScrollPane();
		jPanel2 = new javax.swing.JPanel();
		jPanel15 = new javax.swing.JPanel();
		jScrollPane10 = new javax.swing.JScrollPane();
		potsTable1 = new javax.swing.JTable();
		potsLabel5 = new javax.swing.JLabel();
		blocksTablePanel2 = new javax.swing.JPanel();
		jScrollPane11 = new javax.swing.JScrollPane();
		blocksTable1 = new javax.swing.JTable();
		blocksLabel5 = new javax.swing.JLabel();
		robotInfoPanel2 = new javax.swing.JPanel();
		robotIDLabel5 = new javax.swing.JLabel();
		robotIDTextField1 = new javax.swing.JTextField();
		robotNameLabel5 = new javax.swing.JLabel();
		robotNameTextField1 = new javax.swing.JTextField();
		stateLabel5 = new javax.swing.JLabel();
		robotInfoLabel5 = new javax.swing.JLabel();
		stateTextField1 = new javax.swing.JTextField();
		tasksCompleteLabel1 = new javax.swing.JLabel();
		jSeparator8 = new javax.swing.JSeparator();
		jSplitPane1 = new javax.swing.JSplitPane();
		jPanel7 = new javax.swing.JPanel();
		jPanel14 = new javax.swing.JPanel();
		jScrollPane8 = new javax.swing.JScrollPane();
		potsTable2 = new javax.swing.JTable();
		potsLabel4 = new javax.swing.JLabel();
		blocksTablePanel1 = new javax.swing.JPanel();
		jScrollPane9 = new javax.swing.JScrollPane();
		blocksTable2 = new javax.swing.JTable();
		blocksLabel4 = new javax.swing.JLabel();
		robotInfoPanel1 = new javax.swing.JPanel();
		robotIDLabel4 = new javax.swing.JLabel();
		robotIDTextField2 = new javax.swing.JTextField();
		robotNameLabel4 = new javax.swing.JLabel();
		robotNameTextField2 = new javax.swing.JTextField();
		stateLabel4 = new javax.swing.JLabel();
		robotInfoLabel4 = new javax.swing.JLabel();
		stateTextField2 = new javax.swing.JTextField();
		tasksCompleteLabel2 = new javax.swing.JLabel();
		jSeparator7 = new javax.swing.JSeparator();
		jPanel3 = new javax.swing.JPanel();
		taskCheckBox2 = new javax.swing.JCheckBox();
		tasksLabel2 = new javax.swing.JLabel();
		jSeparator3 = new javax.swing.JSeparator();
		jSplitPane3 = new javax.swing.JSplitPane();
		jPanel11 = new javax.swing.JPanel();
		taskCheckBox3 = new javax.swing.JCheckBox();
		tasksLabel3 = new javax.swing.JLabel();
		jSeparator5 = new javax.swing.JSeparator();
		jPanel12 = new javax.swing.JPanel();
		jPanel13 = new javax.swing.JPanel();
		jScrollPane6 = new javax.swing.JScrollPane();
		potsTable3 = new javax.swing.JTable();
		potsLabel3 = new javax.swing.JLabel();
		blocksTablePanel = new javax.swing.JPanel();
		jScrollPane7 = new javax.swing.JScrollPane();
		blocksTable3 = new javax.swing.JTable();
		blocksLabel3 = new javax.swing.JLabel();
		robotInfoPanel = new javax.swing.JPanel();
		robotIDLabel3 = new javax.swing.JLabel();
		robotIDTextField3 = new javax.swing.JTextField();
		robotNameLabel3 = new javax.swing.JLabel();
		robotNameTextField3 = new javax.swing.JTextField();
		stateLabel3 = new javax.swing.JLabel();
		robotInfoLabel3 = new javax.swing.JLabel();
		stateTextField3 = new javax.swing.JTextField();
		tasksCompleteLabel3 = new javax.swing.JLabel();
		jSeparator6 = new javax.swing.JSeparator();
		jScrollPane2 = new javax.swing.JScrollPane();
		consoleTextArea = new javax.swing.JTextArea();
		titleLabel = new javax.swing.JLabel();
		jSeparator2 = new javax.swing.JSeparator();
		consoleLabel = new javax.swing.JLabel();
		timePanel = new javax.swing.JPanel();
		currentTimeLabel = new javax.swing.JLabel();
		timeElapsedLabel = new javax.swing.JLabel();
		currentTimeTextField = new javax.swing.JTextField();
		maxTimeLabel = new javax.swing.JLabel();
		timeRemainingLabel = new javax.swing.JLabel();
		timeElapsedTextField = new javax.swing.JTextField();
		maxTimeTextField = new javax.swing.JTextField();
		timeRemainingTextField = new javax.swing.JTextField();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		taskCheckBox1.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		taskCheckBox1.setText("Task #1");

		tasksLabel1.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
		tasksLabel1.setText("Tasks");

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
				.setHorizontalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				tasksLabel1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				92,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				taskCheckBox1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(29,
																				29,
																				29))))
						.addComponent(jSeparator1,
								javax.swing.GroupLayout.DEFAULT_SIZE, 112,
								Short.MAX_VALUE));
		jPanel1Layout
				.setVerticalGroup(jPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(tasksLabel1)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(9, 9, 9)
										.addComponent(taskCheckBox1)
										.addContainerGap(209, Short.MAX_VALUE)));

		jSplitPane2.setLeftComponent(jPanel1);

		jSplitPane2.setRightComponent(jScrollPane1);

		potsTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "ID", "Pose", "State" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane10.setViewportView(potsTable1);

		potsLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		potsLabel5.setText("Pots");

		blocksTable1.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "ID", "Pose", "State" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane11.setViewportView(blocksTable1);

		blocksLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		blocksLabel5.setText("Blocks");

		javax.swing.GroupLayout blocksTablePanel2Layout = new javax.swing.GroupLayout(
				blocksTablePanel2);
		blocksTablePanel2.setLayout(blocksTablePanel2Layout);
		blocksTablePanel2Layout
				.setHorizontalGroup(blocksTablePanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								blocksTablePanel2Layout.createSequentialGroup()
										.addContainerGap()
										.addComponent(blocksLabel5)
										.addGap(184, 184, 184))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								blocksTablePanel2Layout
										.createSequentialGroup()
										.addComponent(
												jScrollPane11,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												220, Short.MAX_VALUE)
										.addContainerGap()));
		blocksTablePanel2Layout
				.setVerticalGroup(blocksTablePanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								blocksTablePanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(blocksLabel5)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane11,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												91, Short.MAX_VALUE)
										.addContainerGap()));

		robotIDLabel5.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		robotIDLabel5.setText("Robot ID:");

		robotNameLabel5.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		robotNameLabel5.setText("Robot Name:");

		stateLabel5.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		stateLabel5.setText("State:");

		robotInfoLabel5.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
		robotInfoLabel5.setText("Robot Info");

		tasksCompleteLabel1.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
		tasksCompleteLabel1.setText("X / Y Tasks Complete (100%)");

		javax.swing.GroupLayout robotInfoPanel2Layout = new javax.swing.GroupLayout(
				robotInfoPanel2);
		robotInfoPanel2.setLayout(robotInfoPanel2Layout);
		robotInfoPanel2Layout
				.setHorizontalGroup(robotInfoPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								robotInfoPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												robotInfoPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																robotInfoLabel5,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																189,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																robotInfoPanel2Layout
																		.createSequentialGroup()
																		.addComponent(
																				robotIDLabel5)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				robotIDTextField1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				129,
																				Short.MAX_VALUE))
														.addGroup(
																robotInfoPanel2Layout
																		.createSequentialGroup()
																		.addComponent(
																				robotNameLabel5)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				robotNameTextField1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				110,
																				Short.MAX_VALUE))
														.addGroup(
																robotInfoPanel2Layout
																		.createSequentialGroup()
																		.addComponent(
																				stateLabel5)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				stateTextField1,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				150,
																				Short.MAX_VALUE))
														.addComponent(
																tasksCompleteLabel1,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap())
						.addComponent(jSeparator8,
								javax.swing.GroupLayout.DEFAULT_SIZE, 209,
								Short.MAX_VALUE));
		robotInfoPanel2Layout
				.setVerticalGroup(robotInfoPanel2Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								robotInfoPanel2Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												robotInfoLabel5,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												23,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(tasksCompleteLabel1)
										.addGap(16, 16, 16)
										.addComponent(
												jSeparator8,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												robotInfoPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																robotIDTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																robotIDLabel5))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												robotInfoPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																robotNameLabel5)
														.addComponent(
																robotNameTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												robotInfoPanel2Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																stateLabel5)
														.addComponent(
																stateTextField1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(61, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(
				jPanel15);
		jPanel15.setLayout(jPanel15Layout);
		jPanel15Layout
				.setHorizontalGroup(jPanel15Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel15Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel15Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane10,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																219,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																potsLabel5)
														.addComponent(
																blocksTablePanel2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												robotInfoPanel2,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(42, 42, 42)));
		jPanel15Layout
				.setVerticalGroup(jPanel15Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel15Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel15Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																robotInfoPanel2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel15Layout
																		.createSequentialGroup()
																		.addComponent(
																				potsLabel5)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane10,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				91,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				blocksTablePanel2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(
				jPanel2);
		jPanel2.setLayout(jPanel2Layout);
		jPanel2Layout.setHorizontalGroup(jPanel2Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 507, Short.MAX_VALUE)
				.addGap(0, 507, Short.MAX_VALUE)
				.addGroup(
						jPanel2Layout
								.createSequentialGroup()
								.addComponent(jPanel15,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jPanel2Layout.setVerticalGroup(jPanel2Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 283, Short.MAX_VALUE)
				.addGap(0, 283, Short.MAX_VALUE)
				.addGroup(
						jPanel2Layout
								.createSequentialGroup()
								.addComponent(jPanel15,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		jSplitPane2.setRightComponent(jPanel2);

		robotTabs.addTab("Robot 1", jSplitPane2);

		potsTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "ID", "Pose", "State" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane8.setViewportView(potsTable2);

		potsLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		potsLabel4.setText("Pots");

		blocksTable2.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "ID", "Pose", "State" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane9.setViewportView(blocksTable2);

		blocksLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		blocksLabel4.setText("Blocks");

		javax.swing.GroupLayout blocksTablePanel1Layout = new javax.swing.GroupLayout(
				blocksTablePanel1);
		blocksTablePanel1.setLayout(blocksTablePanel1Layout);
		blocksTablePanel1Layout
				.setHorizontalGroup(blocksTablePanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								blocksTablePanel1Layout.createSequentialGroup()
										.addContainerGap()
										.addComponent(blocksLabel4)
										.addGap(184, 184, 184))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								blocksTablePanel1Layout
										.createSequentialGroup()
										.addComponent(
												jScrollPane9,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												220, Short.MAX_VALUE)
										.addContainerGap()));
		blocksTablePanel1Layout
				.setVerticalGroup(blocksTablePanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								blocksTablePanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(blocksLabel4)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane9,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												91, Short.MAX_VALUE)
										.addContainerGap()));

		robotIDLabel4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		robotIDLabel4.setText("Robot ID:");

		robotIDTextField2
				.addActionListener(new java.awt.event.ActionListener() {
					public void actionPerformed(java.awt.event.ActionEvent evt) {
						robotIDTextField2ActionPerformed(evt);
					}
				});

		robotNameLabel4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		robotNameLabel4.setText("Robot Name:");

		stateLabel4.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		stateLabel4.setText("State:");

		robotInfoLabel4.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
		robotInfoLabel4.setText("Robot Info");

		tasksCompleteLabel2.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
		tasksCompleteLabel2.setText("X / Y Tasks Complete (100%)");

		javax.swing.GroupLayout robotInfoPanel1Layout = new javax.swing.GroupLayout(
				robotInfoPanel1);
		robotInfoPanel1.setLayout(robotInfoPanel1Layout);
		robotInfoPanel1Layout
				.setHorizontalGroup(robotInfoPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								robotInfoPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												robotInfoPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																robotInfoLabel4,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																189,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																robotInfoPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				robotIDLabel4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				robotIDTextField2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				129,
																				Short.MAX_VALUE))
														.addGroup(
																robotInfoPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				robotNameLabel4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				robotNameTextField2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				110,
																				Short.MAX_VALUE))
														.addGroup(
																robotInfoPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				stateLabel4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				stateTextField2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				150,
																				Short.MAX_VALUE))
														.addComponent(
																tasksCompleteLabel2,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap())
						.addComponent(jSeparator7,
								javax.swing.GroupLayout.DEFAULT_SIZE, 209,
								Short.MAX_VALUE));
		robotInfoPanel1Layout
				.setVerticalGroup(robotInfoPanel1Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								robotInfoPanel1Layout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												robotInfoLabel4,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												23,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(tasksCompleteLabel2)
										.addGap(16, 16, 16)
										.addComponent(
												jSeparator7,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												robotInfoPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																robotIDTextField2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																robotIDLabel4))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												robotInfoPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																robotNameLabel4)
														.addComponent(
																robotNameTextField2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												robotInfoPanel1Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																stateLabel4)
														.addComponent(
																stateTextField2,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(61, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(
				jPanel14);
		jPanel14.setLayout(jPanel14Layout);
		jPanel14Layout
				.setHorizontalGroup(jPanel14Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel14Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel14Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane8,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																219,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																potsLabel4)
														.addComponent(
																blocksTablePanel1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												robotInfoPanel1,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(42, 42, 42)));
		jPanel14Layout
				.setVerticalGroup(jPanel14Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel14Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel14Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																robotInfoPanel1,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel14Layout
																		.createSequentialGroup()
																		.addComponent(
																				potsLabel4)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane8,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				91,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				blocksTablePanel1,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(
				jPanel7);
		jPanel7.setLayout(jPanel7Layout);
		jPanel7Layout.setHorizontalGroup(jPanel7Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 507, Short.MAX_VALUE)
				.addGroup(
						jPanel7Layout
								.createSequentialGroup()
								.addComponent(jPanel14,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));
		jPanel7Layout.setVerticalGroup(jPanel7Layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 283, Short.MAX_VALUE)
				.addGroup(
						jPanel7Layout
								.createSequentialGroup()
								.addComponent(jPanel14,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addContainerGap(
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)));

		jSplitPane1.setRightComponent(jPanel7);

		taskCheckBox2.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		taskCheckBox2.setText("Task #1");

		tasksLabel2.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
		tasksLabel2.setText("Tasks");

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout
				.setHorizontalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel3Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addComponent(
																				tasksLabel2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				92,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																jPanel3Layout
																		.createSequentialGroup()
																		.addComponent(
																				taskCheckBox2,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(29,
																				29,
																				29))))
						.addComponent(jSeparator3,
								javax.swing.GroupLayout.DEFAULT_SIZE, 112,
								Short.MAX_VALUE));
		jPanel3Layout
				.setVerticalGroup(jPanel3Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel3Layout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(tasksLabel2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(9, 9, 9)
										.addComponent(taskCheckBox2)
										.addContainerGap(209, Short.MAX_VALUE)));

		jSplitPane1.setLeftComponent(jPanel3);

		robotTabs.addTab("Robot 2", jSplitPane1);

		taskCheckBox3.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		taskCheckBox3.setText("Task #1");

		tasksLabel3.setFont(new java.awt.Font("Verdana", 3, 11)); // NOI18N
		tasksLabel3.setText("Tasks");

		javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(
				jPanel11);
		jPanel11.setLayout(jPanel11Layout);
		jPanel11Layout
				.setHorizontalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel11Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addGroup(
																jPanel11Layout
																		.createSequentialGroup()
																		.addComponent(
																				tasksLabel3,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				92,
																				Short.MAX_VALUE)
																		.addContainerGap())
														.addGroup(
																jPanel11Layout
																		.createSequentialGroup()
																		.addComponent(
																				taskCheckBox3,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE)
																		.addGap(29,
																				29,
																				29))))
						.addComponent(jSeparator5,
								javax.swing.GroupLayout.DEFAULT_SIZE, 112,
								Short.MAX_VALUE));
		jPanel11Layout
				.setVerticalGroup(jPanel11Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel11Layout
										.createSequentialGroup()
										.addGap(6, 6, 6)
										.addComponent(tasksLabel3)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(
												jSeparator5,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(9, 9, 9)
										.addComponent(taskCheckBox3)
										.addContainerGap(209, Short.MAX_VALUE)));

		jSplitPane3.setLeftComponent(jPanel11);

		potsTable3.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "ID", "Pose", "State" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane6.setViewportView(potsTable3);

		potsLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		potsLabel3.setText("Pots");

		blocksTable3.setModel(new javax.swing.table.DefaultTableModel(
				new Object[][] { { null, null, null }, { null, null, null },
						{ null, null, null }, { null, null, null } },
				new String[] { "ID", "Pose", "State" }) {
			Class[] types = new Class[] { java.lang.String.class,
					java.lang.String.class, java.lang.Object.class };

			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}
		});
		jScrollPane7.setViewportView(blocksTable3);

		blocksLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
		blocksLabel3.setText("Blocks");

		javax.swing.GroupLayout blocksTablePanelLayout = new javax.swing.GroupLayout(
				blocksTablePanel);
		blocksTablePanel.setLayout(blocksTablePanelLayout);
		blocksTablePanelLayout
				.setHorizontalGroup(blocksTablePanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								blocksTablePanelLayout.createSequentialGroup()
										.addContainerGap()
										.addComponent(blocksLabel3)
										.addGap(184, 184, 184))
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								blocksTablePanelLayout
										.createSequentialGroup()
										.addComponent(
												jScrollPane7,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												220, Short.MAX_VALUE)
										.addContainerGap()));
		blocksTablePanelLayout
				.setVerticalGroup(blocksTablePanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								blocksTablePanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(blocksLabel3)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(
												jScrollPane7,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												91, Short.MAX_VALUE)
										.addContainerGap()));

		robotIDLabel3.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		robotIDLabel3.setText("Robot ID:");

		robotNameLabel3.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		robotNameLabel3.setText("Robot Name:");

		stateLabel3.setFont(new java.awt.Font("Verdana", 0, 11)); // NOI18N
		stateLabel3.setText("State:");

		robotInfoLabel3.setFont(new java.awt.Font("Verdana", 3, 12)); // NOI18N
		robotInfoLabel3.setText("Robot Info");

		tasksCompleteLabel3.setFont(new java.awt.Font("Courier New", 0, 11)); // NOI18N
		tasksCompleteLabel3.setText("X / Y Tasks Complete (100%)");

		javax.swing.GroupLayout robotInfoPanelLayout = new javax.swing.GroupLayout(
				robotInfoPanel);
		robotInfoPanel.setLayout(robotInfoPanelLayout);
		robotInfoPanelLayout
				.setHorizontalGroup(robotInfoPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								robotInfoPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												robotInfoPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																robotInfoLabel3,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																189,
																Short.MAX_VALUE)
														.addGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																robotInfoPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				robotIDLabel3)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				robotIDTextField3,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				129,
																				Short.MAX_VALUE))
														.addGroup(
																robotInfoPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				robotNameLabel3)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				robotNameTextField3,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				110,
																				Short.MAX_VALUE))
														.addGroup(
																robotInfoPanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				stateLabel3)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				stateTextField3,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				150,
																				Short.MAX_VALUE))
														.addComponent(
																tasksCompleteLabel3,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE))
										.addContainerGap())
						.addComponent(jSeparator6,
								javax.swing.GroupLayout.DEFAULT_SIZE, 209,
								Short.MAX_VALUE));
		robotInfoPanelLayout
				.setVerticalGroup(robotInfoPanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								robotInfoPanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addComponent(
												robotInfoLabel3,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												23,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(18, 18, 18)
										.addComponent(tasksCompleteLabel3)
										.addGap(16, 16, 16)
										.addComponent(
												jSeparator6,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												10,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												robotInfoPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																robotIDTextField3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																robotIDLabel3))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addGroup(
												robotInfoPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																robotNameLabel3)
														.addComponent(
																robotNameTextField3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												robotInfoPanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																stateLabel3)
														.addComponent(
																stateTextField3,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap(61, Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(
				jPanel13);
		jPanel13.setLayout(jPanel13Layout);
		jPanel13Layout
				.setHorizontalGroup(jPanel13Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel13Layout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																jScrollPane6,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																219,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																potsLabel3)
														.addComponent(
																blocksTablePanel,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addComponent(
												robotInfoPanel,
												javax.swing.GroupLayout.PREFERRED_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.PREFERRED_SIZE)
										.addGap(42, 42, 42)));
		jPanel13Layout
				.setVerticalGroup(jPanel13Layout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								javax.swing.GroupLayout.Alignment.TRAILING,
								jPanel13Layout
										.createSequentialGroup()
										.addContainerGap(
												javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)
										.addGroup(
												jPanel13Layout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(
																robotInfoPanel,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addGroup(
																jPanel13Layout
																		.createSequentialGroup()
																		.addComponent(
																				potsLabel3)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				jScrollPane6,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				91,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				blocksTablePanel,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addContainerGap()));

		javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(
				jPanel12);
		jPanel12.setLayout(jPanel12Layout);
		jPanel12Layout.setHorizontalGroup(jPanel12Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel12Layout
						.createSequentialGroup()
						.addComponent(jPanel13,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));
		jPanel12Layout.setVerticalGroup(jPanel12Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				jPanel12Layout
						.createSequentialGroup()
						.addComponent(jPanel13,
								javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE,
								Short.MAX_VALUE)));

		jSplitPane3.setRightComponent(jPanel12);

		robotTabs.addTab("Robot 3", jSplitPane3);

		consoleTextArea.setColumns(20);
		consoleTextArea.setRows(5);
		jScrollPane2.setViewportView(consoleTextArea);

		titleLabel.setBackground(new java.awt.Color(0, 0, 0));
		titleLabel.setFont(new java.awt.Font("Bernard MT Condensed", 1, 48)); // NOI18N
		titleLabel.setForeground(new java.awt.Color(255, 0, 0));
		titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		titleLabel.setText("Advanced Planner");

		jSeparator2.setForeground(new java.awt.Color(51, 51, 51));
		jSeparator2.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N

		consoleLabel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
		consoleLabel.setText("Console:");

		currentTimeLabel.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
		currentTimeLabel.setText("Current Time:");

		timeElapsedLabel.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
		timeElapsedLabel.setText("Time Elapsed:");

		currentTimeTextField.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

		maxTimeLabel.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
		maxTimeLabel.setText("Max Time:");

		timeRemainingLabel.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N
		timeRemainingLabel.setText("Time Remaining:");

		timeElapsedTextField.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

		maxTimeTextField.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

		timeRemainingTextField.setFont(new java.awt.Font("Verdana", 1, 11)); // NOI18N

		javax.swing.GroupLayout timePanelLayout = new javax.swing.GroupLayout(
				timePanel);
		timePanel.setLayout(timePanelLayout);
		timePanelLayout
				.setHorizontalGroup(timePanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								timePanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												timePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING,
																false)
														.addGroup(
																javax.swing.GroupLayout.Alignment.LEADING,
																timePanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				currentTimeLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				currentTimeTextField))
														.addGroup(
																timePanelLayout
																		.createSequentialGroup()
																		.addComponent(
																				timeElapsedLabel)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																		.addComponent(
																				timeElapsedTextField,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				116,
																				javax.swing.GroupLayout.PREFERRED_SIZE)))
										.addGap(47, 47, 47)
										.addGroup(
												timePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																timeRemainingLabel)
														.addComponent(
																maxTimeLabel))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												timePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.TRAILING)
														.addComponent(
																maxTimeTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																119,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																timeRemainingTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																119,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		timePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { currentTimeLabel, maxTimeLabel,
						timeElapsedLabel, timeRemainingLabel });

		timePanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL,
				new java.awt.Component[] { currentTimeTextField,
						maxTimeTextField, timeElapsedTextField,
						timeRemainingTextField });

		timePanelLayout
				.setVerticalGroup(timePanelLayout
						.createParallelGroup(
								javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								timePanelLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												timePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																currentTimeLabel)
														.addComponent(
																currentTimeTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																maxTimeLabel)
														.addComponent(
																maxTimeTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(
												timePanelLayout
														.createParallelGroup(
																javax.swing.GroupLayout.Alignment.BASELINE)
														.addComponent(
																timeElapsedLabel)
														.addComponent(
																timeElapsedTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE)
														.addComponent(
																timeRemainingLabel)
														.addComponent(
																timeRemainingTextField,
																javax.swing.GroupLayout.PREFERRED_SIZE,
																javax.swing.GroupLayout.DEFAULT_SIZE,
																javax.swing.GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));

		timePanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL,
				new java.awt.Component[] { currentTimeLabel,
						currentTimeTextField, maxTimeLabel, maxTimeTextField,
						timeElapsedLabel, timeElapsedTextField,
						timeRemainingLabel, timeRemainingTextField });

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.LEADING)
												.addGroup(
														layout.createSequentialGroup()
																.addComponent(
																		titleLabel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		609,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		javax.swing.LayoutStyle.ComponentPlacement.RELATED,
																		56,
																		javax.swing.GroupLayout.PREFERRED_SIZE))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		timePanel,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																.addGap(146,
																		146,
																		146))
												.addGroup(
														javax.swing.GroupLayout.Alignment.TRAILING,
														layout.createSequentialGroup()
																.addComponent(
																		jSeparator2,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		665,
																		Short.MAX_VALUE)
																.addGap(10, 10,
																		10)))
								.addGap(71, 71, 71))
				.addGroup(
						layout.createSequentialGroup().addContainerGap()
								.addComponent(consoleLabel)
								.addContainerGap(564, Short.MAX_VALUE))
				.addGroup(
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING,
												false)
												.addComponent(
														robotTabs,
														javax.swing.GroupLayout.Alignment.LEADING,
														0, 0, Short.MAX_VALUE)
												.addComponent(
														jScrollPane2,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														598, Short.MAX_VALUE))
								.addContainerGap(23, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						layout.createSequentialGroup()
								.addComponent(titleLabel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										44,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jSeparator2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										10,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(timePanel,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addGap(18, 18, 18)
								.addComponent(robotTabs,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										294,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(11, 11, 11)
								.addComponent(consoleLabel)
								.addGap(13, 13, 13)
								.addComponent(jScrollPane2,
										javax.swing.GroupLayout.PREFERRED_SIZE,
										195,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(66, 66, 66)));

		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit()
				.getScreenSize();
		setBounds((screenSize.width - 647) / 2, (screenSize.height - 787) / 2,
				647, 787);
	}// </editor-fold>

	private void robotIDTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	// Variables declaration - do not modify
	private javax.swing.JLabel blocksLabel3;
	private javax.swing.JLabel blocksLabel4;
	private javax.swing.JLabel blocksLabel5;
	private javax.swing.JTable blocksTable1;
	private javax.swing.JTable blocksTable2;
	private javax.swing.JTable blocksTable3;
	private javax.swing.JPanel blocksTablePanel;
	private javax.swing.JPanel blocksTablePanel1;
	private javax.swing.JPanel blocksTablePanel2;
	private javax.swing.JLabel consoleLabel;
	private javax.swing.JTextArea consoleTextArea;
	private javax.swing.JLabel currentTimeLabel;
	private javax.swing.JTextField currentTimeTextField;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel11;
	private javax.swing.JPanel jPanel12;
	private javax.swing.JPanel jPanel13;
	private javax.swing.JPanel jPanel14;
	private javax.swing.JPanel jPanel15;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel7;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane10;
	private javax.swing.JScrollPane jScrollPane11;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane6;
	private javax.swing.JScrollPane jScrollPane7;
	private javax.swing.JScrollPane jScrollPane8;
	private javax.swing.JScrollPane jScrollPane9;
	private javax.swing.JSeparator jSeparator1;
	private javax.swing.JSeparator jSeparator2;
	private javax.swing.JSeparator jSeparator3;
	private javax.swing.JSeparator jSeparator5;
	private javax.swing.JSeparator jSeparator6;
	private javax.swing.JSeparator jSeparator7;
	private javax.swing.JSeparator jSeparator8;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JSplitPane jSplitPane2;
	private javax.swing.JSplitPane jSplitPane3;
	private javax.swing.JLabel maxTimeLabel;
	private javax.swing.JTextField maxTimeTextField;
	private javax.swing.JLabel potsLabel3;
	private javax.swing.JLabel potsLabel4;
	private javax.swing.JLabel potsLabel5;
	private javax.swing.JTable potsTable1;
	private javax.swing.JTable potsTable2;
	private javax.swing.JTable potsTable3;
	private javax.swing.JLabel robotIDLabel3;
	private javax.swing.JLabel robotIDLabel4;
	private javax.swing.JLabel robotIDLabel5;
	private javax.swing.JTextField robotIDTextField1;
	private javax.swing.JTextField robotIDTextField2;
	private javax.swing.JTextField robotIDTextField3;
	private javax.swing.JLabel robotInfoLabel3;
	private javax.swing.JLabel robotInfoLabel4;
	private javax.swing.JLabel robotInfoLabel5;
	private javax.swing.JPanel robotInfoPanel;
	private javax.swing.JPanel robotInfoPanel1;
	private javax.swing.JPanel robotInfoPanel2;
	private javax.swing.JLabel robotNameLabel3;
	private javax.swing.JLabel robotNameLabel4;
	private javax.swing.JLabel robotNameLabel5;
	private javax.swing.JTextField robotNameTextField1;
	private javax.swing.JTextField robotNameTextField2;
	private javax.swing.JTextField robotNameTextField3;
	private javax.swing.JTabbedPane robotTabs;
	private javax.swing.JLabel stateLabel3;
	private javax.swing.JLabel stateLabel4;
	private javax.swing.JLabel stateLabel5;
	private javax.swing.JTextField stateTextField1;
	private javax.swing.JTextField stateTextField2;
	private javax.swing.JTextField stateTextField3;
	private javax.swing.JCheckBox taskCheckBox1;
	private javax.swing.JCheckBox taskCheckBox2;
	private javax.swing.JCheckBox taskCheckBox3;
	private javax.swing.JLabel tasksCompleteLabel1;
	private javax.swing.JLabel tasksCompleteLabel2;
	private javax.swing.JLabel tasksCompleteLabel3;
	private javax.swing.JLabel tasksLabel1;
	private javax.swing.JLabel tasksLabel2;
	private javax.swing.JLabel tasksLabel3;
	private javax.swing.JLabel timeElapsedLabel;
	private javax.swing.JTextField timeElapsedTextField;
	private javax.swing.JPanel timePanel;
	private javax.swing.JLabel timeRemainingLabel;
	private javax.swing.JTextField timeRemainingTextField;
	private javax.swing.JLabel titleLabel;
	// End of variables declaration
}
