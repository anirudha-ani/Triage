package engine.components;

import java.io.File;
import engine.gameobjects.GameObject;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class AudioComponent extends Component {
    String source = null;
    boolean looping = false;
    String localId = "default";
    public boolean playedAfterVideo = false;

    public boolean isPlaying = false;

    MediaPlayer mediaPlayer;
    public AudioComponent(GameObject gameObject) {
        super("audio", gameObject);
    }
    public AudioComponent(String source, Boolean looping) {
        super("audio");
        this.source = source;
        this.looping = looping;
    }

    public void playAudio() {
        isPlaying = true;
        Media mediaFile = new Media(new File(this.source).toURI().toString());
        mediaPlayer = new MediaPlayer(mediaFile);
        mediaPlayer.play();
        if(this.looping) {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    mediaPlayer.seek(Duration.ZERO);
                    mediaPlayer.play();
                }
            });
        } else {
            mediaPlayer.setOnEndOfMedia(new Runnable() {
                @Override
                public void run() {
                    isPlaying = false;
                }
            });
        }
    }

    public void stopAudio() {
        isPlaying = false;
        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }

    }

    public String getLocalId() {
        return localId;
    }

    public void setLocalId(String localId) {
        this.localId = localId;
    }
}
