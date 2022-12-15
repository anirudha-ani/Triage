package triage.gamelogics;

import triage.App;
import triage.generators.GameObjectId;

import java.util.ArrayList;

public class ClickLogics {
    App currentApp;
    public ClickLogics(App app) {
        this.currentApp = app;
    }

    /**
     * Write the logic as per clicked object id
     * @param clickedObjects contains the ID list of all clicked objects
     *
     */
    public void executeLogic( ArrayList<String> clickedObjects) {
        for (int i = 0 ; i < clickedObjects.size() ; i++) {
            if (clickedObjects.get(i).contains(GameObjectId.START_BUTTON.toString())) {
                this.currentApp.getScreenController().switchToFirstLevelScreen();
            }
        }
    }
}
