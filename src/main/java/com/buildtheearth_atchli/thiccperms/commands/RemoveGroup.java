package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.Group;
import com.buildtheearth_atchli.thiccperms.GroupManager;
import com.buildtheearth_atchli.thiccperms.ThutPerms;
import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class RemoveGroup extends BaseCommand {

    public RemoveGroup() {
        super(CommandManager.removeGroup);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return super.getUsage(sender) + " <name>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String groupName = args[0];
        Group g = ThutPerms.getGroup(groupName);
        if (g == null) throw new CommandException("Error, specified Group does not exist.");
        if (g == GroupManager.get_instance().initial || g == GroupManager.get_instance().mods)
            throw new CommandException("Error, cannot remove default groups.");
        GroupManager.get_instance().groups.remove(g);
        GroupManager.get_instance()._groupNameMap.remove(groupName);
        ThutPerms.savePerms();
        sender.sendMessage(new TextComponentString("Removed group " + groupName));
    }

}
