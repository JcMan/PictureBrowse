import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.*;

public class LoginDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5545133930965852677L;
	public static boolean isLogin=false;
	JTextField userNameText;
	JPasswordField passwordText;
	public LoginDialog(){
		
		this.setBounds(420, 210, 400, 250);
		this.setTitle("��¼");
		this.setUndecorated(true);
		JLabel label=new JLabel();
		label.setSize(400,250);
		
		this.add(label,BorderLayout.CENTER);
		label.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/LoginDialog.jpg")));
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		this.setLayout(null);
		
		userNameText=new JTextField(20);
		passwordText=new JPasswordField(20);         //�û����������
		
		JButton button_login=new JButton("��¼");
		JButton button_register=new JButton("ע��");
		button_login.setFont(new Font("����",Font.PLAIN,10));
		button_register.setFont(new Font("����",Font.PLAIN,10));
		
		Container c=getContentPane();
		((JPanel)c).setOpaque(false);
		this.add(userNameText);
		this.add(passwordText);
		this.add(button_login);
		this.add(button_register);
		userNameText.setBounds(200, 126, 120, 25);
		passwordText.setBounds(200, 156, 120, 25);
		passwordText.setEchoChar('*');               //������������ʾ�ַ�
		button_login.setBounds(330, 126, 55, 25);
		button_register.setBounds(330, 156,55,25);
		
		JButton button_close=new JButton();
		button_close.setBounds(374, 0, 26, 26);
		button_close.setContentAreaFilled(false);           //�����ư�ť����
		button_close.setBorderPainted(false);               //�����ư�ť�߿�
		button_close.setBorder(null);
		
		button_close.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/Close_White.PNG")));///////////
		button_close.setRolloverIcon(new ImageIcon(LoginDialog.class.getResource("/res/Close_Red.PNG")));/////   ��ť�ı任
		button_close.setPressedIcon(new ImageIcon(LoginDialog.class.getResource("/res/Close_White.PNG")));/////
		
		button_close.addActionListener(new ActionListener(){                  //�رհ�ť��ʵ�ַ���
			public void actionPerformed(ActionEvent e){                      
				setVisible(false);
			}                                                 
		});
		this.add(button_close);
		passwordText.addActionListener(new ActionListener(){                //�������Ӽ�����
			public void actionPerformed(ActionEvent e){
				File file=new File("data.txt");
				if(!file.exists()){
					JOptionPane.showMessageDialog(null, "��¼��������Ϊ�գ���ע���˺�","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						FileReader fr=new FileReader(file);
						BufferedReader reader=new BufferedReader(fr);
						String getName=userNameText.getText();
						String getPassword=new String(passwordText.getPassword());
						String strName=null,strPassword=null;
						while((strName=reader.readLine())!=null){
								strPassword=reader.readLine();
								if(getName.equals(strName)&&getPassword.equals(strPassword)){
									isLogin=true;
									setVisible(false);
									JOptionPane.showMessageDialog(null, "��¼�ɹ���","ϵͳ��ʾ",JOptionPane.INFORMATION_MESSAGE);
									Main.loginItem.setEnabled(false);
									break;
								}
						}
						if(isLogin==false){
							JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ������������û���������!","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
							userNameText.setText(null);
							passwordText.setText(null);
							userNameText.requestFocus();
						}
						fr.close();
						reader.close();
					}catch(Exception ee){
						ee.printStackTrace();
					}
				}
			}
		});                                                    
		///////////////////////////////////////////////////////////////////////////////////////////////
		button_register.addActionListener(new ActionListener(){                 //ע�ᰴť��Ӽ�����
			public void actionPerformed(ActionEvent e){
				File file=new File("data.txt");
				String getName=userNameText.getText();
				String getPassword=new String(passwordText.getPassword());
				if(getName.isEmpty()||getPassword.isEmpty()){
					JOptionPane.showMessageDialog(null, "��ȷ���û����������������ע��!","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
				}
				else{
					
					if(file.exists()==false){
						try{
							file.createNewFile();
						}catch(Exception we){
							we.printStackTrace();
						}
					}else{
						if(isUserNameEqual(file)==false){                        //�ж��Ƿ��û����ظ�
							JOptionPane.showMessageDialog(null, "���û����ѱ�ʹ�ã�����������");
							userNameText.setText(null);
							passwordText.setText(null);
							userNameText.requestFocus();
						}
						else{                                           
							try{
								FileWriter fw=new FileWriter(file,true);
								BufferedWriter writer=new BufferedWriter(fw);
								writer.write(getName);
								writer.newLine();
								writer.write(getPassword);
								writer.newLine();
								writer.flush();               //���������е�����д���ļ�
								fw.close();
								writer.close();
								JOptionPane.showMessageDialog(null, "ע��ɹ�!","ϵͳ��ʾ",JOptionPane.INFORMATION_MESSAGE);
								
							}catch(Exception ee){
								ee.printStackTrace();
							}
						}
					}
				}
			}
		});                                
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		button_login.addActionListener(new ActionListener(){                //��¼��ť��Ӽ�����
			public void actionPerformed(ActionEvent e){
				File file=new File("data.txt");
				if(!file.exists()){
					JOptionPane.showMessageDialog(null, "��¼��������Ϊ�գ���ע���˺�","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
				}
				else{
					try{
						FileReader fr=new FileReader(file);
						BufferedReader reader=new BufferedReader(fr);
						String getName=userNameText.getText();
						String getPassword=new String(passwordText.getPassword());
						String strName=null,strPassword=null;
						while((strName=reader.readLine())!=null){
								strPassword=reader.readLine();
								if(getName.equals(strName)&&getPassword.equals(strPassword)){
									isLogin=true;
									setVisible(false);
									JOptionPane.showMessageDialog(null, "��¼�ɹ���","ϵͳ��ʾ",JOptionPane.INFORMATION_MESSAGE);
									Main.loginItem.setEnabled(false);
									break;
								}
						}
						if(isLogin==false){
							JOptionPane.showMessageDialog(null, "��¼ʧ�ܣ������������û���������!","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
							userNameText.setText(null);
							passwordText.setText(null);
							userNameText.requestFocus();
						}
						fr.close();
						reader.close();
					}catch(Exception ee){
						ee.printStackTrace();
					}
				}
			}
		});
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setModal(true);
		
		this.setVisible(true);
		
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	boolean isUserNameEqual(File file){  
		FileReader fr = null;                             //�Զ��庯�����ж��û����Ƿ��ظ�
		BufferedReader reader = null;
		try{
			fr=new FileReader(file);
			reader=new BufferedReader(fr);
			String getName=userNameText.getText();
			String strName=null;
			while((strName=reader.readLine())!=null){
				reader.readLine();
				if(getName.equals(strName)){
					fr.close();
					reader.close();
					return false;	
				}
			}
			
		}catch(Exception e){}
		try{
			fr.close();
			reader.close();
		}catch(Exception eee){}
		return true;
	}
}
