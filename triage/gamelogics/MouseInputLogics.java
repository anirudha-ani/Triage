package triage.gamelogics;

import engine.components.AudioComponent;
import engine.components.RayComponent;
import engine.gameobjects.GameObject;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import triage.App;
import triage.GameState;
import triage.generators.BulletGenerator;
import triage.generators.GameObjectId;

import java.util.ArrayList;

public class MouseInputLogics {
    App currentApp;
    public MouseInputLogics(App app) {
        this.currentApp = app;
    }

    /**
     * Write the logic as per clicked object id
     * @param clickedObjects contains the ID list of all clicked objects
     *
     */
    public void executeClickLogic( ArrayList<String> clickedObjects) {
        for (int i = 0 ; i < clickedObjects.size() ; i++) {
            if (clickedObjects.get(i).contains(GameObjectId.START_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToFirstLevelScreen();
            }
        }
    }

    public void executeClickReleaseLogic(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            GameObject player = currentApp.getGameState().getGameWorld().getGameObject("player");

            if (player != null) {
                RayComponent rayComponent = (RayComponent) player.getComponent("ray");

                if (rayComponent != null) {
                    rayComponent.setShow(false);
                    BulletGenerator bulletGenerator = new BulletGenerator(this.currentApp.getGameState());

                    GameObject bullet = bulletGenerator.generate(rayComponent);
                    currentApp.getGameState().getGameWorld().addGameObject(bullet);
                    AudioComponent audioClip = new AudioComponent("triage/audiofiles/heat-vision.mp3", false);
                    audioClip.playAudio();
                }
            }
        }

    }
}
