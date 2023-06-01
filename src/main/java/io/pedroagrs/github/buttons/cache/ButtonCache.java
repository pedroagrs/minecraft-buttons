package io.pedroagrs.github.buttons.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import io.pedroagrs.github.buttons.cache.listener.ButtonRemovalListener;
import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.model.Button;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class ButtonCache {

    private final Cache<String, Button> cache = Caffeine.newBuilder()
            .removalListener(new ButtonRemovalListener())
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .build();

    public ButtonResponse insert(Button button) {
        if (exists(button.getId())) return ButtonResponse.ALREADY_EXISTS;

        cache.put(button.getId(), button);

        return ButtonResponse.SUCCESS;
    }

    public ButtonResponse invalidate(String id) {
        if (!exists(id)) return ButtonResponse.NOT_FOUND;

        cache.invalidate(id);

        return ButtonResponse.SUCCESS;
    }

    public ButtonResponse update(Button button) {
        if (!exists(button.getId())) return ButtonResponse.NOT_FOUND;

        return cache.asMap().replace(button.getId(), button) != null ? ButtonResponse.SUCCESS : ButtonResponse.FAILURE;
    }

    public Optional<Button> find(String id) {
        return Optional.ofNullable(cache.asMap().get(id));
    }

    public Collection<Button> values() {
        return Collections.unmodifiableCollection(cache.asMap().values());
    }

    private boolean exists(String id) {
        return cache.asMap().containsKey(id);
    }

    public void flush() {
        cache.invalidateAll();
    }
}
