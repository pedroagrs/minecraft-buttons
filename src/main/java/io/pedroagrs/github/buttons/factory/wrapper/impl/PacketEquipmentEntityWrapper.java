package io.pedroagrs.github.buttons.factory.wrapper.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import io.pedroagrs.github.buttons.factory.wrapper.AbstractPacket;
import org.bukkit.inventory.ItemStack;

public class PacketEquipmentEntityWrapper extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_EQUIPMENT;

    public PacketEquipmentEntityWrapper() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public void setEntityId(int value) {
        handle.getIntegers().write(0, value);
    }

    public void setSlot(int value) {
        handle.getIntegers().write(1, value);
    }

    public void setItemStack(ItemStack value) {
        handle.getItemModifier().write(0, value);
    }
}
