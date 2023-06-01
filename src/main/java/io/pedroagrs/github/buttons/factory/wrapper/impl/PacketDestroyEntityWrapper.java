package io.pedroagrs.github.buttons.factory.wrapper.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import io.pedroagrs.github.buttons.factory.wrapper.AbstractPacket;

/**
 * Wrapper for the packet {@link PacketType.Play.Server#ENTITY_DESTROY}.
 */
public class PacketDestroyEntityWrapper extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;

    public PacketDestroyEntityWrapper(int entityId) {
        super(new PacketContainer(TYPE), TYPE);

        handle.getModifier().writeDefaults();
        handle.getIntegerArrays().write(0, new int[] {entityId});
    }
}
