
import java.awt.BorderLayout;
import java.awt.Desktop.Action;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.PopupMenuListener;

import mwm.utils.ColorUtil;


public class ColorizerUnitTest extends JFrame implements ActionListener//, PopupMenuListener
{
	private JPanel _content;
	private JComboBox<String> _textureCb;
	private JComboBox<String> _patternCb;
	private JComboBox<String> _actionCb;
	private JColorChooser _colorChooser;
	
	private Rectangle _textureRect;
	private Rectangle _patternRect;

	private int _leftMargin;
	private int _topMargin;
	
	private HashMap<String, String> _texturePaths;
	private HashMap<String, String> _patternPaths;

	private Image _textureSourceImage;
	private BufferedImage _textureBi;
	private Image _patternSourceImage;
	private BufferedImage _patternBi;
	private BufferedImage _compositeBi;
	private BufferedImageOp _rescaleBiOp;
	
	private boolean _imageLoaded;
//	GraphicsEnvironment _graphicsEnvironment; 
//	GraphicsDevice _graphicsDevice;  
//	GraphicsConfiguration _graphicsConfiguration;
	
	
	public ColorizerUnitTest()
	{
//		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		//setUndecorated(true);
		setSize(1250, 500);
		setTitle("Colorize UnitTest");
		setBackground(new Color(0xFFFF0000));
		setVisible(true);
		
		
		
		init();
	}
	
	public static void main(String[] args)
	{
		ColorizerUnitTest unitTest = new ColorizerUnitTest();
		
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
		
		initData();
		
		initUi();
		
		
	}
	
	void initUi()
	{
		int spacer = 4;
		
		JLabel label = new JLabel("Select a texture");
		_content.add(label);
		label.setBounds(new Rectangle(_leftMargin, _topMargin, 120, 18));
		
		Font font = new Font("Arial", Font.BOLD, 10);
		label.setFont(font);
		 
		String[] values = new String[_texturePaths.size()];
		int i = 0;
		
		for (Map.Entry<String, String> entry: _texturePaths.entrySet()) 
		{ 
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			values[i] = entry.getKey();
			i++;
		}

		_textureCb = new JComboBox<String>(values);
		_content.add(_textureCb);
		_textureCb.setName("textures");
		_textureCb.setBounds(_leftMargin, label.getY() + label.getHeight() + spacer, 120, 18);
		_textureCb.setFont(font);
		_textureCb.addActionListener(this);
//		_textureCb.addPopupMenuListener(this);
		
//		_colorChooser = new JColorChooser();
//		_content.add(_colorChooser);
//		_colorChooser.setBounds(_leftMargin, 100, 1000, 1000);

		//
		String[] actions = {"clear"};
		
		_actionCb = new JComboBox<String>(actions);
		_content.add(_actionCb);
		_actionCb.setName("actions");
		_actionCb.setBounds(_textureCb.getX() + _textureCb.getWidth() + spacer, label.getY() + label.getHeight() + spacer, 120, 18);
		_actionCb.setFont(font);
		_actionCb.addActionListener(this);
		
		_textureRect = new Rectangle(_actionCb.getX() + _actionCb.getWidth() + spacer, _topMargin, 140, 140);
		Graphics2D g2d = (Graphics2D) _content.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(_textureRect.x, _textureRect.y, _textureRect.width, _textureRect.height);
		//
		label = new JLabel("Select a color pattern");
		_content.add(label);
		label.setBounds(new Rectangle(_leftMargin, _textureCb.getWidth() + _textureCb.getVisibleRect().height + spacer, 120, 18));
		label.setFont(font);
		
		values = new String[_patternPaths.size()];
		i = 0;
		
		for (Map.Entry<String, String> entry: _patternPaths.entrySet()) 
		{ 
//			System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
			values[i] = entry.getKey();
			i++;
		}

		_patternCb = new JComboBox<String>(values);
		_content.add(_patternCb);
		_patternCb.setName("patterns");
		_patternCb.setBounds(_leftMargin, label.getY() + label.getHeight() + spacer, 120, 18);
		_patternCb.setFont(font);
		_patternCb.addActionListener(this);
//		_patternCb.addPopupMenuListener(this);
		
		_patternRect = new Rectangle(_textureRect.x, _textureRect.y + _textureRect.height + spacer, 140, 140);
		g2d = (Graphics2D) _content.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(_patternRect.x, _patternRect.y, _patternRect.width, _patternRect.height);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		
		switch(cb.getName())
		{
			case "textures":
				
				loadTextureImage(_texturePaths.get(_textureCb.getSelectedItem()));
				
				renderComposite();
				
				break;
				
			case "patterns":
				
				loadPatternImage(_patternPaths.get(_patternCb.getSelectedItem()));
				
				renderComposite();
				
				break;
				
			case "actions":
				System.out.println("clear");
				Graphics2D g = (Graphics2D) _content.getGraphics(); 
//				g.clearRect(300, _topMargin, _textureImage.getWidth(), _textureImage.getHeight());
				g.setColor(new Color(0xFFFFFF));
				g.fillRect(_textureRect.x, _textureRect.y, _textureRect.width, _textureRect.height);
				g.fillRect(_patternRect.x, _patternRect.y, _patternRect.width, _patternRect.height);
				break;
		}
	}
	
