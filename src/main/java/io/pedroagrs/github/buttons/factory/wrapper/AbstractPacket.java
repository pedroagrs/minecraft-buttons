package io.pedroagrs.github.buttons.factory.wrapper;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.model.ButtonPosition;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Abstract packet wrapper.
 */
public abstract class AbstractPacket {
    private static final ProtocolManager PROTOCOL_MANAGER = ProtocolLibrary.getProtocolManager();

    @Getter
    protected final PacketContainer handle;

    /**
     * Constructs a new strongly typed wrapper for the given packet.
     *
     * @param handle - handle to the raw packet data.
     * @param type - the packet type.
     */
    protected AbstractPacket(PacketContainer handle, PacketType type) {
        Objects.requireNonNull(handle, "Handle cannot be NULL.");

        if (!Objects.deepEquals(handle.getType(), type)) {
            throw new IllegalArgumentException(handle.getHandle() + " is not a packet of type " + type);
        }

        this.handle = handle;
    }

    /**
     * Send the packet to nearby players.
     * @param position - the button location
     * @param viewDistance - the view distance in blocks squared to load the button
     * @return the spawn result
     */
    public ButtonResponse sendPacket(ButtonPosition position, int viewDistance) {
        Location location = toLocation(position);

        if (location == null) return ButtonResponse.FAILURE;

        if (!location.getChunk().isLoaded()) return ButtonResponse.FAILURE;

        location.getWorld().getPlayers().stream()
                .filter(player -> player.getLocation().distanceSquared(location) <= viewDistance)
                .forEach(this::sendPacket);

        return ButtonResponse.SUCCESS;
    }

    /**
     * Send the packet to the given player.
     * @param player - the player to send the packet
     */
    public void sendPacket(Player player) {
        PROTOCOL_MANAGER.sendServerPacket(player, getHandle());
    }

    protected byte getCompressedAngle(float value) {
        return (byte) (int) (value * 256.0F / 360.0F);
    }

    private Location toLocation(ButtonPosition position) {
        World world = Bukkit.getWorld("world");

        if (world == null) return null;

        return new Location(world, position.getX(), position.getY(), position.getZ());
    }
}