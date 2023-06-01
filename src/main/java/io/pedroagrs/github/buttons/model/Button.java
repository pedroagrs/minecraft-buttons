package io.pedroagrs.github.buttons.model;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import io.pedroagrs.github.buttons.factory.ButtonFactory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@Builder
@Getter
public class Button {

    private final String id;

    @Setter
    private WrappedDataWatcher dataWatcher;

    @Builder.Default
    private String display = "N/A";

    private ButtonPosition position;

    public boolean isLookingAt(Vector directionVector, Vector sourceVector, double epsilonDot, double epsilonY) {
        return position.isLookingAt(directionVector, sourceVector, epsilonDot, epsilonY);
    }

    public void fade(Player viewer, ButtonFactory factory, ButtonFade fade) {
        factory.createButtonFadePacket(this, fade).sendPacket(viewer);
    }

    public void show(Player viewer, ButtonFactory factory) {
        factory.createButtonPacket(this).forEach(packet -> packet.sendPacket(viewer));
    }

    public void show(int radius, ButtonFactory factory) {
        factory.createButtonPacket(this).forEach(packet -> packet.sendPacket(position, radius));
    }

    public void upsert(Button button) {
        this.display = button.display;
        this.position = button.position;
    }
}
