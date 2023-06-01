package io.pedroagrs.github.buttons;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import io.pedroagrs.github.buttons.api.ButtonAPI;
import io.pedroagrs.github.buttons.command.ButtonExampleCommand;
import io.pedroagrs.github.buttons.listener.ButtonPacketListener;
import io.pedroagrs.github.buttons.scheduler.ButtonPlayerFadeOutScheduler;
import io.pedroagrs.github.buttons.service.ButtonServiceImpl;
import lombok.Getter;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import static org.bukkit.Bukkit.getScheduler;
import static org.bukkit.Bukkit.getServicesManager;

public class ButtonPlugin extends JavaPlugin {

    @Getter
    private ButtonService service;

    @Override
    public void onEnable() {
        init();
    }

    @Override
    public void onDisable() {
        shutdown();
    }

    private void init() {
        saveDefaultConfig();

        getLogger().info("Loading modules...");

        registerService();
        registerListener();
        registerCommand();
        registerScheduler();

        getLogger().info("Modules loaded!");
    }

    private void shutdown() {
        service.shutdown();

        // plugman issues
        this.service = null;
        ProtocolLibrary.getProtocolManager().removePacketListeners(this);
    }

    private void registerListener() {
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

        protocolManager.addPacketListener(new ButtonPacketListener(this));
    }

    private void registerService() {
        ButtonProvider provider = ButtonProvider.bind(this.service = new ButtonServiceImpl());

        getServicesManager().register(ButtonAPI.class, provider, this, ServicePriority.Highest);
    }

    private void registerScheduler() {
        getScheduler().scheduleSyncRepeatingTask(this, new ButtonPlayerFadeOutScheduler(this), 40L, 40L);
    }

    private void registerCommand() {
        if (getConfig().getBoolean("settings.enable-example-command")) {
            new ButtonExampleCommand(getServicesManager().load(ButtonAPI.class));
        }
    }
}