	private void renderComposite()
	{
		if(_textureSourceImage == null || _patternSourceImage == null)
			return;
		
		System.out.println("buildComposite");
		
		//clear old composite
		Graphics2D g = (Graphics2D) _content.getGraphics();
		g.setColor(Color.WHITE);
		g.fillRect(_textureRect.x + _textureRect.width + 10, _textureRect.y, 2000, 2000);
		
		//new base composite 
		_compositeBi = new BufferedImage(	(int) (_textureSourceImage.getWidth(null) * 1),
											(int) (_textureSourceImage.getHeight(null)), 
											BufferedImage.TYPE_INT_ARGB);
		
		
		int w; //= _compositeBi.getWidth();
		int h; //= _compositeBi.getHeight();
		
		if(_patternBi.getWidth() * _patternBi.getHeight() > _textureBi.getWidth() * _textureBi.getHeight())
		{
			w = _patternBi.getWidth();
			h = _patternBi.getHeight();
		}
		else
		{
			w = _textureBi.getWidth();
			h = _textureBi.getHeight();
		}
//		
		int col;
		int row;
		int color;
		int[] textureChannels = new int[4];
		int[] patternChannels = new int[4];
		int textureColor;
		int patternColor;
		int newColor;
//		//colorize pixels
		
		int patternW = _patternBi.getWidth();
		int patternH = _patternBi.getHeight();
		int textureW = _textureBi.getWidth();
		int textureH = _textureBi.getHeight();
		
		for(row=0; row<h; row++)
		{
			for(col=0; col<w; col++)
			{
				if(col >= patternW || col >= textureW)
					continue;
				
				if(row >= patternH || row >= textureH)
					continue;
				
				
				textureColor = _textureBi.getRGB(col, row);
				patternColor = _patternBi.getRGB(col, row);

				textureChannels[0] = 0x00FF & (textureColor >> 16);
				textureChannels[1] = 0x0000FF & (textureColor >> 8);
				textureChannels[2] = 0x000000FF & (textureColor);
				textureChannels[3] = 0xFF & (textureColor >> 24);
				
				patternChannels[0] = 0x00FF & (patternColor >> 16);
				patternChannels[1] = 0x0000FF & (patternColor >> 8);
				patternChannels[2] = 0x000000FF & (patternColor);
				patternChannels[3] = textureChannels[3]; ////0xFF & (patternColor >> 24);
				
				//if texture is zero alpha dont bother calcing anything
				if(textureChannels[3] == 0x0)
				{
//					trace(channel);
					_compositeBi.setRGB(col, row, textureColor);
					continue;
				}
//				
//				channel = channel & 0x000000FF;
//				
//				if(isNaN(channel))
//				{
//					channel = 0;
//				}
//				
				newColor = colorizeByChannel(textureChannels, patternChannels);
					
				_compositeBi.setRGB(col, row, newColor); //Math.floor(Math.random() * 0xFFFFFF));
				
			}
		}
		
		g.drawImage(_compositeBi,
				_textureRect.x + _textureRect.width + 10, 
				_topMargin,
				_textureSourceImage.getWidth(null), 
				_textureSourceImage.getHeight(null),
				null);
		
	}
	
	int colorizeByChannel(int[] textureChannels, int[] patternChannels)
	{
		int color = 0;
		int[] blendedChannels;
		double percent;
		double threshold = 128d;
		double lum = ColorUtil.getLuminosityByChannels(textureChannels);
		threshold += - 32 + 64 * (( lum / 255));
//		threshold += (( lum / 255));
		
		double balanceThreshold = 256 - threshold;
		
		//textureChannels should all be same as they are from pure grayscale source
		
		//dark values
		if(textureChannels[0] <= threshold)
		{
			percent = (1.0d -  (textureChannels[0] / threshold));
			blendedChannels = ColorUtil.blendRgbChannelsByColor(patternChannels, 0x0, percent);
		}
		else //light values
		{
			percent = (textureChannels[0] - threshold) / balanceThreshold;
			
			blendedChannels = ColorUtil.blendRgbChannelsByColor(patternChannels, 0xFFFFFF, percent);
		}
		
		color = textureChannels[3] << 24 | blendedChannels[0] << 16 | blendedChannels[1] << 8 | blendedChannels[2];
		
		return color;
	}
	
	
	private void loadTextureImage(String url)
	{
		System.out.println(url);
		//create observer
		ImageObserver imageObserver = new ImageObserver() 
		{	
			@Override
			public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) 
			{
				if ((infoflags & ALLBITS) != 0) 
				{ 	 
					_imageLoaded = true; 
				 
					System.out.println("Image loading finished!"); 
				 
					return false; 
				}
				    return true; 	
			}
		};
		
