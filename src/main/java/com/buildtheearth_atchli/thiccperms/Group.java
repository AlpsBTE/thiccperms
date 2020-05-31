package com.buildtheearth_atchli.thiccperms;

import com.google.common.collect.Sets;

import java.util.Set;
import java.util.UUID;

public class Group extends com.buildtheearth_atchli.thiccperms.PermissionsHolder {
    public String name;
    public String prefix = "";
    public String suffix = "";
    public Set<UUID> members = Sets.newHashSet();

    public Group(String name) {
        this.name = name;
    }
}
