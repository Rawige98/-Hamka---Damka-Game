package Voice;

import Utils.Consts;

public class Voice extends Thread {

	private String str;

	public Voice(String str) {
		this.str = str;
	}

	@Override
	public void run() {
		TextToSpeech tts = new TextToSpeech();

		tts.setVoice(Consts.Voice);

		// TTS say something that we actually are typing into the first variable
		tts.speak(str, 2.0f, false, true);
	}
}
