package io.pedroagrs.github.buttons.service;

import io.pedroagrs.github.buttons.ButtonService;
import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.factory.ButtonFactory;
import io.pedroagrs.github.buttons.model.Button;
import io.pedroagrs.github.buttons.model.ButtonPosition;
import io.pedroagrs.github.buttons.provider.ButtonPlayerLookProvider;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Optional;

@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService {

    private final ButtonFactory factory = new ButtonFactory();

    private final ButtonPlayerLookProvider playerProvider = new ButtonPlayerLookProvider(this);

    @Override
    public ButtonResponse createButton(
            @NonNull String id, String text, @NonNull ButtonPosition position, Player... viewers) {
        Button button = factory.createButton(id, text, position);

        if (viewers.length == 0) return createButton(button, ButtonFactory.DEFAULT_VIEW_RADIUS);

        ButtonResponse response = CACHE.insert(button);

        if (response == ButtonResponse.SUCCESS) {
            for (Player viewer : viewers) button.show(viewer, this.factory);
        }

        return response;
    }

    @Override
    public ButtonResponse createButton(@NonNull Button button, int radius) {
        ButtonResponse response = CACHE.insert(button);

        if (response == ButtonResponse.SUCCESS) button.show(radius, this.factory);

        return response;
    }

    @Override
    public ButtonResponse deleteButton(@NonNull String id) {
        return CACHE.invalidate(id);
    }

    @Override
    public Optional<Button> getButton(@NonNull String id) {
        return CACHE.find(id);
    }

    @Override
    public ButtonResponse updateButton(@NonNull Button button) {
        return CACHE.update(button);
    }

    @Override
    public ButtonFactory getButtonFactory() {
        return this.factory;
    }

    @Override
    public ButtonPlayerLookProvider getPlayerProvider() {
        return this.playerProvider;
    }

    @Override
    public Optional<Button> getLookingButton(
            @NonNull Player player, @NonNull Vector direction, @NonNull Vector source) {
        return CACHE.values().stream()
                .filter(button -> {
                    double epsilonY = playerProvider.getEpsilonY(player, button);
                    double epsilonDot = playerProvider.getEpsilonDot(player, button);

                    return button.isLookingAt(direction, source, epsilonDot, epsilonY);
                })
                .findFirst();
    }

    @Override
    public void shutdown() {
        CACHE.flush();
    }
}
