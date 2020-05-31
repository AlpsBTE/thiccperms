package com.buildtheearth_atchli.thiccperms;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.UUID;

public class Player extends com.buildtheearth_atchli.thiccperms.PermissionsHolder {
    public UUID id;
    public List<String> groups = Lists.newArrayList();
    public List<com.buildtheearth_atchli.thiccperms.Group> _groups = Lists.newArrayList();

    public Player() {
    }

    public boolean addGroup(com.buildtheearth_atchli.thiccperms.Group group) {
        if (groups.contains(group.name)) return false;
        groups.add(group.name);
        _groups.add(group);
        return true;
    }

    public boolean removeGroup(com.buildtheearth_atchli.thiccperms.Group group) {
        if (!_groups.contains(group)) return false;
        groups.remove(group.name);
        _groups.remove(group);
        return true;
    }

    @Override
    public boolean isAllowed(String permission) {
        for (com.buildtheearth_atchli.thiccperms.Group group : _groups)
            if (group.isAllowed(permission)) return true;
        return super.isAllowed(permission);
    }

    @Override
    public boolean isDenied(String permission) {
        for (com.buildtheearth_atchli.thiccperms.Group group : _groups)
            if (group.isDenied(permission)) return true;
        return super.isDenied(permission);
    }

    @Override
    public boolean isAll_non_op() {
        return false;
    }
}
