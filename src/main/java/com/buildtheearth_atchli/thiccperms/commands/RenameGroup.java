package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.ThutPerms;
import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import com.google.common.collect.Sets;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import java.util.Set;
import java.util.UUID;

public class RenameGroup extends BaseCommand {
    public RenameGroup() {
        super(CommandManager.renameGroup);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return super.getUsage(sender) + " <oldname> <newname>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String groupName = args[0];
        String newName = args[1];
        com.buildtheearth_atchli.thiccperms.Group g = ThutPerms.getGroup(groupName);
        if (g == null) {
            throw new CommandException("Error, specified Group does not exist.");
        }
        com.buildtheearth_atchli.thiccperms.Group g1 = ThutPerms.getGroup(newName);
        if (g1 != null) {
            throw new CommandException("Error, specified Group already exists.");
        }
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().groups.remove(g);
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._groupNameMap.remove(groupName);
        g1 = ThutPerms.addGroup(newName);
        if (g == com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial) {
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial = g1;
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().groups.remove(g1);
        } else if (g == com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods) {
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods = g1;
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().groups.remove(g1);
        }
        g1.getAllowedCommands().addAll(g.getAllowedCommands());
        g1.getBannedCommands().addAll(g.getBannedCommands());
        Set<UUID> members = Sets.newHashSet(g.members);
        for (UUID id : members) {
            ThutPerms.addToGroup(id, newName);
        }
        ThutPerms.savePerms();
        sender.sendMessage(new TextComponentString("Renamed group " + groupName + " to " + newName));
    }
}
