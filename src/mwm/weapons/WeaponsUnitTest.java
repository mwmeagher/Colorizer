package mwm.weapons;




import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import mwm.geom.Particle;

public class WeaponsUnitTest extends JPanel implements ActionListener//Runnable 
{
	private JPanel _content;
	
	private boolean _initialized;
	private boolean _isActive;
	
	private int _fireFreq;
	private int _heatSeekerFreq;
	private int _fireFreqCnt;		
	
	private Timer _timer;
	private LinkedList<Particle> _particles;
	private BufferedImageOp _rescaleOp;
	
	private int _frameRate = (int) 1000/30;

	private Graphics bufferGraphics;
	private Image offScreen;
	private Dimension dim;
	
	public WeaponsUnitTest()
	{
		super();
		System.out.println("WeaponsUnitTest");
		
//		init();
	}
	
	public static void main(String[] args)
	{
		WeaponsUnitTest unitTest = new WeaponsUnitTest();
		
	}
	
	public void init()
	{
		System.out.println("WeaponsUnitTest.init");
		
		_isActive = true;
		_fireFreq = 15;
		_heatSeekerFreq = 15 * 10;
		_fireFreqCnt = 0;
		
		dim = getSize();
		System.out.println(dim.width + " : " + dim.height);
		
		_content = new JPanel();
//		setContentPane(_content);
		add(_content);
		_content.setSize(dim.width, dim.height);
		
		_content.setLayout(null);
		
		JLabel label = new JLabel("Select a texture");
		_content.add(label);
		label.setBounds(new Rectangle(20, 20, 120, 18));
		
//		offScreen = createImage(dim.width, dim.height);
//		bufferGraphics = offScreen.getGraphics();
				
		Renderer.initialize();
		
		int cnt = 0;
	
		initParticles();
		
		float channels[] = new float[4];
		channels[0] = .9f;
		channels[1] = .9f;
		channels[2] = .9f;
		channels[3] = 1.0f;
		
		float offSets[] = new float[4];
		offSets[0] = 0.0f;
		offSets[1] = 0.0f;
		offSets[2] = 0.0f;
		offSets[3] = 0.0f;
		
		_rescaleOp = new RescaleOp(channels, offSets, null);
		
		Graphics2D g = (Graphics2D) getGraphics();
		g.setColor(new Color( 0xFF000000)); //0xFF000000 | (int) (Math.random() * 256) << 16 | (int) (Math.random() * 256) << 8 | (int) (Math.random() * 256)));
		g.fillRect(0, 0, Renderer.WIDTH, Renderer.HEIGHT);
		
		_timer = new Timer(_frameRate,  e -> actionPerformed(e));
		_timer.start();
		
		_initialized = true;
		
//		while(_isActive)
//		{
//			try
//			{
//				Thread.sleep(frameRate);
//			}
//			
//			catch(InterruptedException e)
//			{
//				System.out.println(e.getMessage());
//			}
//			
//			cnt++;
////			_isActive = false;
//			
//			
////			System.out.println("cnt: " + cnt);
//			
//			update();
//		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
//		System.out.println("actionPerformed");
//		update();
//		repaint();
		repaint();
//		update();
//		paintComponent(getGraphics());
//		paint(getGraphics());
	}

//	public void update(Graphics g)
//	{
//		repaint();
//	}
	
	public void paint(Graphics g)
	{
//		super.paintComponents(g);
		
		draw();
	}
	
	
	private void initParticles()
	{
		System.out.println("initParticles");
		_particles = new LinkedList<Particle>();
		
		//create particles
		int len = 100;
		double circle = 2 * Math.PI;
		
		for(int i=0; i<len; i++)
		{
			double x = (int) (Math.random() * dim.width); //Renderer.WIDTH);
			double y = (int) (Math.random() * dim.height); //Renderer.HEIGHT);
			
			Particle p = new Particle();
			p.x = x;
			p.y = y;
			p.color = 0xFF000000;
//			p.color = 0xFF000000 | (int) (Math.random() * 256) << 16 | (int) (Math.random() * 256) << 8 | (int) (Math.random() * 256);
			
			p.vX = Math.cos(Math.random() * circle) * 1;
			p.vY = Math.sin(Math.random() * circle) * 1;
			
			_particles.add(p);
		}
	}
	
	private void draw()
	{
		if(!_initialized)
			return;
		System.out.println(_initialized + " - draw: " + dim.width + " : " + dim.height);
		
		//clear screen
		Graphics contentG = (Graphics2D) getGraphics();
		//clear screen
//		contentG.clearRect(0, 0, Renderer.WIDTH, Renderer.HEIGHT);
//		contentG.setColor(new Color(0xFF000000));
//		contentG.fillRect(0, 0, dim.width, dim.height); //Renderer.WIDTH, Renderer.HEIGHT);
//		g.drawImage(arg0, arg1, arg2, arg3)
		
//		if(true)
//			return;
		
//		Graphics2D g = (Graphics2D) Renderer.CANVAS.getGraphics();
		
//		contentG.drawImage(Renderer.CANVAS, _rescaleOp, 0, 0);
//		Renderer.CANVAS.getRaster().
//		g.setColor(new Color(0x00000000));
//		Renderer.CANVAS = new BufferedImage(Renderer.WIDTH, Renderer.HEIGHT, BufferedImage.TYPE_INT_ARGB);
//		Renderer.CANVAS.getGraphics().setColor(new Color(0x0));
		
		//iterate thru particles
		int buffer = 40;
//		contentG.setColor(new Color(0xFF00FF00));
//		contentG.setColor(new Color(0xFFFFFF));
		
//		for(Particle p : _particles)
		
		ListIterator<Particle> listIterator = _particles.listIterator();
		Particle p = listIterator.next();
		while(p != null)
		{
			System.out.println(p.x + " : "+ p.y + " : "+ p.color);
			
//			p.color = 0xFF000000 | (int) (Math.random() * 256) << 16 | (int) (Math.random() * 256) << 8 | (int) (Math.random() * 256);
			
			p.x += p.vX;
			if(p.x <= buffer)
			{
				p.x = buffer;
				p.vX *= -1;
			}
			else if(p.x >= dim.width - buffer)
			{
				p.x = dim.width - buffer;
				p.vX *= -1;
			}
			
			p.y += p.vY;
			if(p.y <= buffer)
			{
				p.y = buffer;
				p.vY *= -1;
			}
			else if(p.y >= dim.height - buffer)
			{
				p.y = dim.height - buffer;
				p.vY *= -1;
			}
			
//			Renderer.CANVAS.setRGB((int) p.x, (int) p.y, p.color);
//			Renderer.CANVAS.setRGB((int) p.x + 1, (int) p.y + 1, p.color);
//			contentG.fillRect((int) p.x, (int) p.y, 3, 3);
			
			if(listIterator.hasNext())
				p = listIterator.next();
			else
				p = null;
		}
		

//		contentG.drawImage(Renderer.CANVAS, 0, 0, Renderer.WIDTH, Renderer.HEIGHT, null); //Renderer.WIDTH, Renderer.HEIGHT);
//		contentG.finalize();
		
	}
//	@Override
//	public void run() 
//	{
//		// TODO Auto-generated method stub
//		
//		//render some code
//	}

//	@Override 
//	public void paintComponent(Graphics g)
//	{
////		System.out.println("WeaponsUnitTest.paintComponent");
//		update();
////		repaint();
////		super.paintComponent(g);
//		
//	}
}
