package engine.support;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

/**
 * Adapted from CS195NFrontEnd, this updated version works with JavaFX (and has only
 * one concrete implementation, {@link FXFrontEnd}).<br>
 * <br>
 * Student subclass must implement all <code>on</code>*** methods.<br>
 * Support class must implement all <code>do</code>*** methods and ensure the
 * corresponding <code>on</code>*** methods.<br>
 * 
 * 
 * @author dmayans
 *
 */

public abstract class CS1971FrontEnd {
	
	/**
	 * Default window size if subclasses doesn't specify.
	 */
	protected static final Vec2d DEFAULT_STAGE_SIZE = new Vec2d(960, 540);
	
	/**
	 * Minimum window size. Anything below this resolution won't be graded.
	 */
	protected static Vec2d MINIMUM_STAGE_SIZE = new Vec2d(960, 540);
	
	/**
	 * Current window size. Used by direct subclass to set up window based on
	 * size passed to constructor.
	 */
	protected Vec2d currentStageSize = DEFAULT_STAGE_SIZE;
	
	/**
	 * Debug mode status. Used by direct subclass to add extra useful information
	 * into the title (FPS and window size).
	 */
	protected boolean debugMode = true;
	
	/** 
	 * Fullscreen status. Used by direct subclass to set the window to be fullscreen.
	 */
	protected boolean fullscreen = false;
	
	/**
	 * Default constructor, sets attributes to be their default values.<br>
	 * <br>
	 * Window Size: (960, 540)<br>
	 * Debug Mode: true<br>
	 * Fullscreen Mode: false<br>
	 */
	public CS1971FrontEnd() {
		
	}
	
	/**
	 * Optional constructor, changes the initial values.<br>
	 * <br>
	 * @param windowSize		a Vec2d assigning the initial window size<br>
	 * @param debugMode			a boolean assigning the initial debug mode<br>
	 * @param fullscreen		a boolean assigning the initial fullscreen status<br>
	 * <br>
	 * Note: this initial size can be smaller than the minimum window size. If
	 * it is, the new minimum window size will be reduced to match the size passed
	 * in to it. Student projects never need to be smaller than the default
	 * minimum window size of (960, 540).
	 */
	public CS1971FrontEnd(Vec2d windowSize, boolean debugMode, boolean fullscreen) {
		this.debugMode = debugMode;
		this.fullscreen = fullscreen;
		if(windowSize != null) {
			currentStageSize = windowSize;
			if(windowSize.x < MINIMUM_STAGE_SIZE.x)
				MINIMUM_STAGE_SIZE = new Vec2d(windowSize.x, MINIMUM_STAGE_SIZE.y);
			if(windowSize.y < MINIMUM_STAGE_SIZE.y)
				MINIMUM_STAGE_SIZE = new Vec2d(MINIMUM_STAGE_SIZE.x, windowSize.y);
		}
	}
	
	
	/* 	____ _____ _   _ ____  _____ _   _ _____ 
	 * / ___|_   _| | | |  _ \| ____| \ | |_   _|
	 * \___ \ | | | | | | | | |  _| |  \| | | |  
	 *  ___) || | | |_| | |_| | |___| |\  | | |  
	 * |____/ |_|  \___/|____/|_____|_| \_| |_|  
	 * 
	 *  __  __ _____ _____ _   _  ___  ____  ____  
	 * |  \/  | ____|_   _| | | |/ _ \|  _ \/ ___| 
	 * | |\/| |  _|   | | | |_| | | | | | | \___ \ 
	 * | |  | | |___  | | |  _  | |_| | |_| |___) |
	 * |_|  |_|_____| |_| |_| |_|\___/|____/|____/ 
	 */
	
	/**
	 * Called at a regular interval set by {@link #setTickFrequency(long)}. Use to update any state
	 * that changes over time.
	 * 
	 * @param nanosSincePreviousTick	approximate number of nanoseconds since the previous call
	 *                              	to onTick
	 */
	protected abstract void onTick(long nanosSincePreviousTick);

	protected abstract void onLateTick();
	
	/**
	 * Called when the screen needs to be redrawn. This is at least once per tick, but possibly
	 * more frequently (for example, when the window is resizing).
	 * <br><br>
	 * Note that the entire drawing area is cleared before each call to this method. Furthermore,
	 * {@link #onResize} is guaranteed to be called before the first invocation of onDraw.
	 * 
	 * @param g		a {@link GraphicsContext} object used for drawing.
	 */
	protected abstract void onDraw(GraphicsContext g);
	
	/**
	 * @param e		an FX {@link KeyEvent} representing the input event.
	 */
	protected abstract void onKeyTyped(KeyEvent e);
	
	/**
	 * @param e		an FX {@link KeyEvent} representing the input event.
	 */
	protected abstract void onKeyPressed(KeyEvent e);
	
	/**
	 * @param e		an FX {@link KeyEvent} representing the input event.
	 */
	protected abstract void onKeyReleased(KeyEvent e);
	
	/**
	 * @param e		an FX {@link MouseEvent} representing the input event.
	 */
	protected abstract void onMouseClicked(MouseEvent e);
	
	/**
	 * @param e		an FX {@link MouseEvent} representing the input event.
	 */
	protected abstract void onMousePressed(MouseEvent e);
	
	/**
	 * @param e		an FX {@link MouseEvent} representing the input event.
	 */
	protected abstract void onMouseReleased(MouseEvent e);
	
	/**
	 * @param e		an FX {@link MouseEvent} representing the input event.
	 */
	protected abstract void onMouseDragged(MouseEvent e);
	
