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


public class GuideDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3052074994513644135L;
	
	
	public Font font=new Font("����",Font.PLAIN,17);
	public GuideDialog(){
		
		Image img1=Toolkit.getDefaultToolkit().createImage(GuideDialog.class.getResource("/res/JFrame.PNG"));
		this.setBounds(430, 200, 400, 300);
		
		this.setIconImage(img1);
		JLabel label=new JLabel();
		label.setFont(font);
		label.setSize(400,300);
		
		label.setIcon(new ImageIcon(GuideDialog.class.getResource("/res/GuideDialog.JPG")));
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		setLayout(null);
		Container c=getContentPane();
		((JPanel)c).setOpaque(false);
		CanvasPanel canvasPanel=new CanvasPanel();
		c.add(canvasPanel);
		canvasPanel.setOpaque(false);
		canvasPanel.setBounds(15, 20, 342, 200);
		
		
		setTitle("ʹ��ָ��");
		
		this.setModal(true);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
	}
}
class CanvasPanel extends JPanel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -784226480928736144L;

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2=(Graphics2D)g;
		Font fon=new Font("����",Font.PLAIN,15);
		g2.setFont(fon);
		g2.setColor(Color.BLACK);
		String str="�����ʵ�������ͼƬ�Ĺ��ܣ���֧�ֵ�ͼƬ��ʽ";
		g2.drawString(str, 15,20);
		g2.drawString(new String("��jpg��jepg��png��gif�ȡ����б������ֹ��ܿ���ʹ��"), 15, 40);
		g2.drawString(new String("��ʹ�ã������������л����������·�ת�����๦"),15,60);
		g2.drawString(new String("�ܾ����ڴ���."), 15,80);
	}

	public void drawMyImage() {
		// TODO Auto-generated method stub
		
	}
}