		//
 
//		Image sourceImage = Toolkit.getDefaultToolkit().getImage(url);
//		sourceImage.getWidth(imageObserver);
		_textureSourceImage = null;
		try
		{
			_textureSourceImage = ImageIO.read(new File(url));
			_imageLoaded = true;
//			sourceImage.getWidth(imageObserver); 
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return;
		}
		 
		 
		// We wait until the image is fully loaded 
		int breakCnt = 0;
		
		while (!_imageLoaded) 
		{  
		    try 
		    {
		    	breakCnt++;
		    	
		    	if(breakCnt < 100)
		    	{ 
		    		Thread.sleep(100);
		    	}
		    	else
		    	{
		    		System.out.println("loadImage timed out");
		    		return;
		    	}
		    } 
		    catch (InterruptedException e) 
		    { 
		    	System.out.println("failed to load image");
		    	return;
		    } 
		} 
		

		// Create a buffered image from the source image with a format that's compatible with the screen 

		// If the source image has no alpha info use Transparency.OPAQUE instead 
		if(_imageLoaded == false)
		{
			return;
		}
		else
		{
			_imageLoaded = false;			
		}
	
		
//		BufferedImage image = _graphicsConfiguration.createCompatibleImage(sourceImage.getWidth(null), sourceImage.getHeight(null), Transparency.BITMASK); 
//		_textureImage = new BufferedImage(sourceImage.getWidth(null), sourceImage.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		_textureBi = new BufferedImage((int) (_textureSourceImage.getWidth(null) * 1), (int) (_textureSourceImage.getHeight(null)), BufferedImage.TYPE_INT_ARGB);
	
		//
		double scale = .33d;
		int w = _textureSourceImage.getWidth(null);
		int h = _textureSourceImage.getHeight(null);
		int rectW = _textureRect.width;
		int rectH = _textureRect.height;
		
		if(w > h && _textureSourceImage.getWidth(null) > _textureRect.width)
		{
			scale = (double) rectW / w;
		}
		else if(_textureSourceImage.getHeight(null) > _textureRect.height)
		{
			scale = (double) rectH / h;
		}
		
		w = (int) (w * scale);
		h = (int) (h * scale);
		
		Image scaledImage = _textureSourceImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		// Copy image to buffered image 
		Graphics2D g = _textureBi.createGraphics(); 
//		// Paint the image onto the buffered image 
		g.drawImage(_textureSourceImage, 0, 0, (int) (_textureSourceImage.getWidth(null)), (int) (_textureSourceImage.getHeight(null)), null);
////		g.drawImage(scaledImg, 0, 0, (int) (scaledImg.getWidth(null)), (int) (scaledImg.getHeight(null)), null);
		g.dispose();
		
//		int x = 10;  
//		int y = 10;
//		// Get a pixel 
//		int rgb = _textureBi.getRGB(x, y); 
//		System.out.println("Pixel at [" + x + "," + y + "] RGB : " + rgb);

