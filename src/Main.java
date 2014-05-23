import com.musicg.wave.Wave;
import com.musicg.wave.extension.Spectrogram;

import java.util.Arrays;

public class Main {
	public static void main(String[] args) throws IllegalArgumentException{
		String original = "audio/nara.wav";
        String recording = "audio/nara_rec.wav";

		Spectrogram originalSpec = new Spectrogram(new Wave(original));
        Spectrogram recordingSpec = new Spectrogram(new Wave(recording));

		int[][] originalFeatures = extractFeatures(originalSpec);
        int[][] recordingFeatures = extractFeatures(recordingSpec);


		//int window = 21;
		int window = 1;
		int offset = 1;
		int currentFrame = 1;

        //System.out.println(calculateSimilarity(originalFeatures, recordingFeatures));
        int[] sim = calculateSimilarity(originalFeatures, recordingFeatures);
        Arrays.sort(sim);
        System.out.println(Arrays.toString(sim));
	}

    public static int[] calculateSimilarity(int[][] feat1, int[][] feat2){
        int[][] sample = feat1.length > feat2.length ? feat2 : feat1;
        int[][] original = feat1.length > feat2.length ? feat1 : feat2;

        int[] similarities = new int[original.length - sample.length + 1];
        for(int i = 0; i < similarities.length ; i++){
            similarities[i] = serialSimilarity(sample, Arrays.copyOfRange(original, i, i+sample.length));
        }
        return similarities;
    }

    public static int serialSimilarity(int[][] ser1, int[][] ser2) throws IllegalArgumentException{
        if(ser1.length != ser2.length){
            throw new IllegalArgumentException("Input vectors must be of same length");
        }

        int serialDist = 0;
        for(int i = 0; i < ser1.length; i++){
            serialDist += hanningDist(ser1[i], ser2[i]);
        }
        //serialDist /= 256*ser1.length;

        return serialDist;
    }

    public static int hanningDist(int[] vec1, int[] vec2) throws IllegalArgumentException{
        if(vec1.length != vec2.length){
            throw new IllegalArgumentException("Input vectors must be of same length");
        }

        int hanning = 0;
        for(int i = 0; i < vec1.length; i++){
            hanning += Math.abs(vec1[i] - vec2[i]);
        }

        return hanning;
    }

    public static int[][] extractFeatures(Spectrogram spec){
        int[][] features = new int[spec.getNumFrames()-1][256];
        double[][] data = spec.getAbsoluteSpectrogramData();

        for(int t = 1; t < features.length; t++){
            for(int k = 0; k < 255; k++){
                if(data[t][k] - data[t][k+1] > data[t-1][k] - data[t-1][k+1]){
                    features[t][k] = 1;
                }
            }
        }

        return features;
    }

    /**
     * Pretty prints the given array.
     * @param data array to be printed
     */
    public static String prettyString(int[][] data){
        String prettyStr = "";
        for(int t = 0; t < data.length; t++){
            prettyStr += "t=" + t + " | " + Arrays.toString(data[t]) + "\n";
        }
        return prettyStr;
    }

}
