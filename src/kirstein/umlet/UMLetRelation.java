package kirstein.umlet;

import java.awt.Point;
import java.util.Scanner;
import java.util.Vector;

import kirstein.xml.XMLNode;

public class UMLetRelation extends UMLetElement{
	private Vector<Point> points = new Vector<Point>();
	
	public UMLetRelation(XMLNode node){
		super(node);
		String pts = node.getAllChildren("additional_attributes").firstElement().getText();
		Scanner scan = new Scanner(pts);
		scan.useDelimiter(";");
		while(scan.hasNext()){
			int x = Integer.parseInt(scan.next())+getX();
			int y = Integer.parseInt(scan.next())+getY();
			points.add(new Point(x,y));
		}
	}

	
	public Point getStartPoint(){
		return points.lastElement();
	}
	
	public Point getEndPoint(){
		return points.firstElement();
	}
	
	public String toString(){
		return "UMLetRelation:\n"+getStartPoint()+"\n"+getEndPoint()+"\n"+getTotalText();
	}
	
	public String getLineTypeDefinition(){
		int equals = getTotalText().indexOf('=');
		String lineType = getTotalText().substring(equals+1);
		return lineType;
	}
	
	public UMLetRelationDirection getDirection(){
		String lineType = getLineTypeDefinition();
		boolean forward = lineType.startsWith("<");
		boolean backward = lineType.startsWith(">");
		if(forward && backward)
			return UMLetRelationDirection.BIDIRECTIONAL;
		if(forward)
			return UMLetRelationDirection.FORWARD;
		if(backward)
			return UMLetRelationDirection.BACKWARD;
		return UMLetRelationDirection.NON_DIRECTIONAL;
	}
	
	private UMLetRelationPointType getPointType(int characters){
		switch(characters){
		case 0: return UMLetRelationPointType.NONE;
		case 1: return UMLetRelationPointType.LINE_POINT;
		case 2: return UMLetRelationPointType.HOLLOW_POINT;
		case 3: return UMLetRelationPointType.HOLLOW_DIAMOND;
		case 4: return UMLetRelationPointType.SOLID_DIAMOND;
		default: throw new RuntimeException("Invalid UMLet Relationship Point Type");
		}
	}
	
	public UMLetRelationPointType getForwardPointType(){
		String lineType = getLineTypeDefinition();
		int lastLeft = lineType.lastIndexOf('<');
		lastLeft++;
		return getPointType(lastLeft);
	}
	
	public UMLetRelationPointType getBackwardPointType(){
		String lineType = getLineTypeDefinition();
		int firstRight = lineType.indexOf('>');
		if(firstRight==-1)
			return getPointType(0);
		return getPointType(lineType.length()-firstRight);
	}
	
	public UMLetRelationLineType getLineType(){
		String lineType = getLineTypeDefinition();
		int lastLeft = lineType.lastIndexOf('<');
		char lineDefChar = lineType.charAt(lastLeft+1);
		switch(lineDefChar){
		case '-': return UMLetRelationLineType.SOLID;
		case '.': return UMLetRelationLineType.DASHED;
		default: throw new RuntimeException("Invalid UMLet Relationship Line Type");
		}
	}
	
	private static UMLetClass getClass(Iterable<UMLetClass> classes, Point p){
		for(UMLetClass c:classes){
			if(c.liesOnBounds(p))
				return c;
		}
		return null;
	}
	
	public UMLetClass getStartClass(Iterable<UMLetClass> classes){
		return getClass(classes, getStartPoint());
	}
	
	public UMLetClass getEndClass(Iterable<UMLetClass> classes){
		return getClass(classes, getEndPoint());
	}
}