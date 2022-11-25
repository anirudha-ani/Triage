package engine.UIElement;

import engine.support.Vec2d;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MiniMap {
    Vec2d origin;
    Vec2d size;
    char[][] mapLayout;
    double widthPerBlock;
    double heightPerBlock;

    public MiniMap(Vec2d origin, Vec2d size, char[][] mapLayout) {
        this.origin = origin;
        this.size = size;
        this.mapLayout = mapLayout;
        widthPerBlock = size.x / mapLayout[0].length;
        heightPerBlock = size.y / mapLayout.length;
    }

    public void drawElement(GraphicsContext g) {

        for (int i = 0; i < mapLayout.length; i++) {
            for (int j = 0; j < mapLayout[0].length; j++) {
                Vec2d blockOrigin = new Vec2d(origin.x + j * widthPerBlock, origin.y + i * heightPerBlock);
                Vec2d blockSize = new Vec2d(widthPerBlock, heightPerBlock);
                RectangleUI rectangleUI = new RectangleUI(blockOrigin, blockSize);

                if (mapLayout[i][j] == '#') {
                    rectangleUI.setBackGroundColor(Color.GRAY);
                } else if (mapLayout[i][j] == 'P') {
                    rectangleUI.setBackGroundColor(Color.RED);
                } else {
                    rectangleUI.setBackGroundColor(Color.GREEN);
                }
                rectangleUI.drawElement(g);
            }
        }
    }
}
