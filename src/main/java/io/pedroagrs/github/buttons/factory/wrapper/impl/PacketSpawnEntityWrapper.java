package io.pedroagrs.github.buttons.factory.wrapper.impl;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import io.pedroagrs.github.buttons.factory.wrapper.AbstractPacket;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * Wrapper for the packet {@link PacketType.Play.Server#SPAWN_ENTITY_LIVING}.
 */
public class PacketSpawnEntityWrapper extends AbstractPacket {

    @SuppressWarnings("deprecation")
    public static final PacketType TYPE = PacketType.Play.Server.SPAWN_ENTITY_LIVING;

    public PacketSpawnEntityWrapper() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    /**
     * Retrieve the entity ID.
     *
     * @return The current entity ID
     */
    public int getEntityID() {
        return handle.getIntegers().read(0);
    }

    /**
     * Set entity ID.
     *
     * @param value - new value.
     */
    public void setEntityID(int value) {
        handle.getIntegers().write(0, value);
    }

    /**
     * Set the type of mob.
     *
     * @param value - new value.
     */
    @SuppressWarnings("deprecation")
    public void setType(@NonNull EntityType value) {
        handle.getIntegers().write(1, (int) value.getTypeId());
    }

    /**
     * Retrieve the x position of the object.
     * <p>
     * Note that the coordinate is rounded off to the nearest 1/32 of a meter.
     *
     * @return The current X
     */
    public double getX() {
        return handle.getIntegers().read(2);
    }

    /**
     * Set the x position of the object.
     *
     * @param value - new value.
     */
    public void setX(double value) {
        handle.getIntegers().write(2, (int) (value * 32));
    }

    /**
     * Retrieve the y position of the object.
     * <p>
     * Note that the coordinate is rounded off to the nearest 1/32 of a meter.
     *
     * @return The current y
     */
    public double getY() {
        return handle.getIntegers().read(3);
    }

    /**
     * Set the y position of the object.
     *
     * @param value - new value.
     */
    public void setY(double value) {
        handle.getIntegers().write(3, (int) (value * 32.0D));
    }

    /**
     * Retrieve the z position of the object.
     * <p>
     * Note that the coordinate is rounded off to the nearest 1/32 of a meter.
     *
     * @return The current z
     */
    public double getZ() {
        return handle.getIntegers().read(4);
    }

    /**
     * Set the z position of the object.
     *
     * @param value - new value.
     */
    public void setZ(double value) {
        handle.getIntegers().write(4, (int) (value * 32.0D));
    }

    /**
     * Retrieve the yaw.
     *
     * @return The current Yaw
     */
    public float getYaw() {
        return (handle.getBytes().read(0) * 360.F) / 256.0F;
    }

    /**
     * Set the yaw of the spawned mob.
     *
     * @param value - new yaw.
     */
    public void setYaw(float value) {
        handle.getBytes().write(0, (byte) (value * 256.0F / 360.0F));
    }

    /**
     * Retrieve the pitch.
     *
     * @return The current pitch
     */
    public float getPitch() {
        return (handle.getBytes().read(1) * 360.F) / 256.0F;
    }

    /**
     * Set the pitch of the spawned mob.
     *
     * @param value - new pitch.
     */
    public void setPitch(float value) {
        handle.getBytes().write(1, (byte) (value * 256.0F / 360.0F));
    }

    /**
     * Retrieve the head position.
     * @return The current head position
     */
    public float getHeadPosition() {
        return (handle.getBytes().read(2) * 360.F) / 256.0F;
    }

    /**
     * Set the head position.
     * @param value - new value.
     */
    public void setHeadPosition(float value) {
        handle.getBytes().write(2, getCompressedAngle(value));
    }

    /**
     * Retrieve the data watcher.
     * <p>
     * Content varies by mob, see Entities.
     *
     * @return The current Metadata
     */
    public WrappedDataWatcher getDataWatcher() {
        return handle.getDataWatcherModifier().read(0);
    }

    /**
     * Set the data watcher.
     * @param watcher - watcher
     */
    public void setDataWatcher(@NonNull WrappedDataWatcher watcher) {
        handle.getDataWatcherModifier().write(0, watcher);
    }

    /**
     * Create a fake data watcher for the given entity type.
     * @param world - world
     */
    @ParametersAreNonnullByDefault
    public void setFakeDataWatcher(String world) {
        Location location = new Location(Bukkit.getWorld(world), 0, 256, 0, 0.0f, 0.0f);
        ArmorStand entity = location.getWorld().spawn(location, ArmorStand.class);

        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        entity.remove();

        setDataWatcher(watcher);
    }
}