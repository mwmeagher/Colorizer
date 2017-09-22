import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.HashMap;

import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Visualizer extends JFrame implements ActionListener//, PopupMenuListener
{
	private JPanel _content;
//	private JComboBox<String> _textureCb;
//	private JComboBox<String> _patternCb;
//	private JComboBox<String> _actionCb;
//	private JColorChooser _colorChooser;
//	
//	private Rectangle _textureRect;
//	private Rectangle _patternRect;
//
	private int _leftMargin;
	private int _topMargin;
//	
//	private HashMap<String, String> _texturePaths;
//	private HashMap<String, String> _patternPaths;
//
//	private Image _textureSourceImage;
//	private BufferedImage _textureBi;
//	private Image _patternSourceImage;
//	private BufferedImage _patternBi;
//	private BufferedImage _compositeBi;
//	private BufferedImageOp _rescaleBiOp;
	
	private boolean _imageLoaded;
//	GraphicsEnvironment _graphicsEnvironment; 
//	GraphicsDevice _graphicsDevice;  
//	GraphicsConfiguration _graphicsConfiguration;
	
	
	public Visualizer()
	{
//		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setSize(1250, 1000);
		setTitle("Visualizer UnitTest");
		setBackground(new Color(0xFFFFFF));
		setVisible(true);
		
		
		
		init();
	}
	
	public static void main(String[] args)
	{
		Visualizer visualizer = new Visualizer();
		
	}
	
	void init()
	{
		_leftMargin = 20;
		_topMargin = 10;
		
//		_graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment(); 
//		_graphicsDevice = _graphicsEnvironment.getDefaultScreenDevice();  
//		_graphicsConfiguration = _graphicsDevice.getDefaultConfiguration();
		
		_content = new JPanel();
//		setContentPane(_content);
		add(_content);
		
		_content.setLayout(null);
		
//		initData();
		
		initUi();
		
		
	}
	
	void initUi()
	{
	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	

}
