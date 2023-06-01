package io.pedroagrs.github.buttons.command;

import io.pedroagrs.github.buttons.api.ButtonAPI;
import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.factory.ButtonFactory;
import io.pedroagrs.github.buttons.model.Button;
import io.pedroagrs.github.buttons.model.ButtonPosition;
import io.pedroagrs.github.buttons.util.ButtonCommandUtil;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class ButtonExampleCommand extends Command {

    private static final String[] USAGE = {
        "§c/button create-radius <id> <radius> <name>", "§c/button create <id> <name>",
    };

    private final ButtonAPI api;

    public ButtonExampleCommand(ButtonAPI api) {
        super("button");

        this.api = api;

        CommandMap commandMap = ButtonCommandUtil.getCommandMap();

        if (commandMap == null) {
            Bukkit.getLogger().warning("§cCould not register button command! (Command map not found)");
            return;
        }

        commandMap.register("button", this);
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage(USAGE);
            return true;
        }

        if (handleArg(player, args[0], Arrays.copyOfRange(args, 1, args.length))) {
            player.sendMessage(String.format("§aButton §o%s§a created!", args[1]));
        } else {
            player.sendMessage(USAGE);
        }

        return true;
    }

    private boolean handleArg(Player player, String arg, String... args) {
        if (args.length < 2) return false;

        Button button = Button.builder()
                .id(args[0])
                .display(String.join(" ", Arrays.copyOfRange(args, 1, args.length)))
                .position(ButtonPosition.from(player.getEyeLocation().toVector()))
                .build();

        if (arg.equalsIgnoreCase("create-radius")) {
            return api.create(button, parseRadius(args[1])) == ButtonResponse.SUCCESS;
        } else if (arg.equalsIgnoreCase("create")) {
            return api.create(button, ButtonFactory.DEFAULT_VIEW_RADIUS) == ButtonResponse.SUCCESS;
        }

        return false;
    }

    private int parseRadius(String arg) {
        return Math.max(ButtonFactory.DEFAULT_VIEW_RADIUS, NumberUtils.toInt(arg, ButtonFactory.DEFAULT_VIEW_RADIUS));
    }
}
