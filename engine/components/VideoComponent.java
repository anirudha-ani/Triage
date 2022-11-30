package engine.components;

import engine.gameobjects.GameObject;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

public class VideoComponent extends Component {
    String source = null;
    boolean looping = false;
    public VideoComponent(String source, Boolean looping, GameObject gameObject) {
        super("video", gameObject);
    }
    public VideoComponent(String source, Boolean looping) {
        super("video");
        this.source = source;
        this.looping = looping;
    }

    public void playVideo(Scene scene, Stage stage) {

        Media mediaFile = new Media(new File(this.source).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(mediaFile);
//        MediaView view = new MediaView(mediaPlayer);
        mediaPlayer.play();

        MediaView mediaView = new MediaView (mediaPlayer);
        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene newscene = new Scene(root,500,300);
        stage.setScene(newscene);
        stage.setTitle("Play Video");
        stage.show();
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
                    stage.setScene(scene);
                }
            });
        }
    }
}
