import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class SetSpeedDialog extends JDialog {
	private JTextField text;
	private JLabel label ;
	private JButton b = new JButton("ȷ��");
	public SetSpeedDialog(){
		text = new JTextField(10);
		label = new JLabel("����");
		this.setSize(250, 80);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("�����Զ�����ٶ�");
		this.setLayout(new FlowLayout());
		this.add(text);
		this.add(label);
		this.add(b);
		b.addActionListener(new ActionListener(){
			public void  actionPerformed(ActionEvent e){
				Main.autoLookSpeed=Integer.parseInt(text.getText());
				setVisible(false);
			}
		
		});
		text.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.autoLookSpeed=Integer.parseInt(text.getText());
				setVisible(false);
			}
		});
		
	}
}
