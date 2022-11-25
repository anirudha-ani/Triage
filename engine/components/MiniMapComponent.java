package engine.components;

import engine.UIElement.MiniMap;
import engine.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class MiniMapComponent extends DrawableComponent {
    char[][] mapLayout;
    GameObject player;

    public MiniMapComponent(GameObject gameObject, GameObject player, char[][] mapLayout) {
        super("minimap", gameObject);
        this.mapLayout = mapLayout;
        this.player = player;

    }

    public void setMapLayout(char[][] mapLayout) {
        this.mapLayout = mapLayout;
    }

    @Override
    public void draw(GraphicsContext g) {
        int player_x = (int) Math.floor(player.transformComponent.getPositionOnWorld().x / 32);
        int player_y = (int) Math.floor(player.transformComponent.getPositionOnWorld().y / 32);

        for (int i = 0; i < mapLayout.length; i++) {
            for (int j = 0; j < mapLayout[0].length; j++) {
                if (mapLayout[i][j] == 'P') {
                    mapLayout[i][j] = '.';
                }
            }
        }
        mapLayout[player_y][player_x] = 'P';
        drawMiniMap(g, mapLayout);
    }

    public void drawMiniMap(GraphicsContext g, char[][] mapLayout) {
        MiniMap map = new MiniMap(getGameObject().getTransformComponent().getPositionOnWorld(), getGameObject().getTransformComponent().getSizeOnWorld(), mapLayout);
//        rect1.setBackGroundColor(getGameObject().getTransformComponent().getBackGroundColor());
        map.drawElement(g);
    }
}
