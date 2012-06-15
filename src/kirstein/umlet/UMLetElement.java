package kirstein.umlet;

import java.awt.Point;
import java.awt.Rectangle;

import kirstein.htmlutil.HtmlCoder;
import kirstein.xml.XMLNode;

public abstract class UMLetElement {
	private int x, y, w, h;
	private String text;
	
	public UMLetElement(XMLNode node){
		//parse panel_attributes text
		text = node.getAllChildren("panel_attributes").firstElement().getText();
		text = HtmlCoder.decode(text);
		//parse coordinates
		XMLNode coordinates = node.getAllChildren("coordinates").firstElement();
		XMLNode xNode = coordinates.getAllChildren("x").firstElement();
		XMLNode yNode = coordinates.getAllChildren("y").firstElement();
		XMLNode wNode = coordinates.getAllChildren("w").firstElement();
		XMLNode hNode = coordinates.getAllChildren("h").firstElement();
		x = Integer.parseInt(xNode.getText());
		y = Integer.parseInt(yNode.getText());
		w = Integer.parseInt(wNode.getText());
		h = Integer.parseInt(hNode.getText());
	}
	
	public String getTotalText(){
		return text;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getWidth(){
		return w;
	}
	
	public int getHeight(){
		return h;
	}
	
	public Rectangle getBounds(){
		return new Rectangle(x,y,w,h);
	}
	
	public boolean liesOnBounds(Point p){
		if(x==p.getX() || x+w==p.getX())
			return p.getY()>=y && p.getY()<=y+h;
		if(y==p.getY() || y+h==p.getY())
			return p.getX()>=x && p.getX()<=x+w;
		return false;
	}
}
