package io.pedroagrs.github.buttons.factory.wrapper.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import io.pedroagrs.github.buttons.factory.wrapper.AbstractPacket;

/**
 * Wrapper for the packet type {@link PacketType.Play.Server#ENTITY_TELEPORT}.
 */
public class PacketTeleportEntityWrapper extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_TELEPORT;

    /**
     * Constructs a new strongly typed wrapper for the given packet.
     */
    protected PacketTeleportEntityWrapper() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    /**
     * Retrieve entity ID.
     * @param value - new value.
     */
    public void setEntityID(int value) {
        handle.getIntegers().write(0, value);
    }

    /**
     * Retrieve X.
     * @param value - new value.
     */
    public void setX(double value) {
        handle.getIntegers().write(1, (int) (value * 32.0D));
    }

    /**
     * Retrieve Y.
     * @param value - new value.
     */
    public void setY(double value) {
        handle.getIntegers().write(2, (int) (value * 32.0D));
    }

    /**
     * Retrieve Z.
     * @param value - new value.
     */
    public void setZ(double value) {
        handle.getIntegers().write(3, (int) (value * 32.0D));
    }

    /**
     * Retrieve Yaw.
     * @param value - new value.
     */
    public void setYaw(byte value) {
        handle.getBytes().write(0, value);
    }

    /**
     * Retrieve Pitch.
     * @param value - new value.
     */
    public void setPitch(byte value) {
        handle.getBytes().write(1, value);
    }

    /**
     * Retrieve On Ground.
     * @param value - new value.
     */
    public void setOnGround(boolean value) {
        handle.getBooleans().write(0, value);
    }
}
