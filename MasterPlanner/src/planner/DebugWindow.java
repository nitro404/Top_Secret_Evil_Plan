package planner;

import java.awt.*;
import javax.swing.*;
import shared.*;

public class DebugWindow extends JFrame implements Updatable {
	
	private JTextArea m_consoleText;
	private Font m_consoleFont;
	private JScrollPane m_consoleScrollPane;
	
	private static final long serialVersionUID = 1L;
	
	public DebugWindow() {
		setTitle("Debug Window");
		setSize(640, 800);
		setMinimumSize(new Dimension(320, 240));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		initComponents();
	}
	
	public void initComponents() {
		m_consoleText = new JTextArea();
        m_consoleFont = new Font("Verdana", Font.PLAIN, 14);
        m_consoleText.setFont(m_consoleFont);
        m_consoleText.setEditable(false);
        m_consoleScrollPane = new JScrollPane(m_consoleText);
        add(m_consoleScrollPane);
	}
	
	public void update() {
		m_consoleText.setText(SystemManager.console.toString());
		m_consoleText.setCaretPosition(m_consoleText.getText().length());
		m_consoleText.scrollRectToVisible(new Rectangle(0, m_consoleText.getHeight() - 2, 1, 1));
	}
	
}
