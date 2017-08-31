package client;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

public class ImagePanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3399670098865523896L;
	private Image image;
	
	public ImagePanel(Image inImage){
		this.image = inImage;
	}
	
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}
