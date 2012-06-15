package kirstein.htmlutil;

public class Test {
	public static void main(String[] args){
		String out = HtmlCoder.decode("lt=&lt;-+&#163;+&#163;+&#163;");
		System.out.println(out);
	}
}
