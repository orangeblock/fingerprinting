package sound;

import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;

public class Main {
	public static void main(String[] args){
		String filename = "audio/1.wav";
		Wave wave = new Wave(filename);
		Spectrogram spectrogram = new Spectrogram(wave);
		double[][] sData = spectrogram.getAbsoluteSpectrogramData();
		//int window = 21;
		int window = 1;
		int offset = 1;
		int currentFrame = 1;
		int[][] features = new int[spectrogram.getNumFrames()-1][256];
		
		for(int t = 1; t < spectrogram.getNumFrames()-1; t++){
			for(int k = 0; k < features.length; k++){
				if(sData[t][k] - sData[t][k+1] > sData[t-1][k] - sData[t-1][k+1]){
					features[t][k] = 1;
				}
				System.out.println(features[t][k]);
			}
		}
		//System.out.println(features[0][0]);
	}
}
