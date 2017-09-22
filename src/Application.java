import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.List;
import java.awt.Panel;
import java.awt.PopupMenu;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JWindow;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.omg.CORBA._IDLTypeStub;
import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;
import org.omg.stub.java.rmi._Remote_Stub;


public class Application extends JFrame implements ActionListener, ChangeListener
{
	private JPanel _content;
	private ArrayList<JSlider> _sliders;

	private int _leftMargin;
	private int _topMargin;
	
	private BufferedImage _origImage;
	private BufferedImage _renderImage;
	private BufferedImageOp _rescaleOp;
	
	private float[] _channels; //= {1f, 1f, 1f, 1f};
	private float[] _offSets;// = new float[4];
	
	public Application()
	{
		System.out.println("Application instantiated");
		setSize(1250, 500);
		setTitle("UnitTest Application");
		setVisible(true);
		
		init();
	}
	
	void init()
	{
		System.out.println("Application.init");
		
		_leftMargin = 20;
		_topMargin = 20;
		
		_content = new JPanel();
//		setContentPane(_content);
		add(_content);
		
		BorderLayout borderLayout = new BorderLayout();
		_content.setLayout(null); //borderLayout);
		initUi();
		
		initImageElements();
		
		renderImage();
		
	}
	
	void initUi()
	{
		ArrayList<String> sliderNames = new ArrayList<String>(4);
		sliderNames.add("red");
		sliderNames.add("green");
		sliderNames.add("blue");
		sliderNames.add("alpha");
		
		int len = sliderNames.size();
		
		for(int i=0; i<len; i++)
		{
				JSlider slider = new JSlider();
				slider.setName(sliderNames.get(i));
				_content.add(slider);
				slider.setValue(100);
				slider.addChangeListener(this);
				slider.setBounds(_leftMargin, _topMargin + (20 + 2) * i, 100, 20);
				slider.setToolTipText(sliderNames.get(i));
		}
		
		
		ArrayList<String> btnNames = new ArrayList<String>(3);
		btnNames.add("alpha 0x0");
		btnNames.add("alpha 0x99");
		btnNames.add("alpha 0xFF");
//		
		len = btnNames != null ? btnNames.size() : 0;
		
		for(int i=0; i<len; i++)
		{
			BaseButton btn = new BaseButton(btnNames.get(i));
			_content.add(btn);
			btn.setBounds(_leftMargin, 140 + ((18 + 1) * i), 120, 18 );
//			btn.setBackground(new Color(255));			
			btn.setSelected(i == 0);
			btn.index = i;
			btn.setToolTipText(Integer.toString(i));
			
			btn.addActionListener(this);
			
		}		
	}
	
	
	void initImageElements()
	{
		//image
//		_channels = {1f, 1f, 1f, 1f};
		_channels = new float[4];
		_channels[0] = 1.0f;
		_channels[1] = 1.0f;
		_channels[2] = 1.0f;
		_channels[3] = 1.0f;
		
		_offSets = new float[4];
		_offSets[0] = 0.0f;
		_offSets[1] = 0.0f;
		_offSets[2] = 0.0f;
		_offSets[3] = 0.0f;
		
		_rescaleOp = new RescaleOp(_channels, _offSets, null);
		
		try {
		    _origImage = ImageIO.read(new File("assets/camo3.png"));
		    System.out.println("image found");
		} catch (IOException e) {
			System.out.println("image not found");
		}
		
		_renderImage = new BufferedImage(_origImage.getWidth(null), _origImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		
		Graphics biGraphics = _renderImage.getGraphics();
		biGraphics.drawImage(_origImage, 0, 0, null);
		
		Graphics2D g2d = (Graphics2D) _renderImage.getGraphics();
//		try
//		{
			g2d.drawImage(_renderImage, _rescaleOp, 0, 0);
//		}
//		catch(IllegalArgumentException e)
//		{
//			System.out.println(e.getMessage());
//		}
	}
	
	public void stateChanged(ChangeEvent event)
	 {
		JSlider slider = (JSlider) event.getSource();
		float value = (float) slider.getValue() / 100;
		float channelValue = 0f;
		
		switch(slider.getName())
		{
			case "alpha":
			
				_channels[3] = value;
//				_offSets[3] = value;
				
				channelValue = _channels[3];
				break;
				
			case "red":
				
				_channels[0] = value;
				
				channelValue = _channels[0];
				break;
				
			case "green":
				
				_channels[1] = value;
				
				channelValue = _channels[1];
				break;
				
			case "blue":
				
				_channels[2] = value;
				
				channelValue = _channels[2];
				break;
		}
		
//		System.out.println("slider: " + slider.getValue());
//		System.out.println(value);
//		System.out.println("channels: ");
//		System.out.println(_channels[0]);
//		System.out.println(_channels[1]);
//		System.out.println(_channels[2]);
//		System.out.println(_channels[3]);
//		System.out.println(_offSets[0]);
//		System.out.println(_offSets[1]);
//		System.out.println(_offSets[2]);
//		System.out.println(_offSets[3]);
		
		
		renderImage();
		
//		 System.out.println(Integer.toString(_alphaSlider.getValue()));
	 }
	
		public void renderImage()
		{
			if(_origImage == null)
				return;
			
			_rescaleOp = new RescaleOp(_channels, _offSets, null);
			
			Graphics2D g2d = (Graphics2D) _content.getGraphics();
//			g2d.setBackground(new Color(255, 0, 0, 255));
			g2d.setColor(Color.WHITE);
			g2d.fillRect(200, 20, _renderImage.getWidth(), _renderImage.getHeight());
			g2d.drawImage(_renderImage, _rescaleOp, 200, 20);
			
		}
	
	 public void actionPerformed(java.awt.event.ActionEvent event)
	 {
		 BaseButton btn = (BaseButton) event.getSource();
		 
		 switch(btn.index)
		 {
		 	case 0:
		 		updateAlphaChannel(0);
		 		break;
		 		
		 	case 1:
		 		updateAlphaChannel(125);
		 		break;
		 	
		 	case 3:
		 	default:
		 		updateAlphaChannel(255);
		 		break;
		 }
		 
//		 System.out.println(Integer.toString(btn.index));
	 }
	
	
	public static void main(String[] args)
	{
		Application app = new Application();
		
	}
	
	public void draw()
	{
		if(_content != null)
		{
//			_content.paintComponents(_content.getGraphics());
			_content.repaint();

			renderImage();
			
		}
	}
	
	//limited paint
	public void repaint()
	{
		System.out.println("repaint");
		
		draw();
	}
	
	public void paint(Graphics g)
	{
	
//		System.out.println("painting");
		draw();
//		
//		if(_content != null)
//		{
//			System.out.println("content exists as added: " + getComponentZOrder(_content));
//			//add(_content);
//		}
	}

	void updateAlphaChannel(int alpha)
	{
		//update _origImage value then rerender the renderImage
		

		int w = _origImage.getWidth(null); 
		int h = _origImage.getHeight(null); 
		
		int[] pixels = new int[w*h]; 

		int col;
		int row = 0;
		
		for(col = 0; col<w; col++)
		{
			int color = _renderImage.getRGB(col,  row);
			
			_renderImage.setRGB(col,  row, (alpha << 24) | (0x00FFFFFF & color));
			
			if(col == w - 1)
			{
				col = 0;
				row++;
			}
			
			if(row == h - 1)
			{
//				System.out.println("break");
				break;
			}
		}
		
		Graphics2D g2d = (Graphics2D) _content.getGraphics();
//		g2d.setBackground(new Color(255, 0, 0, 255));
		g2d.setColor(Color.WHITE);
		g2d.fillRect(200, 20, _renderImage.getWidth(), _renderImage.getHeight());
		g2d.drawImage(_renderImage, 200, 20, null);
	}
	
}