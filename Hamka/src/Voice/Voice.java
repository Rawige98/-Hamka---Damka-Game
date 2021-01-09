package Voice;

public class Voice extends Thread{
	
	private String str;
	
	public Voice(String str) {
		this.str = str;
	}
	
	
	
	public static void doSpeak(String text) {
		TextToSpeech tts = new TextToSpeech();
	
		// Setting the Voice
		tts.setVoice("cmu-rms-hsmm");

		// TTS say something that we actually are typing into the first variable
		tts.speak(text, 2.0f, false, true);

	}
	
	@Override
	public void run() {
		TextToSpeech tts = new TextToSpeech();
		
		// Setting the Voice
		tts.setVoice("cmu-rms-hsmm");

		// TTS say something that we actually are typing into the first variable
		tts.speak(str, 2.0f, false, true);
	}	
}
