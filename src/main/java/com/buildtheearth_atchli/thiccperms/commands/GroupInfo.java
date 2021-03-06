package com.buildtheearth_atchli.thiccperms.commands;

import com.buildtheearth_atchli.thiccperms.Group;
import com.buildtheearth_atchli.thiccperms.GroupManager;
import com.buildtheearth_atchli.thiccperms.ThutPerms;
import com.buildtheearth_atchli.thiccperms.util.BaseCommand;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.UUID;

public class GroupInfo extends BaseCommand {

    public GroupInfo() {
        super(CommandManager.groupInfo);
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return super.getUsage(sender) + "<player|exists|hasPerms|members|groups|listCommands|perms> <arguments>";
    }

    /**
     * Return whether the specified command parameter index is a username
     * parameter.
     */
    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        if (args[0].equalsIgnoreCase("player")) return index == 1;
        return false;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) throw new CommandException(getUsage(sender));
        if (args[0].equalsIgnoreCase("player")) {
            String playerName = args[1];
            UUID id = null;
            GameProfile profile = null;
            try {
                EntityPlayer test = getPlayer(server, sender, playerName);
                id = test.getUniqueID();
                profile = test.getGameProfile();
            } catch (Exception e1) {
                try {
                    id = UUID.fromString(playerName);
                } catch (Exception e) {
                }
                profile = new GameProfile(id, playerName);
                profile = TileEntitySkull.updateGameprofile(profile);
            }
            if (profile.getId() == null) {
                throw new CommandException("Error, cannot find profile for " + playerName);
            }
            Group current = GroupManager.get_instance().getPlayerGroup(profile.getId());
            if (current == null) sender.sendMessage(new TextComponentString(playerName + " is not in a group"));
            else sender.sendMessage(new TextComponentString(playerName + " is currently in " + current.name));
            return;
        } else if (args[0].equalsIgnoreCase("exists")) {
            String groupName = args[1];
            Group g = ThutPerms.getGroup(groupName);
            if (g != null) sender.sendMessage(new TextComponentString("Group " + groupName + " exists."));
            else sender.sendMessage(new TextComponentString("Group " + groupName + "does not exist."));
            return;
        } else if (args[0].equalsIgnoreCase("hasPerms")) {
            String groupName = args[1];
            String perm = args[2];
            Group g = ThutPerms.getGroup(groupName);
            if (g == null) {
                throw new CommandException("Error, specified Group does not exist.");
            }
            if (g.hasPermission(perm))
                sender.sendMessage(new TextComponentString("Group " + groupName + " can use " + perm));
            else sender.sendMessage(new TextComponentString("Group " + groupName + " can not use " + perm));
            return;
        } else if (args[0].equalsIgnoreCase("members")) {
            String groupName = args[1];
            Group g = ThutPerms.getGroup(groupName);
            if (g == null) {
                throw new CommandException("Error, specified Group does not exist.");
            }
            sender.sendMessage(new TextComponentString("Members of Group " + groupName));
            for (UUID id : g.members) {
                GameProfile profile = getProfile(server, id);
                sender.sendMessage(new TextComponentString(profile.getName()));
            }
            return;
        } else if (args[0].equalsIgnoreCase("groups")) {
            sender.sendMessage(new TextComponentString("List of existing Groups:"));
            sender.sendMessage(new TextComponentString(GroupManager.get_instance().initial.name));
            sender.sendMessage(new TextComponentString(GroupManager.get_instance().mods.name));
            for (Group g : GroupManager.get_instance().groups) {
                sender.sendMessage(new TextComponentString(g.name));
            }
            return;
        } else if (args[0].equalsIgnoreCase("ListCommands")) {
            sender.sendMessage(new TextComponentString("List of existing commands:"));
            for (ICommand command : FMLCommonHandler.instance().getMinecraftServerInstance().getCommandManager()
                    .getCommands().values()) {
                String name = command.getName();
                sender.sendMessage(new TextComponentString(name + "->" + command.getClass().getName()));
            }
            return;
        } else if (args[0].equalsIgnoreCase("perms")) {
            String groupName = args[1];
            Group g = ThutPerms.getGroup(groupName);
            if (g == null) {
                throw new CommandException("Error, specified Group does not exist.");
            }
            sender.sendMessage(new TextComponentString("List of allowed commands:"));
            for (String s : g.getAllowedCommands()) {
                sender.sendMessage(new TextComponentString(s));
            }
            sender.sendMessage(new TextComponentString("all set to: " + g.isAll()));
            return;
        }
        throw new CommandException(getUsage(sender));
    }

}
