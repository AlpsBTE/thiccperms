package com.buildtheearth_atchli.thiccperms;

import com.google.common.collect.Lists;
import net.minecraft.command.ICommand;
import net.minecraftforge.server.permission.DefaultPermissionLevel;

import java.util.List;

public abstract class PermissionsHolder {
    public boolean all = false;
    public boolean all_non_op = true;
    public List<String> allowedCommands = Lists.newArrayList();
    public List<String> bannedCommands = Lists.newArrayList();
    public String parentName = null;
    public com.buildtheearth_atchli.thiccperms.PermissionsHolder _parent;
    public boolean _init = false;
    protected List<String> _whiteWildCards;
    protected List<String> _blackWildCards;

    private void init() {
        _whiteWildCards = Lists.newArrayList();
        _blackWildCards = Lists.newArrayList();
        if (allowedCommands == null) allowedCommands = Lists.newArrayList();
        if (bannedCommands == null) bannedCommands = Lists.newArrayList();
        _init = true;
        for (String s : allowedCommands) {
            if (s.endsWith("*")) {
                _whiteWildCards.add(s.substring(0, s.length() - 1));
            } else if (s.startsWith("*")) {
                _whiteWildCards.add(s.substring(1));
            }
        }
        for (String s : bannedCommands) {
            if (s.endsWith("*")) {
                _blackWildCards.add(s.substring(0, s.length() - 1));
            } else if (s.startsWith("*")) {
                _blackWildCards.add(s.substring(1));
            }
        }
    }

    public List<String> getAllowedCommands() {
        if (allowedCommands == null) allowedCommands = Lists.newArrayList();
        return allowedCommands;
    }

    public List<String> getBannedCommands() {
        if (bannedCommands == null) bannedCommands = Lists.newArrayList();
        return bannedCommands;
    }

    public boolean isDenied(String permission) {
        if (_parent != null && _parent.isDenied(permission)) return true;
        if (!_init || _blackWildCards == null || bannedCommands == null) init();
        for (String pattern : _blackWildCards) {
            if (permission.startsWith(pattern)) return true;
            else if (permission.matches(pattern)) return true;
        }
        return bannedCommands.contains(permission);
    }

    public boolean isAllowed(String permission) {
        if (_parent != null && _parent.isAllowed(permission)) return true;
        if (isAll()) return true;
        if (!_init || _whiteWildCards == null || allowedCommands == null) init();
        if (isAll_non_op() && ThutPerms.manager.getDefaultPermissionLevel(permission) == DefaultPermissionLevel.ALL)
            return true;
        for (String pattern : _whiteWildCards) {
            if (permission.startsWith(pattern)) return true;
            else if (permission.matches(pattern)) return true;
        }
        return allowedCommands.contains(permission);
    }

    public boolean hasPermission(String permission) {
        // Check if permission is specifically denied.
        if (isDenied(permission)) return false;
        // check if permission is allowed.
        return isAllowed(permission);
    }

    public boolean canUse(ICommand command) {
        // Check if the command falls under any denied rules.
        if (isDenied("command." + command.getName())) return false;
        for (String alias : command.getAliases()) {
            if (isDenied("command." + alias)) return false;
        }
        if (ThutPerms.customCommandPerms.containsKey(command.getName())
                && isDenied(ThutPerms.customCommandPerms.get(command.getName())))
            return false;
        if (isDenied(command.getClass().getName())) return false;

        // Then check if it is allowed.
        if (isAllowed("command." + command.getName())) return true;
        for (String alias : command.getAliases()) {
            if (isAllowed("command." + alias)) return true;
        }
        if (ThutPerms.customCommandPerms.containsKey(command.getName())
                && isAllowed(ThutPerms.customCommandPerms.get(command.getName())))
            return true;
        return isAllowed(command.getClass().getName());
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }

    public boolean isAll_non_op() {
        return all_non_op;
    }

    public void setAll_non_op(boolean all_non_op) {
        this.all_non_op = all_non_op;
    }

}
