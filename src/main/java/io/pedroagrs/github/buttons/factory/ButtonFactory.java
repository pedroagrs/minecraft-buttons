package io.pedroagrs.github.buttons.factory;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import io.pedroagrs.github.buttons.factory.wrapper.AbstractPacket;
import io.pedroagrs.github.buttons.factory.wrapper.impl.PacketEquipmentEntityWrapper;
import io.pedroagrs.github.buttons.factory.wrapper.impl.PacketMetadataEntityWrapper;
import io.pedroagrs.github.buttons.factory.wrapper.impl.PacketSpawnEntityWrapper;
import io.pedroagrs.github.buttons.model.Button;
import io.pedroagrs.github.buttons.model.ButtonFade;
import io.pedroagrs.github.buttons.model.ButtonPosition;
import io.pedroagrs.github.buttons.util.ButtonPacketUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.LinkedList;
import java.util.List;

public class ButtonFactory {

    public static final int DEFAULT_VIEW_RADIUS = 16;

    public static final double DEFAULT_MAX_DISTANCE = 3.7D,
            DEFAULT_EPSILON_Y = 1.67, // y
            DEFAULT_EPSILON_Y_SMALL = 1.0D, // y
            DEFAULT_EPSILON_DOT = 0.990D, // dot
            DEFAULT_EPSILON_DOT_SMALL = 0.995D; // dot

    @ParametersAreNonnullByDefault
    public Button createButton(String id, String text, ButtonPosition position) {
        return Button.builder().id(id).display(text).position(position).build();
    }

    public LinkedList<AbstractPacket> createButtonPacket(Button button) {
        PacketSpawnEntityWrapper spawnPacket = new PacketSpawnEntityWrapper();
        PacketEquipmentEntityWrapper equipmentPacket = new PacketEquipmentEntityWrapper();
        ButtonPosition position = button.getPosition();

        spawnPacket.setEntityID(ButtonPacketUtil.getNextEntityId());
        spawnPacket.setType(EntityType.ARMOR_STAND);
        spawnPacket.setFakeDataWatcher("world");

        spawnPacket.setX(position.getX());
        spawnPacket.setY(position.getY());
        spawnPacket.setZ(position.getZ());

        WrappedDataWatcher dataWatcher = spawnPacket.getDataWatcher();

        dataWatcher.setObject(0, (byte) 0x20); // Invisible
        dataWatcher.setObject(2, ChatColor.translateAlternateColorCodes('&', button.getDisplay())); // Custom name
        dataWatcher.setObject(3, (byte) 1); // Custom name visible

        dataWatcher.setObject(10, (byte) 0x01); // small index

        button.getPosition().setEntityId(spawnPacket.getEntityID());
        button.setDataWatcher(dataWatcher);

        equipmentPacket.setEntityId(spawnPacket.getEntityID());
        equipmentPacket.setSlot(4);
        equipmentPacket.setItemStack(new ItemStack(Material.DIRT));

        return new LinkedList<>(List.of(spawnPacket, equipmentPacket));
    }

    public AbstractPacket createButtonFadePacket(Button button, ButtonFade fade) {
        PacketMetadataEntityWrapper packet = new PacketMetadataEntityWrapper();

        packet.setEntityId(button.getPosition().getEntityId());

        WrappedDataWatcher dataWatcher = button.getDataWatcher();
        WrappedWatchableObject fadeObject = dataWatcher.getWatchableObject(10); // small index

        if (fadeObject == null) return packet;

        fadeObject.setValue((fade == ButtonFade.FADE_OUT) ? (byte) 0x01 : (byte) 0x00);

        packet.setMetadata(dataWatcher.getWatchableObjects());

        return packet;
    }
}
