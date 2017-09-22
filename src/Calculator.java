import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.TextField;

import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Calculator extends JFrame
{
	
	public static void main(String[] args)
	{
		System.out.println("Calculator Main");
		Calculator calc = new Calculator();
	}
	
	private JPanel windowContent;
	private TextField topTf;
	final static public String COUNT = "10";
	
	public Calculator()
	{
		System.out.println("Calculator");
		
//		Calculator.COUNT = "15";
		
		windowContent = new JPanel();
		setContentPane(windowContent);
		
		BorderLayout borderLayout = new BorderLayout();
		windowContent.setLayout(borderLayout);
		
		//top tf
		topTf = new TextField();
		windowContent.add(BorderLayout.NORTH, topTf);
		
		//create a panel
		JPanel btnPanel = new JPanel();
		btnPanel.setLayout(null);
		windowContent.add(BorderLayout.CENTER, btnPanel);
		
		int btnWidth = 60;
		int btnHeight = 26;
		int spacer = 0;
		int hStack = 0;
		int vStack = 0;
		JButton btn = null;
		
		for(int i=0; i<5; i++)
		{
			btn = new JButton();
			btn.setText("1_" + Integer.toString(i));
			btn.setBounds(topTf.getX(), topTf.getY() + topTf.getHeight() + (btnHeight + spacer) * i, btnWidth, btnHeight);
			btnPanel.add(btn);
		}
		
		hStack = btnWidth;
		
		
		for(int i=0; i<5; i++)
		{
			btn = new JButton();
			btn.setText("2_" + Integer.toString(i));
			btn.setBounds(btnWidth  + (btnWidth + spacer) * i, topTf.getY() + topTf.getHeight(), btnWidth, btnHeight);
			btnPanel.add(btn);
		}
		
		if(btn != null)
			vStack = btn.getY() + btn.getHeight();
		
		int cols = 0;
		int rows = 0;
		int maxCols = 5;
		int maxRows = 5;
		
		int cnt = 1;
		
		for(int i=0; i<maxCols*maxRows; i++)
		{
			btn = new JButton();
			btn.setText(Integer.toString(cnt));
			btn.setToolTipText(btn.getText());
			
			btn.setBounds(hStack + (btnWidth + spacer) * cols, vStack + (btnHeight + spacer) * rows, btnWidth, btnHeight);
			btnPanel.add(btn);

			cols++;
			
			if(cols == maxCols)
			{
				cols = 0;
				rows++;
			}
			
			cnt++;
		}
		
		
		
		setSize(400, 200);
		setVisible(true);
		
		
	}
	
	final public void init()
	{
		
	}

}
