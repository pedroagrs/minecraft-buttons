package io.pedroagrs.github.buttons.factory.wrapper.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import io.pedroagrs.github.buttons.factory.wrapper.AbstractPacket;

import java.util.List;

public class PacketMetadataEntityWrapper extends AbstractPacket {

    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_METADATA;

    public PacketMetadataEntityWrapper() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public void setEntityId(int value) {
        handle.getIntegers().write(0, value);
    }

    public void setMetadata(List<WrappedWatchableObject> values) {
        handle.getWatchableCollectionModifier().write(0, values);
    }
}
