package engine;

import engine.support.FXFrontEnd;
import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * This is your main Application class that you will contain your
 * 'draws' and 'ticks'. This class is also used for controlling
 * user input
 */
public class Application extends FXFrontEnd {

    private Screen currentScreen = new Screen();
    private String currentScreenId;
    private Vec2d currentScreenSize = DEFAULT_STAGE_SIZE;

    public Application(String title) {
        super(title);
    }

    public Application(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
        super(title, windowSize, debugMode, fullscreen);
    }

    public void removeScreen(String screenId) {
    }

    public void flushAllScreens() {

    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public String getCurrentScreenId() {
        return currentScreenId;
    }

    public void setCurrentScreenId(String currentScreenId) {
        this.currentScreenId = currentScreenId;
    }

    /**
     * Called periodically and used to update the state of your game.
     *
     * @param nanosSincePreviousTick approximate number of nanoseconds since the previous call
     */
    @Override
    protected void onTick(long nanosSincePreviousTick) {
//    System.out.println("Ontick called");
    }

    /**
     * Called after onTick().
     */
    @Override
    protected void onLateTick() {
        // Don't worry about this method until you need it. (It'll be covered in class.)
    }

    /**
     * Called periodically and meant to draw graphical components.
     *
     * @param g a {@link GraphicsContext} object used for drawing.
     */
    @Override
    protected void onDraw(GraphicsContext g) {
//    g.setStroke(Color.BLUE);
//    g.setFill(Color.RED);
//
//    g.fillOval(0, 0, 200, 200);
        this.currentScreen.draw(g);

    }

    /**
     * Called when a key is typed.
     *
     * @param e an FX {@link KeyEvent} representing the input event.
     */
    @Override
    protected void onKeyTyped(KeyEvent e) {
//    System.out.println("onKeyTyped called");
    }

    /**
     * Called when a key is pressed.
     *
     * @param e an FX {@link KeyEvent} representing the input event.
     */
    @Override
    protected void onKeyPressed(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
        currentScreen.onKeyPressed(e);
//    System.out.println("onKeyPressed called");
    }

    /**
     * Called when a key is released.
     *
     * @param e an FX {@link KeyEvent} representing the input event.
     */
    @Override
    protected void onKeyReleased(KeyEvent e) {
        if (e.getCode() == KeyCode.ESCAPE) {
            System.exit(0);
        }
        currentScreen.onKeyReleased(e);
//    System.out.println("onKeyReleased called");
    }

    /**
     * Called when the mouse is clicked.
     *
     * @param e an FX {@link MouseEvent} representing the input event.
     */
    @Override
    protected void onMouseClicked(MouseEvent e) {
//        System.out.println("onMouseClicked called");
        currentScreen.onMouseClicked(e);
    }

    /**
     * Called when the mouse is pressed.
     *
     * @param e an FX {@link MouseEvent} representing the input event.
     */
    @Override
    protected void onMousePressed(MouseEvent e) {
//    System.out.println("onMousePressed called");
    }

    /**
     * Called when the mouse is released.
     *
     * @param e an FX {@link MouseEvent} representing the input event.
     */
    @Override
    protected void onMouseReleased(MouseEvent e) {
        currentScreen.onMouseReleased(e);
//    System.out.println("onMouseReleased called");
    }

    /**
     * Called when the mouse is dragged.
     *
     * @param e an FX {@link MouseEvent} representing the input event.
     */
    @Override
    protected void onMouseDragged(MouseEvent e) {
        currentScreen.onMouseDragged(e);
    }

    /**
     * Called when the mouse is moved.
     *
     * @param e an FX {@link MouseEvent} representing the input event.
     */
    @Override
    protected void onMouseMoved(MouseEvent e) {
        currentScreen.onMouseMoved(e);
    }

    /**
     * Called when the mouse wheel is moved.
     *
     * @param e an FX {@link ScrollEvent} representing the input event.
     */
    @Override
    protected void onMouseWheelMoved(ScrollEvent e) {
        currentScreen.onMouseWheelMoved(e);
    }

    /**
     * Called when the window's focus is changed.
     *
     * @param newVal a boolean representing the new focus state
     */
    @Override
    protected void onFocusChanged(boolean newVal) {
//    System.out.println("onFocusChanged called");
    }

    /**
     * Called when the window is resized.
     *
     * @param newSize the new size of the drawing area.
     */
    @Override
    protected void onResize(Vec2d newSize) {
        currentScreenSize = newSize;
        currentScreen.onResize(newSize);
//    System.out.println("onResize called");
    }

    /**
     * Called when the app is shutdown.
     */
    @Override
    protected void onShutdown() {
//    System.out.println("onShutdown called");
    }

    /**
     * Called when the app is starting up.s
     */
    @Override
    protected void onStartup() {
//    System.out.println("onStartup called");
    }

    public Vec2d getCurrentScreenSize() {
        return currentScreenSize;
    }
}
