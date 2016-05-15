import java.awt.BorderLayout;

import org.jvnet.substance.skin.SubstanceGreenMagicLookAndFeel;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
public class MusicPlayer extends Frame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static boolean isStop = true;              //控制播放线程
	public static boolean hasStop = true;             //播放线程状态
	static String filepath;                    //播放文件目录
	static String filename;                    //播放文件名称
	AudioInputStream audioInputStream=null ;  //文件流
	AudioFormat audioFormat=null;             //文件格式
	public static SourceDataLine sourceDataLine;            //输出设备
	static List list;                             //文件列表
	static Label labelfilepath;                 //播放目录显示标签
	static Label labelfilename;                 //播放文件显示标签
	Font font = new Font("华文行楷",Font.PLAIN,20);
	public static String oldPath=null;
	public MusicPlayer(){
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}                                                     //设置外观
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		setTitle("背景音乐");
		this.setBounds(450, 100, 370, 550);
		//文件列表
		list = new List(10);
		list.setFont(font);
		list.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				if(e.getClickCount() == 2){
					filename = list.getSelectedItem();
					setVisible(false);
					play();
				}
			}
		});
		this.add(list,"Center");
		//信息显示
		Panel panel = new Panel(new GridLayout(2,1));
		labelfilepath = new Label("播放目录");
		labelfilename = new Label("播放文件");
		labelfilepath.setFont(font);
		labelfilename.setFont(font);
		panel.add(labelfilepath);
		panel.add(labelfilename);
		add(panel,"North");
		//注册窗体关闭事件
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				//System.exit(0);
				setVisible(false);
				//Main.openMusicCount=0;
			}
		});
		setVisible(true);
		
		///////////////////////
		File f = new File("musicDirectory.txt");
		try{
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String s = br.readLine();
			MusicPlayer.filepath=s;
		}catch(Exception e){}
		
		if(MusicPlayer.filepath!=null){
			MusicPlayer.labelfilepath.setText("播放目录: "+MusicPlayer.filepath);
			//显示文件列表
			MusicPlayer.list.removeAll();
			
			File filedir = new File(MusicPlayer.filepath);
			File[] filelist = filedir.listFiles();
			for(File file:filelist){
				MusicPlayer.filename = file.getName().toLowerCase();
					if(MusicPlayer.filename.endsWith(".mp3")||MusicPlayer.filename.endsWith(".wav")){
						MusicPlayer.list.add(MusicPlayer.filename);
					}
			}
		}
	}
	//播放
	 void play(){                                     //播放歌曲
		try{
			isStop = true;  //停止播放线程
			//等待播放线程停止
			while(!hasStop){
				try{
					Thread.sleep(10);
				}catch(Exception e){}
			}
			File file = new File(filepath+filename);
			labelfilename.setText("播放文件：" + filename);
			//取得文件输入流
			try{
				audioInputStream = AudioSystem.getAudioInputStream(file);
				audioFormat = audioInputStream.getFormat();
				//转换MP3文件编码
				if(audioFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED){
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							audioFormat.getSampleRate(),16,audioFormat.getChannels(),audioFormat.getChannels()*2,
							audioFormat.getSampleRate(),false);
					audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
				}
				//打开输出设备
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat,AudioSystem.NOT_SPECIFIED);
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
			}catch(Exception e){}
			//创建独立线程进行播放
			isStop = false;
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//定义内部类PalyThread
	 class PlayThread extends Thread{
		byte[] tempBuffer = new byte[320];
		public void run(){
			try{
				int cnt;
				hasStop = false;
				//读取数据到缓存数据
				try{
					while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length))!=-1){
						if(isStop)
							break;
						if(cnt>0)
							//写入缓存数据
							sourceDataLine.write(tempBuffer, 0, cnt);
					}
					//Block等待临时数据输出为空
					sourceDataLine.drain();
					sourceDataLine.close();
				}catch(Exception e){}
				hasStop = true;
			}catch(Exception e){
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
}
