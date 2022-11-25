package engine.support;// package

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


public abstract class FXFrontEnd extends CS1971FrontEnd {
	/**
	 * Amount of time between ticks in milliseconds.
	 */
	private static final int DEFAULT_FPS = 60;
	private static final int DEFAULT_TICK_DELAY = 1000 / DEFAULT_FPS;
	
	/**
	 * The window used to hold the canvas
	 */
	private Stage stage = null;
	private Scene scene = null;
	private String title = "";
	private String debugTitle = "";
	
	/**
	 * The canvas used to draw the application
	 */
	private CS1971Canvas canvas = null;
	
	/**
	 * Timer utilities for calling tick
	 */
	private Timeline timer;
	private int tickDelay = DEFAULT_TICK_DELAY;
	private long timeOfLastTick = 0;
	private int ticksSinceUpdate = 0;
	private int targetFPS = DEFAULT_FPS;
	private long[] fpsTracker = new long[targetFPS];
	private boolean running = false;
	
	public FXFrontEnd(String title) {
		super();
		this.title = title;
	}
	
	public FXFrontEnd(String title, Vec2d windowSize, boolean debugMode, boolean fullscreen) {
		super(windowSize, debugMode, fullscreen);
		this.title = title;
	}
	
	public final void start() {
		FXApplication.begin(this);
	}
	
	protected final void init(Stage stage) {	
		this.stage = stage;
		stage.setTitle(title);
		
		canvas = new CS1971Canvas();
		
		scene = new Scene(canvas.root(), currentStageSize.x, currentStageSize.y);
		canvas.resize(currentStageSize);
		stage.setScene(scene);
				
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent t) {
            	shutdown();
            }
        });
		
		stage.show();
		
		stage.setMinWidth(MINIMUM_STAGE_SIZE.x);
		stage.setMinHeight((stage.getHeight() - currentStageSize.y) + MINIMUM_STAGE_SIZE.y);
		
		onStartup();
		
		running = true;
		listen();
		timer = new Timeline(new KeyFrame(Duration.millis(tickDelay), e -> {
			callAllTick();
		}));
		timer.setCycleCount(1);
		timer.play();
		
	}
	
	@Override
	public final void doSetTickFrequency(long nanoDelay) {
		tickDelay = (int) (nanoDelay / 1000000);
		targetFPS = 1000 / tickDelay;
		interruptTimer();
	}
	
	@Override
	public final void doSetTargetFPS(int fps) {
		tickDelay = (int) (1000000000 / fps);
		targetFPS = fps;
		interruptTimer();
	}
	
	private final void interruptTimer() {
		ticksSinceUpdate = 0;
		fpsTracker = new long[targetFPS];
		if(running) {
			timer.stop();
			timer.getKeyFrames().clear();
			timer.getKeyFrames().add(new KeyFrame(Duration.millis(tickDelay), e -> {
				callAllTick();
			}));
			timer.play();
		}
	}
	
	@Override
	public final void doSetDebugMode() {
		// Handled each tick by checking the value of CS1971FrontEnd.debugMode
		if(!debugMode) {
			stage.setTitle(title);
		}
	}
	
	@Override
	public final void doSetFullscreen() {
		stage.setFullScreen(fullscreen);
		Vec2d size = new Vec2d(scene.getWidth(), scene.getHeight());
		callAllResize(size);
	}
	
	@Override
	public final void shutdown() {
		onShutdown();
		Platform.exit();
		System.exit(0);
	}
	
	// Helper function to resize the canvas, invoke a resize call, then invoke a draw call
	private final void callAllResize(Vec2d size) {
		canvas.resize(size);
		onResize(size);
		canvas.draw();
	}
	
	// Helper function to ready a new tick, update the title, invoke a tick call, then invoke a draw call
	private final void callAllTick() {
		long time = System.nanoTime();
		onTick(time - timeOfLastTick);
		fpsTracker[ticksSinceUpdate++ % targetFPS] = time - timeOfLastTick;
		timeOfLastTick = time;
		onLateTick();

		updateTitle();
		canvas.draw();
		timer.stop();
		timer.play();
	}
	
	private final void updateTitle() {
		if(debugMode) {
			debugTitle = title + " (Size: ";
			debugTitle = debugTitle + Integer.toString((int) currentStageSize.x) + ", ";
			debugTitle = debugTitle + Integer.toString((int) currentStageSize.y) + ")";
			
			debugTitle += " (FPS: ";
			if(ticksSinceUpdate > targetFPS) {
				long sum = 0;
				for(long l : fpsTracker) {
					sum += l;
				}
				long fps = (1000000000L * targetFPS) / sum;
				debugTitle = debugTitle + Long.toString(fps) + ")";
			} else {
				int i = (ticksSinceUpdate * 3) / targetFPS;
				debugTitle = debugTitle + "calc";
				for(int j = 0; j < i; j++) debugTitle += ".";
				debugTitle = debugTitle + ")";
			}
			
			stage.setTitle(debugTitle);
		}
	}
	
	
	private final void listen() {
		stage.widthProperty().addListener((obs, oldVal, newVal) -> {
			currentStageSize = new Vec2d(scene.getWidth(), scene.getHeight());
			callAllResize(currentStageSize);
		});
		stage.heightProperty().addListener((obs, oldVal, newVal) -> {
			currentStageSize = new Vec2d(scene.getWidth(), scene.getHeight());
			callAllResize(currentStageSize);
		});
		stage.focusedProperty().addListener((obs, oldVal, newVal) -> {
			onFocusChanged(newVal);
		});
		
		scene.setOnKeyPressed(e -> {
			onKeyPressed(e);
		});
		scene.setOnKeyTyped(e -> {
			onKeyTyped(e);
		});
		scene.setOnKeyReleased(e -> {
			onKeyReleased(e);
		});
		
		scene.setOnMouseMoved(e -> {
			onMouseMoved(e);
		});
		scene.setOnMouseDragged(e -> {
			onMouseDragged(e);
		});
		scene.setOnMouseClicked(e -> {
			onMouseClicked(e);
		});
		scene.setOnMousePressed(e -> {
			onMousePressed(e);
		});
		scene.setOnMouseReleased(e -> {
			onMouseReleased(e);
		});
		
		scene.setOnScroll(e -> {
			onMouseWheelMoved(e);
		});
		
	}
	

	
	private final class CS1971Canvas {
		
		private final Pane root;
		private final Canvas canvas;
		
		public CS1971Canvas() {
			root = new Pane();
			canvas = new Canvas();
			
			root.getChildren().addAll(canvas);
		}
		
		public final Pane root() {
			return root;
		}
		
		public void resize(Vec2d size) {
			root.setPrefWidth(size.x);
			root.setPrefHeight(size.y);
			canvas.setWidth(size.x);
			canvas.setHeight(size.y);
		}
		
		public void draw() {
			GraphicsContext g = canvas.getGraphicsContext2D();
			g.clearRect(0, 0, currentStageSize.x, currentStageSize.y);
			onDraw(g);
		}
		
	}

}
