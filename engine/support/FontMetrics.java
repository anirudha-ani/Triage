package engine.support;// package

import javafx.geometry.Bounds;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public final class FontMetrics {
	
	public final double width;
	public final double height;
	
	public FontMetrics(String text) {
		final Text t = new Text(text);
		Bounds b = t.getLayoutBounds();
		width = b.getWidth();
		height = b.getHeight();
		
	}
	
	public FontMetrics(String text, Font font) {
		final Text t = new Text(text);
		t.setFont(font);
		Bounds b = t.getLayoutBounds();
		width = b.getWidth();
		height = b.getHeight();
	}

}
