package kirstein.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;

public class Test {
	public static void main(String[] args) throws Throwable{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.parse("test//xml_test_0.dat");
		Element root = dom.getDocumentElement();
		System.out.println(root.getNodeName());
		System.out.println(root.getTextContent());
		NodeList children = root.getChildNodes();
		for(int i = 0; i < children.getLength(); i++){
			System.out.println(i+"n) "+children.item(i).getNodeName());
			System.out.println(i+"v) "+children.item(i).getNodeValue());
			System.out.println(i+"c) "+children.item(i).getTextContent());
			NamedNodeMap nnm = children.item(i).getAttributes();
			if(nnm!=null)
			for(int j = 0; j < nnm.getLength(); j++)
				System.out.println(nnm.item(j));
		}
	}
}
