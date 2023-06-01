package io.pedroagrs.github.buttons.util;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;

@UtilityClass
public class ButtonCommandUtil {

    private final CommandMap COMMAND_MAP;

    static {
        CommandMap commandMap;
        try {
            Field fieldCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            fieldCommandMap.setAccessible(true);

            commandMap = (CommandMap) fieldCommandMap.get(Bukkit.getServer());
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            commandMap = null;
            exception.printStackTrace();
        }

        COMMAND_MAP = commandMap;
    }

    public CommandMap getCommandMap() {
        return COMMAND_MAP;
    }
}
