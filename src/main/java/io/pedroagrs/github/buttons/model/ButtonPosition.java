package io.pedroagrs.github.buttons.model;

import io.pedroagrs.github.buttons.factory.ButtonFactory;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.util.Vector;

@Getter
@Setter
public class ButtonPosition {

    private double x, y, z;

    private int entityId = -1;

    private ButtonPosition(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static ButtonPosition from(Vector vector) {
        return new ButtonPosition(vector.getX(), vector.getY(), vector.getZ());
    }

    public static ButtonPosition subtract(ButtonPosition position, ButtonPosition other) {
        return new ButtonPosition(position.x - other.x, position.y - other.y, position.z - other.z);
    }

    public void normalize(double magnitude) {
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
    }

    public double magnitude() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public float dot(ButtonPosition position) {
        return (float) (this.x * position.getX() + this.y * position.getY() + this.z * position.getZ());
    }

    public boolean isLookingAt(Vector directionVector, Vector sourceVector, double epsilonDot, double epsilonY) {
        ButtonPosition distance = ButtonPosition.subtract(fixedPosition(epsilonY), ButtonPosition.from(sourceVector));
        double magnitude = distance.magnitude();

        if (magnitude != 0) distance.normalize(distance.magnitude());

        float dot = distance.dot(ButtonPosition.from(directionVector));

        return dot > epsilonDot && magnitude < ButtonFactory.DEFAULT_MAX_DISTANCE;
    }

    private ButtonPosition fixedPosition(double epsilonY) {
        return new ButtonPosition(x, y + epsilonY, z);
    }
}
