package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class CopyGroup extends BaseCommand {

    public CopyGroup() {
        super(CommandManager.copyGroup);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return super.getUsage(sender) + " <from> <to>";
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        String groupFrom = args[0];
        com.buildtheearth_atchli.thiccperms.Group gFrom = com.buildtheearth_atchli.thiccperms.ThutPerms.getGroup(groupFrom);
        if (gFrom == null) {
            throw new CommandException("Error, specified Group " + groupFrom + " does not exist.");
        }
        String groupTo = args[1];
        boolean replace = true;
        if (args.length == 3 && args[2].equals("add")) replace = false;
        com.buildtheearth_atchli.thiccperms.Group gTo = com.buildtheearth_atchli.thiccperms.ThutPerms.getGroup(groupTo);
        if (gTo == null) {
            throw new CommandException("Error, specified Group " + groupTo + " does not exist.");
        }
        if (replace) {
            gTo.getAllowedCommands().clear();
            gTo.getBannedCommands().clear();
        }

        gTo.setAll(gFrom.isAll());
        gTo._init = false;
        gTo.getAllowedCommands().addAll(gFrom.getAllowedCommands());
        gTo.getBannedCommands().addAll(gFrom.getBannedCommands());
        com.buildtheearth_atchli.thiccperms.ThutPerms.savePerms();
        sender.sendMessage(new TextComponentString("Copied from " + groupFrom + " to " + groupTo));
    }

}
