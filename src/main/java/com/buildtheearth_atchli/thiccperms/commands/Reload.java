package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

public class Reload extends BaseCommand {

    public Reload() {
        super(CommandManager.reload);
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        com.buildtheearth_atchli.thiccperms.ThutPerms.loadPerms();
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._server = server;
        // Reload player names, to apply the tags if they exist
        for (EntityPlayer player : server.getPlayerList().getPlayers()) {
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._manager.createPlayer(player);
            player.refreshDisplayName();
        }
        sender.sendMessage(new TextComponentString("Reloaded Permissions from File"));
    }

}
