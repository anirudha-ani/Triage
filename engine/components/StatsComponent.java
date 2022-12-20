package engine.components;

import engine.gameobjects.GameObject;

public class StatsComponent extends Component {

    private double health = 100 ;
    private double attack = 100 ;
    private String facing = "right"; // Keep the value only between left and right

    public StatsComponent(GameObject gameObject) {
        super("stats", gameObject);
    }

    public StatsComponent() {
        super("stats");
    }
    public StatsComponent(double health, double attack) {
        super("stats");
        this.health = health;
        this.attack = attack;
    }
    public StatsComponent(GameObject gameObject, double health, double attack) {
        super("stats");
        this.health = health;
        this.attack = attack;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public String getFacing() {
        return facing;
    }

    public void setFacing(String facing) {
        if(facing != "left" && facing != "right") {
            return;
        }
        this.facing = facing;
    }
}
