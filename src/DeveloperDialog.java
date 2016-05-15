import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;


public class DeveloperDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4905045559608437996L;
	Image img;
	Image img1=Toolkit.getDefaultToolkit().getImage(DeveloperDialog.class.getResource("/res/开发者.JPG"));
	public DeveloperDialog(){
		img=Toolkit.getDefaultToolkit().getImage(DeveloperDialog.class.getResource("/res/JFrame.PNG"));
		this.setBounds(430, 200, 400, 300);
		
		
		Container c=getContentPane();
		((JPanel)c).setOpaque(false);
		JLabel label=new JLabel();
		label.setSize(400,300);
		setLayout(null);
		label.setIcon(new ImageIcon(DeveloperDialog.class.getResource("/res/开发者背景.jpg")));
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		
		
		JLabel labelU=new JLabel();
		labelU.setIcon(new ImageIcon(DeveloperDialog.class.getResource("/res/开发者.JPG")));
		labelU.setBounds(280, 0, 100, 120);
		c.add(labelU);
		setTitle("开发者信息");
		this.setIconImage(img);
		this.setModal(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
	class CanPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = -5421009580980476925L;

		public void piantComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			Font fon=new Font("楷体",Font.PLAIN,15);
			g2.setFont(fon);
			g2.setColor(Color.BLACK);
			g2.drawString("2a5sh5", 10, 10);
			g2.drawImage(img1,0,0,this);
		}
	}
}
