package engine.components;

import engine.gameobjects.GameObject;
import javafx.scene.canvas.GraphicsContext;

public class DrawableComponent extends Component {
    public DrawableComponent(String tag, GameObject gameObject) {
        super(tag, gameObject);
    }

    @Override
    public void draw(GraphicsContext g) {

    }
}
