package engine.hitboxes;

import engine.support.Vec2d;

public final class Ray {

    public final Vec2d src;
    public final Vec2d dir;

    public Ray(Vec2d src, Vec2d dir) {
        this.src = src;
        this.dir = dir;
    }

}
