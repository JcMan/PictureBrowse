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
		this.setTitle("登录");
		this.setUndecorated(true);
		JLabel label=new JLabel();
		label.setSize(400,250);
		
		this.add(label,BorderLayout.CENTER);
		label.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/LoginDialog.jpg")));
		this.getLayeredPane().add(label,new Integer(Integer.MIN_VALUE));
		this.setLayout(null);
		
		userNameText=new JTextField(20);
		passwordText=new JPasswordField(20);         //用户名和密码框
		
		JButton button_login=new JButton("登录");
		JButton button_register=new JButton("注册");
		button_login.setFont(new Font("楷体",Font.PLAIN,10));
		button_register.setFont(new Font("楷体",Font.PLAIN,10));
		
		Container c=getContentPane();
		((JPanel)c).setOpaque(false);
		this.add(userNameText);
		this.add(passwordText);
		this.add(button_login);
		this.add(button_register);
		userNameText.setBounds(200, 126, 120, 25);
		passwordText.setBounds(200, 156, 120, 25);
		passwordText.setEchoChar('*');               //设置密码框的显示字符
		button_login.setBounds(330, 126, 55, 25);
		button_register.setBounds(330, 156,55,25);
		
		JButton button_close=new JButton();
		button_close.setBounds(374, 0, 26, 26);
		button_close.setContentAreaFilled(false);           //不绘制按钮区域
		button_close.setBorderPainted(false);               //不绘制按钮边框
		button_close.setBorder(null);
		
		button_close.setIcon(new ImageIcon(LoginDialog.class.getResource("/res/Close_White.PNG")));///////////
		button_close.setRolloverIcon(new ImageIcon(LoginDialog.class.getResource("/res/Close_Red.PNG")));/////   按钮的变换
		button_close.setPressedIcon(new ImageIcon(LoginDialog.class.getResource("/res/Close_White.PNG")));/////
		
		button_close.addActionListener(new ActionListener(){                  //关闭按钮的实现方法
			public void actionPerformed(ActionEvent e){                      
				setVisible(false);
			}                                                 
		});
		this.add(button_close);
		passwordText.addActionListener(new ActionListener(){                //密码框添加监视器
			public void actionPerformed(ActionEvent e){
				File file=new File("data.txt");
				if(!file.exists()){
					JOptionPane.showMessageDialog(null, "登录资料内容为空，请注册账号","系统提示",JOptionPane.ERROR_MESSAGE);
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
									JOptionPane.showMessageDialog(null, "登录成功！","系统提示",JOptionPane.INFORMATION_MESSAGE);
									Main.loginItem.setEnabled(false);
									break;
								}
						}
						if(isLogin==false){
							JOptionPane.showMessageDialog(null, "登录失败！请重新输入用户名和密码!","系统提示",JOptionPane.ERROR_MESSAGE);
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
		button_register.addActionListener(new ActionListener(){                 //注册按钮添加监视器
			public void actionPerformed(ActionEvent e){
				File file=new File("data.txt");
				String getName=userNameText.getText();
				String getPassword=new String(passwordText.getPassword());
				if(getName.isEmpty()||getPassword.isEmpty()){
					JOptionPane.showMessageDialog(null, "请确保用户名和密码输入后再注册!","系统提示",JOptionPane.ERROR_MESSAGE);
				}
				else{
					
					if(file.exists()==false){
						try{
							file.createNewFile();
						}catch(Exception we){
							we.printStackTrace();
						}
					}else{
						if(isUserNameEqual(file)==false){                        //判断是否用户名重复
							JOptionPane.showMessageDialog(null, "该用户名已被使用，请重新输入");
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
								writer.flush();               //将缓存区中的内容写入文件
								fw.close();
								writer.close();
								JOptionPane.showMessageDialog(null, "注册成功!","系统提示",JOptionPane.INFORMATION_MESSAGE);
								
							}catch(Exception ee){
								ee.printStackTrace();
							}
						}
					}
				}
			}
		});                                
		///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		button_login.addActionListener(new ActionListener(){                //登录按钮添加监视器
			public void actionPerformed(ActionEvent e){
				File file=new File("data.txt");
				if(!file.exists()){
					JOptionPane.showMessageDialog(null, "登录资料内容为空，请注册账号","系统提示",JOptionPane.ERROR_MESSAGE);
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
									JOptionPane.showMessageDialog(null, "登录成功！","系统提示",JOptionPane.INFORMATION_MESSAGE);
									Main.loginItem.setEnabled(false);
									break;
								}
						}
						if(isLogin==false){
							JOptionPane.showMessageDialog(null, "登录失败！请重新输入用户名和密码!","系统提示",JOptionPane.ERROR_MESSAGE);
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
		FileReader fr = null;                             //自定义函数，判断用户名是否重复
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
