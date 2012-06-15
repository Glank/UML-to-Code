package kirstein.umlet;

import kirstein.xml.XMLNode;

public class UMLetClass extends UMLetElement{
	
	public UMLetClass(XMLNode node){
		super(node);
	}
	
	public String toString(){
		return "UMLetClass:\n"+getTotalText();
	}
}
