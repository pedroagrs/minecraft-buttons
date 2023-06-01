package io.pedroagrs.github.buttons;

import io.pedroagrs.github.buttons.api.ButtonAPI;
import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.model.Button;
import io.pedroagrs.github.buttons.model.ButtonPosition;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * The default implementation of the {@link ButtonAPI}
 */
@RequiredArgsConstructor(staticName = "bind", access = AccessLevel.PROTECTED, onConstructor_ = @NonNull)
class ButtonProvider implements ButtonAPI {

    private final ButtonService service;

    @Override
    public ButtonResponse create(Button button) {
        return service.createButton(button.getId(), button.getDisplay(), button.getPosition());
    }

    @Override
    public ButtonResponse create(Button button, int radius) {
        return service.createButton(button, radius);
    }

    @Override
    public ButtonResponse create(Button button, Player... viewers) {
        return service.createButton(button.getId(), button.getDisplay(), button.getPosition(), viewers);
    }

    @Override
    public ButtonResponse create(String id, String text, Location location, Player... viewers) {
        return service.createButton(id, text, ButtonPosition.from(location.toVector()), viewers);
    }

    @Override
    public ButtonResponse delete(String id) {
        return service.deleteButton(id);
    }

    @Override
    public ButtonResponse update(Button button) {
        return service.updateButton(button);
    }
}
