import java.awt.Cursor;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;
public class Main extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -337337039902335432L;
	Image img;
	static LoginDialog loginDialog;
	Font font;
	public static JMenuItem loginItem;
	Image currentImage=Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/res/MainBackGround.JPG"));
	public ImageIcon currentIcon=new ImageIcon(Main.class.getResource("/res/MainBackGround.JPG"));
	String currentImagePath=null;
	File file[];
	CanvasPanel canvas=new CanvasPanel();
	int Width=1200;
	int Height=700;
	int w=1200;
	int h=700;
	int x=0;
	int y=0;
	int nowIndex=0;
	int fileLength=0;
	public int musicCount=0;
	Thread thread;
	public static int autoLookSpeed=1000;
	boolean isClicked = false;
	static int isOpen=0;
	public static int openMusicCount=0;
	public MusicPlayer musicPlayer;
	JCheckBoxMenuItem javaAppearanceItem ;           //java���
	JCheckBoxMenuItem systemAppearanceItem;          //ϵͳ���
	public JPanel picListPanel=null;
	public static void main(String[] args) {
		
		new Main();
		
	}
	public Main(){
		super("���쿴ͼ");
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}                                                     //�������
		
		this.setBounds(80, 10, 1200, 700);
		this.setLayout(null);
		img=Toolkit.getDefaultToolkit().createImage(Main.class.getResource("/res/JFrame.PNG"));
		this.setIconImage(img);      //���ô���ͼ��
		this.setResizable(false);    //ȥ����󻯰�ť
		systemAppearanceItem = new JCheckBoxMenuItem("ϵͳ���");         //�������Ϊϵͳ���
		systemAppearanceItem.setState(true);
		Toolkit toolkit=Toolkit.getDefaultToolkit();
		Image imageY=toolkit.getImage(Main.class.getResource("/res/you.PNG"));
		Image imageZ=toolkit.getImage(Main.class.getResource("/res/zuo.PNG"));
		final Cursor cursorZ=toolkit.createCustomCursor(imageZ, new Point(16,16),"MyCursorZ");
		final Cursor cursorY=toolkit.createCustomCursor(imageY, new Point(16,16),"MyCursorY");
		addProjectMenu(this);
		canvas.setBounds(0, 0, 1200, 700);
		this.add(canvas);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		canvas.addMouseMotionListener(new MouseMotionAdapter(){
			public void mouseMoved(MouseEvent e){
				if(isOpen>0){
					if(e.getX()<=600&&e.getY()>=200&&e.getY()<=400)
						setCursor(cursorZ);
					else if(e.getX()>=600&&e.getY()>=200&&e.getY()<=400)
						setCursor(cursorY);
					else
						setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			}
		});
		canvas.addMouseListener(new MouseAdapter(){
			public void mouseExited(MouseEvent e){
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				
			}
			public void mouseClicked(MouseEvent ee){
				if(ee.getModifiers()==InputEvent.BUTTON1_MASK){                
					if(ee.getX()<=600&&getCursor().getName().equals("MyCursorZ")){          //��һ��
						if(currentImagePath==null){}
						else{
							while(true){
								nowIndex--;
								if(nowIndex<0)
									nowIndex=file.length-1;
								currentImagePath=file[nowIndex].getAbsolutePath();
								if(currentImagePath.endsWith("jpg")||currentImagePath.endsWith("png")||currentImagePath.endsWith("jepg")||
										currentImagePath.endsWith("gif")){
									currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
									currentIcon=new ImageIcon(currentImagePath);
									canvas.drawMyImage();
									break;
								}
								
							}
						}
					}
					else if(ee.getX()>600&&getCursor().getName().equals("MyCursorY")){     //��һ��
						if(currentImagePath==null){}
						else{
							while(true){
								nowIndex++;
								if(nowIndex==file.length)
									nowIndex=0;
								currentImagePath=file[nowIndex].getAbsolutePath();
								if(currentImagePath.endsWith("jpg")||currentImagePath.endsWith("png")||currentImagePath.endsWith("jepg")||
										currentImagePath.endsWith("gif")){
									currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
									currentIcon=new ImageIcon(currentImagePath);
									canvas.drawMyImage();
									break;
								}
								
							}
						}
					}
				}
			}
		});
		
		
	}
	/**
	 * //����java���
	 */
	void setJavaAppearance(){                    
		 try{
             UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
             SwingUtilities.updateComponentTreeUI(this);
         }catch(Exception exe){}
	}
	void setSystemAppearance(){                  //����ϵͳ���
		try{
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception exe){
            exe.printStackTrace();
        }
	}
	void openFileDialog(){              //���ļ��Ի���
		FileDialog dialog = new FileDialog(this,"��",0);
		dialog.setVisible(true);
		if(MusicPlayer.filepath==null);
		else
			MusicPlayer.oldPath = new String(MusicPlayer.filepath);
		MusicPlayer.filepath = dialog.getDirectory();
		File file = new File("musicDirectory.txt");
		try{
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(MusicPlayer.filepath);
			bw.flush();
			fw.close();
			bw.close();
		}catch(Exception e){}
	}
	public void addProjectMenu(final JFrame frame){
		font=new Font("����",Font.PLAIN,15);
		JMenuBar menuBar=new JMenuBar();
		ImageIcon icon_1=new ImageIcon(Main.class.getResource("/res/MenuIcon_1.PNG"));
		ImageIcon icon_2=new ImageIcon(Main.class.getResource("/res/MenuIcon_2.png"));
		ImageIcon icon_3=new ImageIcon(Main.class.getResource("/res/MenuIcon_3.png"));
		ImageIcon icon_4=new ImageIcon(Main.class.getResource("/res/MenuIcon_4.png"));
		JMenu fileMenu=new JMenu("�ļ�");
		
		final JMenuItem openFileItem=new JMenuItem("��");
		openFileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		final JMenuItem lastOpenPositionItem = new JMenuItem("�ϴ����");
		final JMenuItem exitItem=new JMenuItem("�˳�");
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
		openFileItem.setIcon(icon_1);
		exitItem.setIcon(icon_2);
		lastOpenPositionItem.setIcon(icon_3);
		exitItem.setFont(font);
		fileMenu.setFont(font);
		lastOpenPositionItem.setFont(font);
		openFileItem.setFont(font);
		fileMenu.add(openFileItem);
		fileMenu.add(lastOpenPositionItem);
		fileMenu.add(exitItem);
		fileMenu.insertSeparator(1);
		
		JMenu editMenu=new JMenu("�༭");
		
		final JMenuItem setPictureBiggerItem=new JMenuItem("�Ŵ�");
		final JMenuItem setPictureSmallerItem=new JMenuItem("��С");
		setPictureBiggerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT,InputEvent.CTRL_MASK));
		setPictureSmallerItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT,InputEvent.CTRL_MASK));
		setPictureBiggerItem.setIcon(icon_1);
		setPictureSmallerItem.setIcon(icon_2);
		editMenu.setFont(font);
		setPictureBiggerItem.setFont(font);
		setPictureSmallerItem.setFont(font);
		editMenu.add(setPictureBiggerItem);
		editMenu.add(setPictureSmallerItem);
		
		JMenu musicMenu=new JMenu("��������");
		
		final JMenuItem selectCatalogItem = new JMenuItem("ѡ��Ŀ¼");
		final JMenuItem stopMusicItem = new JMenuItem("�ر�����");
		final JMenuItem musicListItem = new JMenuItem("�����б�");
		selectCatalogItem.setIcon(icon_1);
		stopMusicItem.setIcon(icon_3);
		musicListItem.setIcon(icon_2);
		selectCatalogItem.setFont(font);
		musicListItem.setFont(font);
		musicMenu.setFont(font);
		stopMusicItem.setFont(font);
		musicMenu.add(selectCatalogItem);
		musicMenu.add(musicListItem);
		musicMenu.add(stopMusicItem);
		
		JMenu lookMenu=new JMenu("�鿴");
		
		final JMenuItem frontPictureItem=new JMenuItem("��һ��");
		final JMenuItem nextPictureItem =new JMenuItem("��һ��"); 
		final JMenuItem autoLookItem = new JMenuItem("�Զ����");
		final JMenuItem exitAutoLookItem = new JMenuItem("�˳��Զ����");
		
		frontPictureItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, InputEvent.CTRL_MASK));
		nextPictureItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN,InputEvent.CTRL_MASK));
		frontPictureItem.setIcon(icon_1);
		nextPictureItem.setIcon(icon_2);
		autoLookItem.setIcon(icon_3);
		exitAutoLookItem.setIcon(icon_4);
		
		autoLookItem.setFont(font);
		exitAutoLookItem.setFont(font);
		lookMenu.setFont(font);
		
		frontPictureItem.setFont(font);
		nextPictureItem.setFont(font);
		lookMenu.add(frontPictureItem);
		lookMenu.add(nextPictureItem);
		lookMenu.add(autoLookItem);
		lookMenu.add(exitAutoLookItem);
		lookMenu.insertSeparator(2);
		
		JMenu userCenterMenu=new JMenu("��������");
		
		loginItem=new JMenuItem("��¼");
		loginItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L,InputEvent.CTRL_MASK));
		final JMenuItem guideItem=new JMenuItem("ʹ��ָ��");
		final JMenuItem developerInformationItem=new JMenuItem("��������Ϣ");
		loginItem.setIcon(icon_1);
		guideItem.setIcon(icon_2);
		developerInformationItem.setIcon(icon_4);
		loginItem.setFont(font);
		userCenterMenu.setFont(font);
		guideItem.setFont(font);
		developerInformationItem.setFont(font);
		userCenterMenu.add(loginItem);
		userCenterMenu.add(guideItem);
		userCenterMenu.add(developerInformationItem);
		userCenterMenu.insertSeparator(1);
		
		JMenu setingMenu = new JMenu("����");

		JMenu setAppearanceMenu = new JMenu("���");
		final JMenuItem setautoLookSpeedItem = new JMenuItem("�Զ�����ٶ�");
		javaAppearanceItem = new JCheckBoxMenuItem("java���");
		//systemAppearanceItem = new JCheckBoxMenuItem("ϵͳ���");
		setAppearanceMenu.add(javaAppearanceItem);
		setAppearanceMenu.add(systemAppearanceItem);
		setingMenu.add(setAppearanceMenu);
		setingMenu.add(setautoLookSpeedItem);
		setingMenu.setFont(font);
		setautoLookSpeedItem.setIcon(icon_1);
		setautoLookSpeedItem.setFont(font);
		setAppearanceMenu.setFont(font);
		javaAppearanceItem.setFont(font);
		systemAppearanceItem.setFont(font);
		setAppearanceMenu.setIcon(icon_3);
		
		
		
		menuBar.add(fileMenu);                                           //��Ӳ˵�
		menuBar.add(editMenu);
		menuBar.add(musicMenu);
		menuBar.add(lookMenu);
		menuBar.add(userCenterMenu);
		menuBar.add(setingMenu);
		
		
		////////////////////////////////////////////////////////////////////////
		
		class ItemListener implements ActionListener{                                //�����ڲ��࣬ʵ��ActionListener�ӿ�
			public void actionPerformed(ActionEvent e){
				if(e.getSource()==exitItem){                                   //ʵ�֡��˳����˵���
					System.exit(0);
				}
				if(e.getSource()==lastOpenPositionItem){                       //ʵ�֡��ϴ�������˵���
					if(LoginDialog.isLogin==false){
						JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else{
						try{
							File lastFile = new File("position.txt");
							FileReader fr = new FileReader(lastFile);
							BufferedReader br = new BufferedReader(fr);
							String lastPath=br.readLine();
							br.close();fr.close();
							JFileChooser fileChooser=new JFileChooser();
							FileFilter filter=new FileNameExtensionFilter("ͼ���ļ�(jpg/gif...)","JPG","JEPG","GIF","PNG");
							fileChooser.setFileFilter(filter);
							fileChooser.setSelectedFile(new File(lastPath));
							int i=fileChooser.showOpenDialog(getContentPane());
							if(i==JFileChooser.APPROVE_OPTION){                      //�ж��Ƿ�Ϊ�򿪰�ť
								
								File selectedFile=fileChooser.getSelectedFile();
								currentImagePath=selectedFile.getAbsolutePath();
								currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
								currentIcon=new ImageIcon(currentImagePath);
								canvas.drawMyImage();
								File parentFile=selectedFile.getParentFile();
								file=parentFile.listFiles();
								for(int j=0;j<file.length;j++){
									if(file[i].getName().equals(selectedFile.getName()))
										nowIndex=i;
								}
								isOpen++;
								
								
							}
						}catch(Exception e1){}
					}
				}
				if(e.getSource()==openFileItem){                   //ʵ�֡��򿪡��˵���
					if(LoginDialog.isLogin==false){
						JOptionPane.showMessageDialog(null, "����û�е�¼���޷�ʹ�ô˹���!","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else{
						try{
							JFileChooser fileChooser=new JFileChooser();
							FileFilter filter=new FileNameExtensionFilter("ͼ���ļ�(jpg/gif...)","JPG","JEPG","GIF","PNG");
							fileChooser.setFileFilter(filter);
							int i=fileChooser.showOpenDialog(getContentPane());
							if(i==JFileChooser.APPROVE_OPTION){                      //�ж��Ƿ�Ϊ�򿪰�ť
								File selectedFile=fileChooser.getSelectedFile();
								currentImagePath=selectedFile.getAbsolutePath();
								currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
								currentIcon=new ImageIcon(currentImagePath);
								canvas.drawMyImage();
								File parentFile=selectedFile.getParentFile();
								MyFliter fliter = new MyFliter(".jpg",".png",".gif");
								file=parentFile.listFiles(fliter);
								fileLength=file.length;
								for(int j=0;j<file.length;j++){
									if(file[i].getName().equals(selectedFile.getName()))
										nowIndex=i;
								}
								File file1=new File("position.txt");
								File picList = new File("picList.txt");
								FileWriter fw = new FileWriter(picList);
								FileWriter writer = new FileWriter(file1);
								BufferedWriter bw = new BufferedWriter(writer);
								BufferedWriter bwriter = new BufferedWriter(fw);
								String picListStr = selectedFile.getParent();
								bw.write(currentImagePath);
								bwriter.write(picListStr);
								bwriter.flush();
								bw.flush();
								bwriter.close();
								writer.close();
								fw.close();
								isOpen++;
								
							}
						}catch(Exception e1){}
					}
				}
				

				if(e.getSource()==selectCatalogItem){                        //ʵ�֡�ѡ��Ŀ¼���˵���
					if(LoginDialog.isLogin==false){
						JOptionPane.showMessageDialog(null, "����û�е�¼,�޷�ʹ�ô˹��ܣ�","ϵͳ��ʾ",JOptionPane.ERROR_MESSAGE);
					}
					else{
						openFileDialog();
					}
					
				}
				
				if(e.getSource()==musicListItem){                 //ʵ�֡������б��˵���
					if(openMusicCount==0){
						musicPlayer = new MusicPlayer();
						openMusicCount++;
					}
					else{
						musicPlayer.setVisible(true);
						File f = new File("musicDirectory.txt");
						try{
							FileReader fr = new FileReader(f);
							BufferedReader br = new BufferedReader(fr);
							String s = br.readLine();
							MusicPlayer.filepath=s;
							if(MusicPlayer.oldPath==null||MusicPlayer.filepath.equals(MusicPlayer.oldPath));
							else{
								MusicPlayer.labelfilepath.setText("����Ŀ¼: "+MusicPlayer.filepath);
								//��ʾ�ļ��б�
								MusicPlayer.list.removeAll();
								
								File filedir = new File(MusicPlayer.filepath);
								File[] filelist = filedir.listFiles();
								for(File file:filelist){
									MusicPlayer.filename = file.getName().toLowerCase();
										if(MusicPlayer.filename.endsWith(".mp3")||MusicPlayer.filename.endsWith(".wav")){
											MusicPlayer.list.add(MusicPlayer.filename);
										}
								}
								MusicPlayer.oldPath=new String(MusicPlayer.filepath);
							}
							br.close();fr.close();
						}catch(Exception e1){}
						
					}
							
				}
				if(e.getSource()==loginItem){                              //ʵ�֡���¼���˵���
					loginDialog=new LoginDialog();
				}
				
				if(e.getSource()==stopMusicItem){                              //ʵ�֡��ر����֡��˵���
					MusicPlayer.sourceDataLine.close();
					musicPlayer.setVisible(false);
				}
				if(e.getSource()==frontPictureItem){                        //ʵ�֡���һ�š��˵���
					if(currentImagePath==null){}
					else{
						while(true){
							nowIndex--;
							if(nowIndex<0)
								nowIndex=file.length-1;
							currentImagePath=file[nowIndex].getAbsolutePath();
							if(currentImagePath.endsWith("jpg")||currentImagePath.endsWith("png")||currentImagePath.endsWith("jepg")||
									currentImagePath.endsWith("gif")){
								currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
								currentIcon=new ImageIcon(currentImagePath);
								canvas.drawMyImage();
								break;
							}
							
						}
					}
					
				}
				if(e.getSource()==nextPictureItem){                     //ʵ�֡���һ�š��˵���
					if(currentImagePath==null){}
					else{
						while(true){
							nowIndex++;
							if(nowIndex==file.length)
								nowIndex=0;
							currentImagePath=file[nowIndex].getAbsolutePath();
							if(currentImagePath.endsWith("jpg")||currentImagePath.endsWith("png")||currentImagePath.endsWith("jepg")||
									currentImagePath.endsWith("gif")){
								currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
								currentIcon=new ImageIcon(currentImagePath);
								canvas.drawMyImage();
								break;
							}
							
						}
						
					}
					
				}
				
				if(e.getSource()==autoLookItem){              //ʵ�֡��Զ�������˵���
					thread = new Thread(new Runnable(){
						public void run(){
							try{
								while(true){
									Thread.sleep(autoLookSpeed);
									nowIndex++;
									if(nowIndex==file.length)
										nowIndex=0;
									currentImagePath=file[nowIndex].getAbsolutePath();
									if(currentImagePath.endsWith("jpg")||currentImagePath.endsWith("png")||currentImagePath.endsWith("jepg")||
											currentImagePath.endsWith("gif")){
										currentImage=Toolkit.getDefaultToolkit().getImage(currentImagePath);
										currentIcon=new ImageIcon(currentImagePath);
										canvas.drawMyImage();
									}
									if(isClicked==true){
										isClicked=false;
										return ;
									}
								}
								
							}catch(Exception e){}
						}
					});
					thread.start();
				}
				if(e.getSource()==exitAutoLookItem){        //ʵ��"�˳��Զ����"�˵���
					isClicked=true;
				}

				if(e.getSource()==guideItem){                 //ʵ�֡�ʹ��ָ�ϡ��˵���
					new GuideDialog();
					
				}
				
				if(e.getSource()==setautoLookSpeedItem){          //ʵ�֡������Զ�����ٶȡ��˵���
					new SetSpeedDialog();
				}
				if(e.getSource()==developerInformationItem){        //ʵ�ֿ�������Ϣ�˵���
					new DeveloperDialog();
				}
				if(e.getSource()==javaAppearanceItem){             //ʵ�֡�java��ۡ��˵���
					setJavaAppearance();
					if(systemAppearanceItem.getState()){
						systemAppearanceItem.setSelected(false);
					}
				}
				if(e.getSource()==systemAppearanceItem){         //ʵ�֡�ϵͳ��ۡ��˵���
					setSystemAppearance();
					if(javaAppearanceItem.getState()){
						javaAppearanceItem.setState(false);
					}
				}
				if(e.getSource()==setPictureBiggerItem){         //ʵ��"�Ŵ�"�˵���
					if(currentImagePath==null){}
					else{
						x-=30;
						y-=(int)((float)(30*h)/w);
						int tempW=w;
						int tempH=h;
						w+=60;
						h+=2*(int)((float)(30*tempH)/tempW);
						canvas.repaint();
					}
				}
				if(e.getSource()==setPictureSmallerItem){    //ʵ�֡���С���˵���
					if(currentImagePath==null){}
					else{
						if(w<=40||h<=40){}
						else{
							x+=30;
							y+=(int)((float)(30*h)/w);
							int tempW=w;
							int tempH=h;
							w-=60;
							h-=2*(int)((float)(30*tempH)/tempW);
							
							canvas.repaint();
						}
						
					}
					
					
				}
			}
		}   
		/////////////////////////////////////////////////////////////////////////////////////////////Ϊ�˵�����Ӽ�����
		openFileItem.addActionListener(new ItemListener());
		exitItem.addActionListener(new ItemListener());
		selectCatalogItem.addActionListener(new ItemListener());
		loginItem.addActionListener(new ItemListener());
		stopMusicItem.addActionListener(new ItemListener());
		frontPictureItem.addActionListener(new ItemListener());
		nextPictureItem.addActionListener(new ItemListener());
		guideItem.addActionListener(new ItemListener());
		developerInformationItem.addActionListener(new ItemListener());
		setPictureBiggerItem.addActionListener(new ItemListener());
		setPictureSmallerItem.addActionListener(new ItemListener());
		lastOpenPositionItem.addActionListener(new ItemListener());
		autoLookItem.addActionListener(new ItemListener());
		exitAutoLookItem.addActionListener(new ItemListener());
		setautoLookSpeedItem.addActionListener(new ItemListener());
		musicListItem.addActionListener(new ItemListener());
		javaAppearanceItem.addActionListener(new ItemListener());
		systemAppearanceItem.addActionListener(new ItemListener());
		
		frame.setJMenuBar(menuBar);
		
		
	}
	class CanvasPanel extends JPanel{
		/**
		 * 
		 */
		private static final long serialVersionUID = 6948750429251444112L;
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			g2.drawImage(currentImage,x,y,w,h,this);
		}
		public void drawMyImage(){
			Width=currentIcon.getIconWidth();
			Height=currentIcon.getIconHeight();
			 
			if(Width<=1200&&Height<=700){
				w=Width;
				h=Height;
				x=600-w/2;
				y=300-h/2;
			}
			
			if(Width<=1200&&Height>700){
				w=(int)((float)600*Width/Height);
				h=600;
				x=600-w/2;
				y=20;
			}
			
			if(Width>1200&&Height<=700){
				w=1000;
				h=(int)((float)1200*Height/Width);
				x=100;
				y=300-h/2;
			}
			if(Width>1200&&Height>700){
				if((float)(Height*Width)>=(float)(700)/1200){
					w=(int)((float)600*Width/Height);
					h=600;
					x=600-w/2;
					y=20;
				}
				else{
					w=1200;
					h=(int)((float)1200*Height/Width);
					x=100;
					y=300-h/2;
				}
			}
			this.repaint();
		}
	}
	
}
class MyFliter implements java.io.FilenameFilter{
	private String type[] = new String[3];
	public MyFliter(String type1,String type2,String type3){
		this.type[0]=type1;
		this.type[1]=type2;
		this.type[2]=type3;
		
	}
	public boolean accept(File dir,String name){
		return name.endsWith(type[0])||name.endsWith(type[1])||name.endsWith(type[2]);
	}
}


