package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.ThutPerms;
import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.server.permission.DefaultPermissionLevel;

public class AddGroup extends BaseCommand {
    public AddGroup() {
        super(CommandManager.addGroup);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return super.getUsage(sender) + " <groupname>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String groupName = args[0];
        com.buildtheearth_atchli.thiccperms.Group g = ThutPerms.getGroup(groupName);
        if (g != null) {
            throw new CommandException("Error, Group already exists, cannot create again.");
        }
        g = ThutPerms.addGroup(groupName);
        g.setAll(false);
        g._init = false;
        for (String node : ThutPerms.manager.getRegisteredNodes()) {
            if (ThutPerms.manager.getDefaultPermissionLevel(node) == DefaultPermissionLevel.ALL) {
                g.getAllowedCommands().add(node);
            }
        }
        ThutPerms.savePerms();
        sender.sendMessage(new TextComponentString("Created group " + groupName));
    }

}
