package io.pedroagrs.github.buttons.model;

import com.google.common.collect.Maps;
import io.pedroagrs.github.buttons.ButtonService;
import io.pedroagrs.github.buttons.factory.ButtonFactory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ButtonLookPlayer {

    private final Map<String, Long> buttons = Maps.newConcurrentMap();

    public ButtonLookPlayer(Button button) {
        addButton(button);
    }

    public boolean fadeOut(Player player, ButtonService service) {
        if (player == null) return true;

        buttons.entrySet().removeIf(entry -> {
            if (entry.getValue() > System.currentTimeMillis()) return false;

            Optional<Button> button = service.getButton(entry.getKey());

            if (button.isEmpty()) return true;
            if (isLookingAt(button.get(), player)) return false;

            button.get().fade(player, service.getButtonFactory(), ButtonFade.FADE_OUT);

            return true;
        });

        return buttons.isEmpty();
    }

    public double getEpsilonY(Button button) {
        return buttons.containsKey(button.getId())
                ? ButtonFactory.DEFAULT_EPSILON_Y
                : ButtonFactory.DEFAULT_EPSILON_Y_SMALL;
    }

    public double getEpsilonDot(Button button) {
        return buttons.containsKey(button.getId())
                ? ButtonFactory.DEFAULT_EPSILON_DOT
                : ButtonFactory.DEFAULT_EPSILON_DOT_SMALL;
    }

    public boolean addButton(Button button) {
        if (buttons.containsKey(button.getId())) return false;

        this.buttons.put(button.getId(), TimeUnit.SECONDS.toMillis(3)); // fadeout delay

        return true;
    }

    private boolean isLookingAt(Button button, Player player) {
        Vector direction = player.getEyeLocation().getDirection();
        Vector source = player.getEyeLocation().toVector();

        return button.getPosition().isLookingAt(direction, source, getEpsilonDot(button), getEpsilonY(button));
    }
}
