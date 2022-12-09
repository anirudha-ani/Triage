package triage;

import engine.support.FXApplication;
import engine.support.FXFrontEnd;

/**
 * Here is your main class. You should not have to edit this
 * class at all unless you want to change your window size
 * or turn off the debugging information.
 */
public class Main {

    public static void main(String[] args) {
        FXFrontEnd app = new App("Terror In CIT");
        FXApplication application = new FXApplication();
        FXApplication.begin(app);
    }
}
