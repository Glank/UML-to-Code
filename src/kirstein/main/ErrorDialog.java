package kirstein.main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ErrorDialog extends JDialog implements ActionListener{
	private static final long serialVersionUID = 3685247261304712226L;
	private JTextArea area;
	private JScrollPane scroller;
	private JButton ok;
	
	private ErrorDialog(String errorText){
		setTitle("Error");
		area = new JTextArea(5,25);
		area.setText(errorText);
		scroller = new JScrollPane(area);
		ok = new JButton("OK");
		ok.addActionListener(this);
		setLayout(new BorderLayout());
		add(scroller, BorderLayout.CENTER);
		setModal(true);
		pack();
	}
	
	public static void show(String errorText){
		new ErrorDialog(errorText).setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		dispose();
	}
}