		Graphics2D g2d = (Graphics2D) _content.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(_textureRect.x, _textureRect.y, _textureRect.width, _textureRect.height);
		//a resize sample
		g2d.drawImage(scaledImage, _textureRect.x, _textureRect.y, (int) (scaledImage.getWidth(null)), (int) (scaledImage.getHeight(null)), null);
//		g2d.drawImage(_textureBi, _textureRect.x + _textureRect.width + 10, _topMargin, _textureBi.getWidth(), _textureBi.getHeight(), null);

//		g2d.dispose();
//		//resize test with rendering hints
//		g2d.setComposite(AlphaComposite.Src);
//		 
//		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,7
//		RenderingHints.VALUE_INTERPOLATION_BILINEAR);
//		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
//		RenderingHints.VALUE_RENDER_QUALITY);
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//		RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
	private void loadPatternImage(String url)
	{
		System.out.println(url);
		
		try
		{
			_patternSourceImage = ImageIO.read(new File(url));
			_imageLoaded = true;
//			sourceImage.getWidth(imageObserver); 
		}
		catch(IOException e)
		{
			System.out.println(e.getMessage());
			return;
		}
			
		_patternBi = new BufferedImage((int) (_patternSourceImage.getWidth(null) * 1), (int) (_patternSourceImage.getHeight(null)), BufferedImage.TYPE_INT_ARGB);
	
		//
		double scale = .33d;
		int w = _patternSourceImage.getWidth(null);
		int h = _patternSourceImage.getHeight(null);
		int rectW = _patternRect.width;
		int rectH = _patternRect.height;
		
		if(w > h && _patternSourceImage.getWidth(null) > _patternRect.width)
		{
			scale = (double) rectW / w;
		}
		else if(_patternSourceImage.getHeight(null) > _patternRect.height)
		{
			scale = (double) rectH / h;
		}
		
		w = (int) (w * scale);
		h = (int) (h * scale);
		
		Image scaledImage = _patternSourceImage.getScaledInstance(w, h, Image.SCALE_SMOOTH);
		
		// Copy image to buffered image 
		Graphics2D g = _patternBi.createGraphics(); 
//		// Paint the image onto the buffered image 
		g.drawImage(_patternSourceImage, 0, 0, (int) (_patternSourceImage.getWidth(null)), (int) (_patternSourceImage.getHeight(null)), null);
////		g.drawImage(scaledImg, 0, 0, (int) (scaledImg.getWidth(null)), (int) (scaledImg.getHeight(null)), null);
		g.dispose();

		Graphics2D g2d = (Graphics2D) _content.getGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(_patternRect.x, _patternRect.y, _patternRect.width, _patternRect.height);
		//a resize sample
		g2d.drawImage(scaledImage, _patternRect.x, _patternRect.y, (int) (scaledImage.getWidth(null)), (int) (scaledImage.getHeight(null)), null);
//		g2d.drawImage(_textureBi, _textureRect.x + _textureRect.width + 10, _topMargin, _textureBi.getWidth(), _textureBi.getHeight(), null);

	}
	
	
	public void repaint()
	{
		//redraw any fill
		System.out.println("repaint");
		Graphics2D g2d = (Graphics2D) _content.getGraphics();
		g2d.setColor(new Color(0xFFFFFF));
		g2d.fillRect(_textureRect.x, _textureRect.y, _textureRect.width, _textureRect.height);
		g2d.fillRect(_patternRect.x, _patternRect.y, _patternRect.width, _patternRect.height);
	}
	
	void initData()
	{
		_texturePaths = new HashMap<String, String>();
		
		_texturePaths.put("Coin1", "assets/coin1.png");
		_texturePaths.put("Coin2", "assets/coin2.png");
		_texturePaths.put("Chest1", "assets/chest2.png");
		_texturePaths.put("Football1", "assets/football1_sm.jpg");
		_texturePaths.put("Football2", "assets/football2.png");
		_texturePaths.put("Crown1", "assets/crown1_sm.jpg");
		_texturePaths.put("Tank1", "assets/tank1_sm.jpg");
		_texturePaths.put("Spartan1", "assets/spartan1_sm.jpg");
		_texturePaths.put("Seat1", "assets/seat1.png");
		_texturePaths.put("GatorSkin1", "assets/gator.jpg");
		_texturePaths.put("Helm1", "assets/helm1.png");
		_texturePaths.put("Woman1", "assets/woman1.jpg");
//		_texturePaths.put("Sculpture1", "assets/sculpture1.jpg");
		
		_patternPaths = new HashMap<String, String>();
		
		_patternPaths.put("Camo1", "assets/camo1.jpg");
		_patternPaths.put("Camo2", "assets/camo2.jpg");
		_patternPaths.put("Camo3", "assets/camo3.png");
		_patternPaths.put("Camo4", "assets/camo4.jpg");
		_patternPaths.put("WeatheredMetal1", "assets/weathered1.jpg");
		_patternPaths.put("WeatheredMetal2", "assets/weathered2.jpg");
		_patternPaths.put("WeatheredMetal3", "assets/weathered3.jpg");
		_patternPaths.put("WeatheredMetal4", "assets/weathered4.png");
		_patternPaths.put("WeatheredMetal5", "assets/weathered5.jpg");
		_patternPaths.put("WeatheredMetal6", "assets/weathered6.jpg");
		_patternPaths.put("Zebra1", "assets/zebra1.jpg");
		_patternPaths.put("Skulls1", "assets/skulls1.jpg");
		_patternPaths.put("Rainbow1", "assets/rainbow1.jpg");
		_patternPaths.put("Flag1", "assets/flag1.jpg");

		
	}	
//	public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent event)
//	{
//		System.out.println("willBecomeVis");
//	}
//	
//	public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent event)
//	{
//		System.out.println("willBecomeInVis");
//		repaint();
//	}
//	
//	public void popupMenuCanceled(javax.swing.event.PopupMenuEvent event)
//	{
//		System.out.println("popUpCanceled");
//		repaint();
//	}
}
