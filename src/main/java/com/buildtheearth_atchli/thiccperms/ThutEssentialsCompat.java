package com.buildtheearth_atchli.thiccperms;

import com.buildtheearth_atchli.thiccessentials.events.NameEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ThutEssentialsCompat {

    public ThutEssentialsCompat() {
    }

    @SubscribeEvent
    public void NameEvent(NameEvent evt) {
        com.buildtheearth_atchli.thiccperms.Group g = com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().getPlayerGroup(evt.toName.getUniqueID());
        String name = evt.getName();
        // Apply main group suffix first.
        if (g != null) {
            if (!g.prefix.isEmpty()) name = g.prefix + name;
        }

        com.buildtheearth_atchli.thiccperms.Player player = com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._manager.getPlayer(evt.toName.getUniqueID());
        if (player != null) {
            // Prefixes in order in list
            for (com.buildtheearth_atchli.thiccperms.Group g1 : player._groups) {
                if (!g1.prefix.isEmpty()) name = g1.prefix + name;
            }
            // Suffixes in order in list
            for (com.buildtheearth_atchli.thiccperms.Group g1 : player._groups) {
                if (!g1.suffix.isEmpty()) name = name + g1.suffix;
            }
        }
        // Main group suffic last.
        if (g != null) {
            if (!g.suffix.isEmpty()) name = name + g.suffix;
        }
        evt.setName(name);
    }

}
