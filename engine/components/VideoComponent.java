package engine.components;

import engine.gameobjects.GameObject;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;
import triage.GameState;

import java.io.File;

public class VideoComponent extends Component {
    String source = null;
    boolean looping = false;
    MediaView mediaView;
    MediaPlayer mediaPlayer = null;
    boolean isPlaying = false;
    Scene scene = null;
    Stage stage = null;
    public VideoComponent(String source, Boolean looping, GameObject gameObject) {
        super("video", gameObject);
    }
    public VideoComponent(String source, Boolean looping) {
        super("video");
        this.source = source;
        this.looping = looping;
    }

    public VideoComponent() {
        super("video");
    }

    /**
     * Why this scene and stage
     * Because the video plays on a different scene
     * So what I did
     * @param scene
     * @param stage
     */

    public void playVideo(GameState currentState, Scene scene, Stage stage, double width , double height) {
        this.scene = scene;
        this.stage = stage;

        Media mediaFile = new Media(new File(this.source).toURI().toString());
        mediaPlayer = new MediaPlayer(mediaFile);
        mediaView = new MediaView (mediaPlayer);

        // Sets the media to the current screen width and height
        mediaView.setFitWidth(width);
        mediaView.setFitHeight(height);

        Group root = new Group();
        root.getChildren().add(mediaView);
        Scene newscene = new Scene(root,width,height);

        /**
         * This listener detects any key press and stops the video
         */
        newscene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                stopVideo(currentState);
            }
        });

        /**
         * This listener change the media view height and width based on the windows size change
         */
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitWidth(newVal.doubleValue());
        });

        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            mediaView.setFitHeight(newVal.doubleValue());
        });

        stage.setScene(newscene);
        stage.show();

        isPlaying = true;
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
                    stage.setScene(scene);
                    currentState.isVideoPlaying = false;
                }
            });
        }
    }

    public void stopVideo(GameState currentState) {
        System.out.println("Stop video");
        if(!isPlaying) {
            return;
        }
        if(mediaPlayer != null) {
            mediaPlayer.stop();
            currentState.isVideoPlaying = false;
        }
        if(stage != null && scene != null) {
            stage.setScene(scene);
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isLooping() {
        return looping;
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }
}
