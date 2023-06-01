package io.pedroagrs.github.buttons.util;

import com.comphenix.protocol.reflect.accessors.Accessors;
import com.comphenix.protocol.reflect.accessors.FieldAccessor;
import com.comphenix.protocol.utility.MinecraftReflection;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ButtonPacketUtil {

    private final FieldAccessor ENTITY_ID =
            Accessors.getFieldAccessorOrNull(MinecraftReflection.getEntityClass(), "entityCount", int.class);

    public int getNextEntityId() {
        Integer entityCount = (Integer) ENTITY_ID.get(null);

        ENTITY_ID.set(null, entityCount + 1);

        return entityCount;
    }
}
