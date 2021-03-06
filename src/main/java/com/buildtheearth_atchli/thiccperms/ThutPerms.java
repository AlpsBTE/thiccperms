package com.buildtheearth_atchli.thiccperms;

import com.buildtheearth_atchli.thiccperms.commands.CommandManager;
import com.buildtheearth_atchli.thiccperms.util.SpawnProtector;
import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.PlayerList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.server.permission.PermissionAPI;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

@Mod(
        modid = com.buildtheearth_atchli.thiccperms.ThutPerms.MODID,
        name = "Thut Permissions",
        version = com.buildtheearth_atchli.thiccperms.ThutPerms.VERSION,
        dependencies = "after:worldedit",
        updateJSON = com.buildtheearth_atchli.thiccperms.ThutPerms.UPDATEURL,
        acceptableRemoteVersions = "*",
        acceptedMinecraftVersions = com.buildtheearth_atchli.thiccperms.ThutPerms.MCVERSIONS
)
public class ThutPerms {
    public static final String MODID = com.buildtheearth_atchli.thiccperms.Reference.MODID;
    public static final String VERSION = com.buildtheearth_atchli.thiccperms.Reference.VERSION;
    public static final String UPDATEURL = "";

    public final static String MCVERSIONS = "[1.9.4, 1.13]";
    public static final PermissionsManager manager = new PermissionsManager();
    public static boolean allCommandUse = false;
    public static File configFile = null;
    public static File jsonFile_groups = null;
    public static File folder_players = null;
    public static Logger logger = Logger.getLogger(MODID);
    public static boolean debug = false;
    public static Map<String, String> customCommandPerms = Maps.newHashMap();
    public static boolean wrapOpCommands = true;

