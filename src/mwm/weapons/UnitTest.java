package mwm.weapons;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.sql.Time;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import mwm.geom.Particle;

public class UnitTest extends JFrame 
{
	private WeaponsUnitTest _unitTest;
	
	public UnitTest()
	{
//		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setSize(1250, 500);
		setTitle("Colorize UnitTest");
		setBackground(new Color(0xFFFFFF));
		setVisible(true);
		
		init();
	}
	
	public static void main(String[] args)
	{
		UnitTest unitTest = new UnitTest();
	}
	
	private void init()
	{	
		System.out.println("UnitTest.init");
		
		_unitTest = new WeaponsUnitTest();
		add(_unitTest);
//		pack();
		_unitTest.setSize(Renderer.WIDTH, Renderer.HEIGHT);
		_unitTest.init();
	}

	
}
	



