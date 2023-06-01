package io.pedroagrs.github.buttons.provider;

import com.google.common.collect.Maps;
import io.pedroagrs.github.buttons.ButtonService;
import io.pedroagrs.github.buttons.factory.ButtonFactory;
import io.pedroagrs.github.buttons.model.Button;
import io.pedroagrs.github.buttons.model.ButtonFade;
import io.pedroagrs.github.buttons.model.ButtonLookPlayer;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class ButtonPlayerLookProvider {

    private final Map<UUID, ButtonLookPlayer> cache = Maps.newConcurrentMap();

    private final ButtonService service;

    public void look(Player player, Button button) {
        if (addButton(player, button)) button.fade(player, service.getButtonFactory(), ButtonFade.FADE_IN);
    }

    public double getEpsilonY(Player player, Button button) {
        ButtonLookPlayer lookPlayer = cache.get(player.getUniqueId());

        return lookPlayer != null ? lookPlayer.getEpsilonY(button) : ButtonFactory.DEFAULT_EPSILON_Y_SMALL;
    }

    public double getEpsilonDot(Player player, Button button) {
        ButtonLookPlayer lookPlayer = cache.get(player.getUniqueId());

        return lookPlayer != null ? lookPlayer.getEpsilonDot(button) : ButtonFactory.DEFAULT_EPSILON_DOT_SMALL;
    }

    private boolean addButton(Player player, Button button) {
        if (cache.containsKey(player.getUniqueId())) {
            return cache.get(player.getUniqueId()).addButton(button);
        }

        cache.put(player.getUniqueId(), new ButtonLookPlayer(button));

        return true;
    }

    public void tick() {
        cache.entrySet().removeIf(entry -> entry.getValue().fadeOut(Bukkit.getPlayer(entry.getKey()), service));
    }
}
