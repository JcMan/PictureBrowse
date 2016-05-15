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
	private JButton b = new JButton("确定");
	public SetSpeedDialog(){
		text = new JTextField(10);
		label = new JLabel("毫秒");
		this.setSize(250, 80);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setTitle("设置自动浏览速度");
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
