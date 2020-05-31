package com.buildtheearth_atchli.thiccessentials.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class NameEvent extends Event {
    public final EntityPlayer toName;
    String displayName;
    String newName;

    public NameEvent(EntityPlayer target, String name) {
        displayName = name;
        newName = name;
        toName = target;
    }

    public String getName() {
        return newName;
    }

    public void setName(String name) {
        newName = name;
    }

    public String getDefaultName() {
        return displayName;
    }
}
