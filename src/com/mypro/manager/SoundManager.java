package com.mypro.manager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * 锟斤拷效锟斤拷锟斤拷锟斤拷
 * @author dell
 *
 */
public class SoundManager {
	/**
	 * 锟斤拷效锟斤拷锟斤拷锟斤拷
	 */
	public static final int SOUND_BGM_FIRE = 1;							//锟斤拷锟斤拷锟斤拷效
	public static final int SOUND_BGM_NET = SOUND_BGM_FIRE + 1;			//锟斤拷锟斤拷锟斤拷效
	public static final int SOUND_BGM_CHANGE_CANNON = SOUND_BGM_NET+1;	//锟斤拷锟斤拷锟�
	public static final int SOUND_BGM_GOLD = SOUND_BGM_CHANGE_CANNON+1;	//锟斤拷媒锟斤拷
	public static final int SOUND_BGM_HIGH_POINT = SOUND_BGM_GOLD+1;	//锟斤拷酶叻锟�
	public static final int SOUND_BGM_HUNDRED_POINT = SOUND_BGM_HIGH_POINT+1;	//锟斤拷冒俜锟�
	public static final int SOUND_BGM_NO_GOLD = SOUND_BGM_HUNDRED_POINT+1;	//没锟叫斤拷锟�
	
	private static SoundManager soundManager;
	private static HashMap<Integer, byte[]> soundMap;
	private static HashMap<Integer, SourceDataLine> lineMap;
	private Mixer mixer;
	private static ExecutorService threadPool = Executors.newCachedThreadPool();

	
//	private  SoundManager(){
//		try{
//			
//	    	soundMap = new HashMap<Integer, byte[]>();
//	    	lineMap = new HashMap<Integer, SourceDataLine>();
////	    	mixer = AudioSystem.getMixer(AudioSystem.getMixerInfo()[0]);
//	    	initSoundData(SOUND_BGM_FIRE,"bgm_fire.ogg");
//	    	initSoundData(SOUND_BGM_CHANGE_CANNON,"firechange.ogg");
//	    	initSoundData(SOUND_BGM_NET,"bgm_net.ogg");
//	    	initSoundData(SOUND_BGM_GOLD,"coinanimate.ogg");
//	    	initSoundData(SOUND_BGM_HIGH_POINT,"highpoints.ogg");
//	    	initSoundData(SOUND_BGM_HUNDRED_POINT,"hundredpoints.mp3");
//	    	initSoundData(SOUND_BGM_NO_GOLD,"coinsnone.ogg");
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
	
	private void initSoundData(int key,String soundFile){
		try {		
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("bgm"+File.separator+soundFile));
			AudioFormat audioFormat = audioInputStream.getFormat();
			// 锟斤拷锟斤拷锟斤拷璞�
			if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {

				audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,

				audioFormat.getSampleRate(), 16, audioFormat

				.getChannels(), audioFormat.getChannels() * 2,

				audioFormat.getSampleRate(), false);

				audioInputStream = AudioSystem.getAudioInputStream(audioFormat,

				audioInputStream);

			}
			DataLine.Info dataLineInfo = new DataLine.Info(

			SourceDataLine.class, audioFormat);

			SourceDataLine sourceDataLine = (SourceDataLine) mixer.getLine(dataLineInfo);

//			SourceDataLine sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

			sourceDataLine.open(audioFormat);

			sourceDataLine.start();
			byte[] buffer = new byte[1024*1024];			
			int len = audioInputStream.read(buffer);
			byte[] data = new byte[len];
			System.arraycopy(buffer, 0, data, 0, len);
			soundMap.put(key, data);
			lineMap.put(key, sourceDataLine);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static SoundManager getSoundManager(){
		if(soundManager==null){
			soundManager = new SoundManager();
		}		
		return soundManager;
	}
	
	/**
	 * 锟斤拷锟斤拷锟斤拷效
	 * @param soundID	锟斤拷应锟斤拷前锟斤拷某锟�
	 */
	public static void playSound(final int soundID){
//		threadPool.execute(new Runnable(){
//
//			@Override
//			public void run() {
//				// 锟斤拷取锟斤拷莸锟斤拷锟斤拷锟斤拷锟斤拷
//				SourceDataLine line = lineMap.get(soundID);
//				
//				line.write(soundMap.get(soundID),0, soundMap.get(soundID).length);
//			}			
//		});
	}
	/**
	 * 锟酵凤拷锟斤拷源
	 */
	public static void release(){
		soundMap.clear();
		for(SourceDataLine line:lineMap.values()){
			line.drain();
			line.close();
		}
	
		soundManager = null;
	}
}
