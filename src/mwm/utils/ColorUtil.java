package mwm.utils;

public class ColorUtil 
{
	
	static public double getLuminosityByChannels(int[] colorChannels)
	{
		double lum = .299d * colorChannels[0] + .587d * colorChannels[1] + .114d * colorChannels[2];
		
		return lum;
	}
	
	public static int[] blendRgbChannelsByColor(int[] channels, int color, double percent)
	{
		int[] blendedChannels  = new int[channels.length];
		
		color = 0x00FFFFFF & color;
		
		int r = (0x00FF0000 & color) >> 16;
		int g = (0x0000FF00 & color) >> 8;
		int b = (0x000000FF & color);
		
		blendedChannels[0] = (int) (channels[0] + (r - channels[0]) * percent);
		blendedChannels[1] = (int) (channels[1] + (g - channels[1]) * percent);
		blendedChannels[2] = (int) (channels[2] + (b - channels[2]) * percent);
		
		if(channels.length == 4) 
		{
			blendedChannels[3] = channels[3];
		}
		
		return blendedChannels;
	}
	
	
	
	
//	static public function getLuminousity(color:uint):Number
//	{
//		var b:uint = color & 0x0000FF;
//		var g:uint = ( color & 0x00FF00 ) >> 8;
//		var r:uint = ( color & 0xFF0000 ) >> 16;
//		//var lum:Number = .28*r + .606*g + .104*b;
//		//sqrt( 0.299*R^2 + 0.587*G^2 + 0.114*B^2 )
//		var lum:Number = .299*r + .587*g + .114*b;
//		//			var lum:Number = Math.sqrt(.299*r*r + .587*g*g + .114*b*b);
//		
//		//			trace("getLuminsousity: color: " + color + " : " + lum);
//		return lum;
//	}

}
