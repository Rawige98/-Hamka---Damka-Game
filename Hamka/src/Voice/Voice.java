package Voice;

public class Voice {

	public static void voicesExamples(String text) {
		TextToSpeech tts = new TextToSpeech();

	

		// Setting the Voice
		tts.setVoice("dfki-poppy-hsmm");

		// TTS say something that we actually are typing into the first variable
		tts.speak(text, 2.0f, false, true);

	}
}
