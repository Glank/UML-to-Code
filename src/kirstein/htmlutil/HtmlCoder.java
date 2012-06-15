package kirstein.htmlutil;

public class HtmlCoder {
	public static String decode(String in){
		String out = in;
		out = out.replaceAll("&nbsp;", " ");
		out = out.replaceAll("&amp;", "&");
		out = out.replaceAll("&lt;", "<");
		out = out.replaceAll("&gt;", ">");
		int i;
		while((i=out.indexOf("&#"))!=-1){
			int i2 = out.indexOf(";",i);
			String first = out.substring(0,i);
			String num = out.substring(i+2,i2);
			String last = out.substring(i2+1);
			char c = (char)Integer.parseInt(num);
			out = first+c+last;
		}
		return out;
	}
}
