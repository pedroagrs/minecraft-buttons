package io.pedroagrs.github.buttons.scheduler;

import io.pedroagrs.github.buttons.ButtonPlugin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ButtonPlayerFadeOutScheduler implements Runnable {

    private final ButtonPlugin plugin;

    @Override
    public void run() {
        plugin.getService().getPlayerProvider().tick();
    }
}
