package View;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
/***
 * This class handle the playing of music sounds during the game.
 */
public class Sound {
	/***/
	private static Sound instance = null;
	
	/***/
	private Map<URL, MediaPlayer> soundMap;

	private Sound() {
	}

	public static Sound getInstance() {

		if (instance == null) {
			instance = new Sound();
			instance.soundMap = new HashMap<>();
		}

		return instance;
	}

	public void play(String res) {
		final URL resource = getResource(res);
		if (!soundMap.containsKey(resource)) {
			final Media media = new Media(resource.toString());
			final MediaPlayer mediaPlayer = new MediaPlayer(media);
			soundMap.put(resource, mediaPlayer);
		}

		soundMap.get(resource).seek(Duration.ZERO);
		soundMap.get(resource).play();
	}

	public void swap(String res) {
		final URL resource = getResource(res);
		if (soundMap.containsKey(resource)) {
			MediaPlayer currentPlayer = soundMap.get(resource);

			if (currentPlayer.getStatus().equals(MediaPlayer.Status.PAUSED) || 
					currentPlayer.getStatus().equals(MediaPlayer.Status.STOPPED)) {
				// Currently paused, play it
				currentPlayer.seek(Duration.ZERO);
				currentPlayer.play();
			} else {
				currentPlayer.pause();
			}

		} else{
			play(res);
		}
	}

	public void changeVolume(String res, Double vol) {
		final URL resource = getResource(res);
		if (soundMap.containsKey(resource)) {
			MediaPlayer currentPlayer = soundMap.get(resource);
			currentPlayer.setVolume(vol / 100);

		}
	}

	private URL getResource(String res) {
		
		final URL resource = getClass().getResource("/Sounds/"+res);
		return resource;
	}
	
	
	public void stop(String key) {
		final URL resource = getResource(key);
		if (soundMap.containsKey(resource)) {
			soundMap.get(resource).stop();
		}
	}
}
