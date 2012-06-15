package kirstein.xml;

import org.w3c.dom.Node;

public class Attribute {
	private String name;
	private String value;
	
	public Attribute(Node n){
		this(n.getNodeName(), n.getNodeValue());
	}
	
	public Attribute(String name, String value){
		this.name = name;
		this.value = value;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValueAsString(){
		return value;
	}
	
	public int getValueAsInt(){
		return Integer.parseInt(value);
	}
	
	public double getValueAsDouble(){
		return Double.parseDouble(value);
	}
	
	public String toString(){
		return name + "=\"" + value + '\"';
	}
}
