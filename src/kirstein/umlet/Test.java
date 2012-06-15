package kirstein.umlet;

import java.io.File;

public class Test {
	public static void main(String[] args) throws Throwable{
		UMLetDocument doc = new UMLetDocument(new File("C:\\Users\\Ernest\\Documents\\ActiveJavaProjects\\umlet\\test1.uxf"));
		doc.print();
	}
}
