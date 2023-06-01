package io.pedroagrs.github.buttons.cache.listener;

import com.github.benmanes.caffeine.cache.RemovalCause;
import com.github.benmanes.caffeine.cache.RemovalListener;
import io.pedroagrs.github.buttons.model.Button;
import org.checkerframework.checker.nullness.qual.Nullable;

public class ButtonRemovalListener implements RemovalListener<String, Button> {

    @Override
    public void onRemoval(@Nullable String key, @Nullable Button value, RemovalCause cause) {}
}
