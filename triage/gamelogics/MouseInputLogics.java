package triage.gamelogics;

import engine.components.AudioComponent;
import engine.components.RayComponent;
import engine.gameobjects.GameObject;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import triage.App;
import triage.blueprints.AudioId;
import triage.generators.BulletGenerator;
import triage.generators.ObjectIds.GameObjectId;
import triage.savefiles.SaveFileTags;

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
            if (clickedObjects.get(i).contains(GameObjectId.SHOPPING_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToCartScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.PAUSE_BUTTON.toString())) {
                this.currentApp.getScreenController().resetScreen();
                this.currentApp.getScreenController().switchToFirstLevelScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.EXIT_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToMenuScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.UNLOCK_BUTTON.toString())) {
                currentApp.getGameState().getSaveFile().modifyElements(SaveFileTags.SHURIKEN.toString(), Integer.toString(1));
                currentApp.getGameState().getSaveFile().modifyElements(SaveFileTags.COINS.toString(), Integer.toString(currentApp.getGameState().getCoinCount()-300));
                this.currentApp.getGameState().setCoinCount(currentApp.getGameState().getCoinCount()-300);
                this.currentApp.getGameState().setBoughtItem(true);
                this.currentApp.getScreenController().switchToCartScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.USEITEM_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToMenuScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.LOAD_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToLoadScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.LEVEL1_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToFirstLevelScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.LEVEL2_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToSecondLevelScreen();
            }
            if (clickedObjects.get(i).contains(GameObjectId.SETTINGS_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToInstructionScreen();
            }
        }
    }

    public void executeClickReleaseLogic(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) {
            GameObject player = currentApp.getGameState().getGameWorld().getGameObject("player");

            if (player != null && currentApp.getGameState().isBoughtItem()) {
                RayComponent rayComponent = (RayComponent) player.getComponent("ray");

                if (rayComponent != null) {
                    rayComponent.setShow(false);
                    BulletGenerator bulletGenerator = new BulletGenerator(this.currentApp.getGameState());

                    GameObject bullet = bulletGenerator.generate(rayComponent);
                    currentApp.getGameState().getGameWorld().addGameObject(bullet);

                    /**
                     * The reason I am not registering it to the GameState is because
                     * it is a small clip which plays immediately.
                     */
                    AudioComponent audioClip = new AudioComponent("triage/audiofiles/heat-vision.mp3", false);
                    audioClip.setLocalId(AudioId.BULLET.toString());
                    audioClip.playAudio();
                }
            }
        }

    }
}
