package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class PlayerInfo extends BaseCommand {

    public PlayerInfo() {
        super(CommandManager.playerInfo);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return super.getUsage(sender) + " <player> <arguments>";
    }

    /**
     * Return whether the specified command parameter index is a username
     * parameter.
     */
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return index == 0;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        // TODO Auto-generated method stub

    }

}