    static ExclusionStrategy exclusion = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes f) {
            String name = f.getName();
            return name.startsWith("_");
        }

        @Override
        public boolean shouldSkipClass(Class<?> clazz) {
            return clazz.getName().contains("net.minecraft");
        }
    };

    public ThutPerms() {
        initLogger();
    }

    public static void setAnyCommandUse(MinecraftServer server, boolean enable) {
        Field f = ReflectionHelper.findField(PlayerList.class, "commandsAllowedForAll", "field_72407_n", "t");
        f.setAccessible(true);
        try {
            f.set(server.getPlayerList(), enable);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static void loadPerms() {
        if (jsonFile_groups.exists()) {
            String json = null;
            try {
                Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(exclusion).setPrettyPrinting()
                        .create();
                json = FileUtils.readFileToString(jsonFile_groups, "UTF-8");
                com.buildtheearth_atchli.thiccperms.GroupManager.set_instance(gson.fromJson(json, com.buildtheearth_atchli.thiccperms.GroupManager.class));
                com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().init();
                savePerms();
            } catch (Throwable e) {
                e.printStackTrace();
                System.err.println("\n" + json);
                System.out.println(e);
            }
            return;
        }

        // Legacy load from old location.
        String folder = FMLCommonHandler.instance().getMinecraftServerInstance().getFolderName();
        File file = FMLCommonHandler.instance().getSavesDirectory();
        File saveFolder = new File(file, folder);
        File permsFolder = new File(saveFolder, "permissions");
        if (permsFolder.exists()) {
            File permsFile = new File(permsFolder, "permissions.json");
            if (permsFile.exists()) {
                try {
                    Gson gson = new GsonBuilder().addDeserializationExclusionStrategy(exclusion).setPrettyPrinting()
                            .create();
                    String json = FileUtils.readFileToString(permsFile, "UTF-8");
                    com.buildtheearth_atchli.thiccperms.GroupManager.set_instance(gson.fromJson(json, com.buildtheearth_atchli.thiccperms.GroupManager.class));
                    com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().init();
                    savePerms();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            com.buildtheearth_atchli.thiccperms.GroupManager.set_instance(new com.buildtheearth_atchli.thiccperms.GroupManager());
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().init();
            savePerms();
        }
    }

    public static void savePerms() {
        try {
            Gson gson = new GsonBuilder().addSerializationExclusionStrategy(exclusion).setPrettyPrinting().create();
            Predicate<String> nonnull = new Predicate<String>() {
                @Override
                public boolean test(String t) {
                    return t == null || t.isEmpty();
                }
            };
            for (com.buildtheearth_atchli.thiccperms.Group group : com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().groups) {
                group.allowedCommands.removeIf(nonnull);
                group.bannedCommands.removeIf(nonnull);
                if (!group.allowedCommands.isEmpty()) Collections.sort(group.allowedCommands);
                if (!group.bannedCommands.isEmpty()) Collections.sort(group.bannedCommands);
            }
            FileUtils.writeStringToFile(jsonFile_groups, gson.toJson(com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static com.buildtheearth_atchli.thiccperms.Group addGroup(String name) {
        com.buildtheearth_atchli.thiccperms.Group ret = new com.buildtheearth_atchli.thiccperms.Group(name);
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._groupNameMap.put(name, ret);
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().groups.add(ret);
        return ret;
    }

    public static void addToGroup(UUID id, String name) {
        com.buildtheearth_atchli.thiccperms.Group group = com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._groupNameMap.get(name);
        // Remove from all other groups first.
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial.members.remove(id);
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods.members.remove(id);
        for (com.buildtheearth_atchli.thiccperms.Group old : com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().groups)
            old.members.remove(id);
        if (group != null) {
            group.members.add(id);
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._groupIDMap.put(id, group);
        }
    }

    public static com.buildtheearth_atchli.thiccperms.Group getGroup(String name) {
        if (name.equals(com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial.name))
            return com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial;
        if (name.equals(com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods.name))
            return com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods;
        return com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._groupNameMap.get(name);
    }

    private void initLogger() {
        FileHandler logHandler = null;
        logger.setLevel(Level.ALL);
        try {
            File logs = new File("." + File.separator + "logs");
            logs.mkdirs();
            File logfile = new File(logs, MODID + ".log");
            if ((logfile.exists() || logfile.createNewFile()) && logfile.canWrite() && logHandler == null) {
                logHandler = new FileHandler(logfile.getPath());
                logHandler.setFormatter(new com.buildtheearth_atchli.thiccperms.LogFormatter());
                logger.addHandler(logHandler);
            }
        } catch (SecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        Configuration config = new Configuration(configFile = e.getSuggestedConfigurationFile());
        File folder = new File(configFile.getParentFile(), "thutperms");
        jsonFile_groups = new File(folder, "thutperms.json");
        folder_players = new File(folder, "players");
        folder_players.mkdirs();
        config.load();
        allCommandUse = config.getBoolean("allCommandUse", Configuration.CATEGORY_GENERAL, false,
                "Can any player use OP commands if their group is allowed to?");
        wrapOpCommands = config.getBoolean("wrapOpCommands", Configuration.CATEGORY_GENERAL, wrapOpCommands,
                "Should any OP command be wrapped with a non-op variant for permissions use?");
        String[] custom = config.getStringList("customCommandPerms", Configuration.CATEGORY_GENERAL,
                new String[]{"give:minecraft.command.give"},
                "Custom mappings for permissions, the default shows an example for the give command");
        manager.SPDiabled = config.getBoolean("singleplayerdisabled", Configuration.CATEGORY_GENERAL, true,
                "does this not do anything single player");
        for (String s : custom) {
            String[] args = s.split(":");
            customCommandPerms.put(args[0], args[1]);
        }

        for (Field f : CommandManager.class.getDeclaredFields()) {
            try {
                String value = (String) f.get(null);
                String name = f.getName();
                f.set(config.getString(name, Configuration.CATEGORY_GENERAL, value, "Name for " + name + " command"),
                        value);
            } catch (Exception e1) {
                com.buildtheearth_atchli.thiccperms.ThutPerms.logger.log(Level.SEVERE, "Error with " + f.getName(), e1);
            }
        }

        config.save();
        if (e.getSide() == Side.CLIENT && manager.SPDiabled) {
            if (debug) logger.log(Level.INFO, "Disabling on client side as set by config. (preinit)");
            return;
        }
        MinecraftForge.EVENT_BUS.register(new SpawnProtector());
        PermissionAPI.setPermissionHandler(manager);
        MinecraftForge.EVENT_BUS.register(manager);
    }

    @Optional.Method(modid = "thutessentials")
    @EventHandler
    public void thutEssentialsCompat(FMLPreInitializationEvent e) {
        if (e.getSide() == Side.CLIENT && manager.SPDiabled) {
            if (debug) logger.log(Level.INFO, "Disabled on client side as set by config. (tecompat preinit)");
            return;
        }
        MinecraftForge.EVENT_BUS.register(new com.buildtheearth_atchli.thiccperms.ThutEssentialsCompat());
    }

    @EventHandler
    public void serverLoad(FMLServerStartedEvent event) {
        if (event.getSide() == Side.CLIENT && manager.SPDiabled) {
            if (debug) logger.log(Level.INFO, "Disabled on client side as set by config. (serverstarted)");
            return;
        }
        manager.onServerStarted(event);
        loadPerms();
        if (com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial == null) {
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().initial = new com.buildtheearth_atchli.thiccperms.Group("default");
            savePerms();
        }
        if (com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods == null) {
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods = new com.buildtheearth_atchli.thiccperms.Group("mods");
            com.buildtheearth_atchli.thiccperms.GroupManager.get_instance().mods.setAll(true);
            savePerms();
        }
        com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._server = FMLCommonHandler.instance().getMinecraftServerInstance();
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        if (event.getSide() == Side.CLIENT && manager.SPDiabled) {
            if (debug) logger.log(Level.INFO, "Disabled on client side as set by config. (server starting)");
            return;
        }
        new CommandManager(event);
        MinecraftForge.EVENT_BUS.register(this);
        com.buildtheearth_atchli.thiccperms.ThutPerms.setAnyCommandUse(event.getServer(), allCommandUse);
    }

    @SubscribeEvent
    void logIn(PlayerLoggedInEvent event) {
        com.buildtheearth_atchli.thiccperms.PlayerManager manager = com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._manager;
        manager.createPlayer(event.player);
    }

    @SubscribeEvent
    void logOut(PlayerLoggedInEvent event) {
        com.buildtheearth_atchli.thiccperms.PlayerManager manager = com.buildtheearth_atchli.thiccperms.GroupManager.get_instance()._manager;
        manager.unloadPlayer(event.player);
    }
}
