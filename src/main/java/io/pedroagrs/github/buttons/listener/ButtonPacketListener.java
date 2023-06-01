package io.pedroagrs.github.buttons.listener;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import io.pedroagrs.github.buttons.ButtonPlugin;
import io.pedroagrs.github.buttons.ButtonService;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ButtonPacketListener extends PacketAdapter {

    private static final PacketType[] TYPES =
            new PacketType[] {PacketType.Play.Client.LOOK, PacketType.Play.Client.POSITION_LOOK};
    private final ButtonService service;

    public ButtonPacketListener(ButtonPlugin plugin) {
        super(new AdapterParameteters()
                .plugin(plugin)
                .listenerPriority(ListenerPriority.HIGH)
                .types(TYPES)
                .serverSide());

        this.service = plugin.getService();
    }

    @Override
    public void onPacketReceiving(PacketEvent event) {
        Player player = event.getPlayer();

        Vector direction = player.getEyeLocation().getDirection();
        Vector source = player.getEyeLocation().toVector();

        service.getLookingButton(player, direction, source)
                .ifPresent(button -> service.getPlayerProvider().look(player, button));
    }
}
