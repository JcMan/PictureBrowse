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
	public static boolean isStop = true;              //���Ʋ����߳�
	public static boolean hasStop = true;             //�����߳�״̬
	static String filepath;                    //�����ļ�Ŀ¼
	static String filename;                    //�����ļ�����
	AudioInputStream audioInputStream=null ;  //�ļ���
	AudioFormat audioFormat=null;             //�ļ���ʽ
	public static SourceDataLine sourceDataLine;            //����豸
	static List list;                             //�ļ��б�
	static Label labelfilepath;                 //����Ŀ¼��ʾ��ǩ
	static Label labelfilename;                 //�����ļ���ʾ��ǩ
	Font font = new Font("�����п�",Font.PLAIN,20);
	public static String oldPath=null;
	public MusicPlayer(){
		try {
			UIManager.setLookAndFeel(new SubstanceGreenMagicLookAndFeel());
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}                                                     //�������
		this.setResizable(false);
		this.setLayout(new BorderLayout());
		setTitle("��������");
		this.setBounds(450, 100, 370, 550);
		//�ļ��б�
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
		//��Ϣ��ʾ
		Panel panel = new Panel(new GridLayout(2,1));
		labelfilepath = new Label("����Ŀ¼");
		labelfilename = new Label("�����ļ�");
		labelfilepath.setFont(font);
		labelfilename.setFont(font);
		panel.add(labelfilepath);
		panel.add(labelfilename);
		add(panel,"North");
		//ע�ᴰ��ر��¼�
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
		}
	}
	//����
	 void play(){                                     //���Ÿ���
		try{
			isStop = true;  //ֹͣ�����߳�
			//�ȴ������߳�ֹͣ
			while(!hasStop){
				try{
					Thread.sleep(10);
				}catch(Exception e){}
			}
			File file = new File(filepath+filename);
			labelfilename.setText("�����ļ���" + filename);
			//ȡ���ļ�������
			try{
				audioInputStream = AudioSystem.getAudioInputStream(file);
				audioFormat = audioInputStream.getFormat();
				//ת��MP3�ļ�����
				if(audioFormat.getEncoding()!=AudioFormat.Encoding.PCM_SIGNED){
					audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
							audioFormat.getSampleRate(),16,audioFormat.getChannels(),audioFormat.getChannels()*2,
							audioFormat.getSampleRate(),false);
					audioInputStream = AudioSystem.getAudioInputStream(audioFormat,audioInputStream);
				}
				//������豸
				DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,audioFormat,AudioSystem.NOT_SPECIFIED);
				sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
				sourceDataLine.open(audioFormat);
				sourceDataLine.start();
			}catch(Exception e){}
			//���������߳̽��в���
			isStop = false;
			Thread playThread = new Thread(new PlayThread());
			playThread.start();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//�����ڲ���PalyThread
	 class PlayThread extends Thread{
		byte[] tempBuffer = new byte[320];
		public void run(){
			try{
				int cnt;
				hasStop = false;
				//��ȡ���ݵ���������
				try{
					while((cnt = audioInputStream.read(tempBuffer,0,tempBuffer.length))!=-1){
						if(isStop)
							break;
						if(cnt>0)
							//д�뻺������
							sourceDataLine.write(tempBuffer, 0, cnt);
					}
					//Block�ȴ���ʱ�������Ϊ��
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
