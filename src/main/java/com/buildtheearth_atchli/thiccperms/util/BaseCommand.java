package com.buildtheearth_atchli.thiccperms.util;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public abstract class BaseCommand extends CommandBase {
    private final String name;
    List<String> aliases = Lists.newArrayList();

    public BaseCommand(String name) {
        this.name = name;
        aliases.add(name);
        if (!aliases.contains(getName().toLowerCase(Locale.ENGLISH))) {
            aliases.add(getName().toLowerCase(Locale.ENGLISH));
        }
    }

    public static GameProfile getProfile(MinecraftServer server, UUID id) {
        GameProfile profile = null;
        // First check profile cache.
        if (id != null) profile = server.getPlayerProfileCache().getProfileByUUID(id);
        if (profile == null) profile = new GameProfile(id, null);

        // Try to fill profile via secure method.
        profile = server.getMinecraftSessionService().fillProfileProperties(profile, true);
        return profile;
    }

    public static GameProfile getProfile(MinecraftServer server, String arg) {
        UUID id = null;
        String name = null;

        // First check if arg is a UUID
        try {
            id = UUID.fromString(arg);
        } catch (Exception e) {
            // If not a UUID, arg is the name.
            name = arg;
        }

        GameProfile profile = null;

        // First check profile cache.
        if (id != null) profile = server.getPlayerProfileCache().getProfileByUUID(id);
        if (profile == null) profile = new GameProfile(id, name);

        // Try to fill profile via secure method.
        profile = server.getMinecraftSessionService().fillProfileProperties(profile, true);
        return profile;
    }

    /**
     * Return the required permission level for this command.
     */
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/" + getName();
    }

    @Override
    public List<String> getAliases() {
        return aliases;
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args,
                                          @Nullable BlockPos pos) {
        int last = args.length - 1;
        if (last >= 0 && isUsernameIndex(args,
                last)) {
            return getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames());
        }
        return Collections.emptyList();
    }
}
