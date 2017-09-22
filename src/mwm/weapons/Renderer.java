package mwm.weapons;

import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.RescaleOp;

public class Renderer
{
	public static int WIDTH = 1400;
	public static int HEIGHT = 1000;
	public static BufferedImage CANVAS;
	public static RescaleOp FADE_CANVAS;
	
	private static BufferedImageOp FADE_TRANSFORM;
	private static Rectangle rect;
	
	
	public static void initialize()
	{
		if(CANVAS == null)
		{
//			rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
//			WIDTH = rect.width;
//			HEIGHT = rect.height;
			
			CANVAS = new BufferedImage(	WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
			FADE_CANVAS = new RescaleOp(new float[]{.99f,  .99f, .99f}, new float[]{0,0,0}, null);
//			
////			FADE_TRANSFORM = new ColorTransform(.95, .95, .95  , 1, 0,0,0,0);
//			FADE_TRANSFORM = new ColorTransform(1, 1, 1, .95, 0,0,0,0);
		}
	}
}