	/**
	 * @param e		an FX {@link MouseEvent} representing the input event.
	 */
	protected abstract void onMouseMoved(MouseEvent e);
	
	/**
	 * @param e		an FX {@link ScrollEvent} representing the input event.
	 */
	protected abstract void onMouseWheelMoved(ScrollEvent e);
	
	/**
	 * @param newVal	a boolean representing the new focus state
	 */
	protected abstract void onFocusChanged(boolean newVal);
	
	/**
	 * Called when the size of the drawing area changes. Any subsequent calls to onDraw should note
	 * the new size and be sure to fill the entire area appropriately. Guaranteed to be called
	 * before the first call to onDraw.
	 * 
	 * @param newSize	the new size of the drawing area.
	 */
	protected abstract void onResize(Vec2d newSize);
	
	/**
	 * Called before the window closes by user input. Not guaranteed to be called if the program
	 * terminates due to an exception or a System.exit(...) / Platform.close call.
	 */
	protected abstract void onShutdown();
	
	/**
	 * Called after the window is initialized by engine.support code, but before it appears. Guaranteed
	 * to be called before all events, ticks, resizes, and draw calls.
	 */
	protected abstract void onStartup();
	
	 /*  ____  _   _ ____  _     ___ ____ 
	  * |  _ \| | | | __ )| |   |_ _/ ___|
	  * | |_) | | | |  _ \| |    | | |    
	  * |  __/| |_| | |_) | |___ | | |___ 
	  * |_|    \___/|____/|_____|___\____|
	  * 
	  *  __  __ _____ _____ _   _  ___  ____  ____  
	  * |  \/  | ____|_   _| | | |/ _ \|  _ \/ ___| 
	  * | |\/| |  _|   | | | |_| | | | | | | \___ \ 
	  * | |  | | |___  | | |  _  | |_| | |_| |___) |
	  * |_|  |_|_____| |_| |_| |_|\___/|____/|____/ 
	  */
	
	
	/**
	 * Enable or disable debug mode. When debug mode is enabled, the current size of the draw area
	 * and aspect ratio is displayed in the title of the window along with the current FPS count,
	 * and pressing F12 will bring up a dialog to resize the window.
	 * @param debug		true to enable debug mode, false to disable
	 */
	public final void setDebugMode(boolean debug) {
		if (debug != debugMode) {
			debugMode = debug;
			doSetDebugMode();
		}
	}
	
	/**
	 * Gets whether debug mode is currently set.
	 * @return	true if the debug mode is currently on, false if not 
	 */
	public final boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * Controls the frequency of {@link #onTick(long) onTick()} calls. Ticks will occur approximately
	 * once every <code>nanoDelay</code> nanoseconds, but <b>no specific accuracy guarantees can be
	 * made</b>. Always use the argument of onTick to determine the actual amount of time passed.
	 * 
	 * @param nanoDelay		the number of nanoseconds between the start of each ticks. For example,
	 *                 		for 50 FPS, this value would be 1000000000/50 (20000000). Must be > 0.
	 */
	public final void setTickFrequency(long nanoDelay) {
		if (nanoDelay <= 0) {
			throw new IllegalArgumentException("nanoDelay must be greater than 0");
		}
		
		this.doSetTickFrequency(nanoDelay);
	}
	
	/**
	 * Controls the frequency of {@link #onTick(long) onTick()} calls. Ticks will occur approximately 
	 * <code>fps</code> times each second, but <b>no specific accuracy guarantees can be made.</b>
	 * Always use the argument of onTick to determine the actual amount of time passed.
	 * 
	 * @param fps			the target FPS, must be > 0;
	 * 
	 */
	public final void setTargetFPS(int fps) {
		if(fps <= 0) {
			throw new IllegalArgumentException("fps must be greater than 0");
		}
		
		this.doSetTargetFPS(fps);
	}
	
	/**
	 * Opens up the application for events, no guarantee that this function ever returns
	 */
	public abstract void start();
	
	/**
	 * Calls onShutdown, then terminates the program.
	 */
	public abstract void shutdown();
	
	 /*  ____  _   _ ____  ____   ___  ____ _____ 
	  * / ___|| | | |  _ \|  _ \ / _ \|  _ \_   _|
	  * \___ \| | | | |_) | |_) | | | | |_) || |  
	  *  ___) | |_| |  __/|  __/| |_| |  _ < | |  
	  * |____/ \___/|_|   |_|    \___/|_| \_\|_|  
	  *                                         
	  *  __  __ _____ _____ _   _  ___  ____  ____  
	  * |  \/  | ____|_   _| | | |/ _ \|  _ \/ ___| 
	  * | |\/| |  _|   | | | |_| | | | | | | \___ \ 
	  * | |  | | |___  | | |  _  | |_| | |_| |___) |
	  * |_|  |_|_____| |_| |_| |_|\___/|____/|____/ 
	  */
	
	/**
	 * Actually set the tick frequency. <code>nanoDelay</code> is greater than 0.
	 */
	protected abstract void doSetTickFrequency(long nanoDelay);
	
	/**
	 * Actually set the tick frequency. <code>fps</code> is greater than 0
	 */
	protected abstract void doSetTargetFPS(int fps);
	
	/**
	 * Actually run code to enable or disable debug mode. The <code>debug</code> field will hold the
	 * value of the desired new state.
	 */
	protected abstract void doSetDebugMode();
	
	/**
	 * Actually run code to go fullscreen or windowed. The <code>fullscreen</code> field will hold
	 * the value of the desired new state. When returning from fullscreen back to windowed, it's
	 * nice if the subclass remembers the old window size and position, but this behavior is not
	 * strictly required.
	 */
	protected abstract void doSetFullscreen();
		
}
