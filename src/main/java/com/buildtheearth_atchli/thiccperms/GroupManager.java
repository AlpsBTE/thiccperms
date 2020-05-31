package com.buildtheearth_atchli.thiccperms;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

public class GroupManager {
    /**
     * Map of dimension id to group manager.
     */
    public static Map<Integer, GroupManager> _instanceMap = Maps.newHashMap();
    private static com.buildtheearth_atchli.thiccperms.GroupManager _instance;
    public Map<UUID, com.buildtheearth_atchli.thiccperms.Group> _groupIDMap = Maps.newHashMap();
    public Map<String, com.buildtheearth_atchli.thiccperms.Group> _groupNameMap = Maps.newHashMap();
    public HashSet<com.buildtheearth_atchli.thiccperms.Group> groups = Sets.newHashSet();

    public com.buildtheearth_atchli.thiccperms.Group initial = new com.buildtheearth_atchli.thiccperms.Group("default");
    public com.buildtheearth_atchli.thiccperms.Group mods = new com.buildtheearth_atchli.thiccperms.Group("mods");

    public com.buildtheearth_atchli.thiccperms.PlayerManager _manager = null;
    public MinecraftServer _server;

    public GroupManager() {
        ThutPerms.logger.log(Level.INFO, "Initializing Group Manager.");
        _manager = new com.buildtheearth_atchli.thiccperms.PlayerManager(this);
    }

    /**
     * @return the _instance
     */
    public static com.buildtheearth_atchli.thiccperms.GroupManager get_instance() {
        return _instance;
    }

    /**
     * @param _instance the _instance to set
     */
    public static void set_instance(com.buildtheearth_atchli.thiccperms.GroupManager _instance) {
        com.buildtheearth_atchli.thiccperms.GroupManager._instance = _instance;
    }

    public void init() {
        if (initial == null) initial = new com.buildtheearth_atchli.thiccperms.Group("default");
        _groupNameMap.put(initial.name, initial);
        if (mods == null) mods = new com.buildtheearth_atchli.thiccperms.Group("mods");
        _groupNameMap.put(mods.name, mods);
        for (com.buildtheearth_atchli.thiccperms.Group g : groups) {
            if (g.name.isEmpty()) g.name = "unnamed" + new Random().nextFloat();
            _groupNameMap.put(g.name, g);
            for (UUID id : g.members) {
                initial.members.remove(id);
                mods.members.remove(id);
                _groupIDMap.put(id, g);
            }
        }

        // Refeshes players in groups to ensure that there is only 1 group with
        // each player, this cleans up some issues with badly formatted
        // permissions files
        for (UUID id : _groupIDMap.keySet()) {
            ThutPerms.addToGroup(id, _groupIDMap.get(id).name);
        }

        mods.setAll(true);
        _groupNameMap.put(initial.name, initial);
        _groupNameMap.put(mods.name, mods);

        // Set up parents.
        for (com.buildtheearth_atchli.thiccperms.Group g : groups) {
            if (g.parentName != null) {
                g._parent = _groupNameMap.get(g.parentName);
                if (g._parent == null) g.parentName = null;
            }
        }
    }

    public com.buildtheearth_atchli.thiccperms.Group getPlayerGroup(UUID id) {
        com.buildtheearth_atchli.thiccperms.Group ret = _groupIDMap.get(id);
        if (ret == null) {
            if (_server.getPlayerList().getOppedPlayers().getEntry(new GameProfile(id, null)) != null) {
                return mods;
            }
            return initial;
        }
        return ret;
    }

    public boolean hasPermission(UUID id, String perm) {
        com.buildtheearth_atchli.thiccperms.Group g = getPlayerGroup(id);
        Player player = _manager.getPlayer(id);
        boolean canPlayerUse = (player != null && player.hasPermission(perm));

        // Check if that player is specifically denied the perm.
        if (player != null && player.isDenied(perm)) return false;

        return g.hasPermission(perm) || canPlayerUse;
    }

}
