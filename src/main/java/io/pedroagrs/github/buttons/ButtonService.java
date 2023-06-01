package io.pedroagrs.github.buttons;

import io.pedroagrs.github.buttons.cache.ButtonCache;
import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.factory.ButtonFactory;
import io.pedroagrs.github.buttons.model.Button;
import io.pedroagrs.github.buttons.model.ButtonPosition;
import io.pedroagrs.github.buttons.provider.ButtonPlayerLookProvider;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Optional;

public interface ButtonService {

    ButtonCache CACHE = new ButtonCache();

    ButtonResponse createButton(@NonNull String id, String text, @NonNull ButtonPosition position, Player... viewers);

    ButtonResponse createButton(@NonNull Button button, int radius);

    ButtonResponse deleteButton(@NonNull String id);

    ButtonResponse updateButton(@NonNull Button button);

    Optional<Button> getButton(@NonNull String id);

    ButtonFactory getButtonFactory();

    ButtonPlayerLookProvider getPlayerProvider();

    @ParametersAreNonnullByDefault
    Optional<Button> getLookingButton(Player player, Vector direction, Vector source);

    void shutdown();
}
