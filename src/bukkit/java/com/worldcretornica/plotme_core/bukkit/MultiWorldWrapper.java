package com.worldcretornica.plotme_core.bukkit;

import multiworld.ConfigException;
import multiworld.InvalidWorldGenException;
import multiworld.MultiWorldPlugin;
import multiworld.WorldGenException;
import multiworld.data.DataHandler;
import multiworld.worldgen.WorldGenerator;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

public class MultiWorldWrapper implements Delegate<MultiWorldPlugin> {

    private final MultiWorldPlugin multiWorldPlugin;

    public MultiWorldWrapper(JavaPlugin multiworld) {
        if (!(multiworld instanceof MultiWorldPlugin)) {
            throw new IllegalArgumentException("JavaPlugin must be castable to MultiWorldPlugin");
        }
        multiWorldPlugin = (MultiWorldPlugin) multiworld;
    }

    @Override
    public MultiWorldPlugin getDelegate() {
        return multiWorldPlugin;
    }

    public final boolean isEnabled() {
        return multiWorldPlugin.isEnabled();
    }

    public DataHandlerWrapper getDataManager() {
        return new DataHandlerWrapper(multiWorldPlugin.getDataManager());
    }

    public static class DataHandlerWrapper implements Delegate<DataHandler> {

        private final DataHandler dataHandler;

        private DataHandlerWrapper(DataHandler dataHandler) {
            this.dataHandler = dataHandler;
        }

        @Override
        public DataHandler getDelegate() {
            return dataHandler;
        }

        public boolean makeWorld(String name, WorldGeneratorWrapper env, long seed, String options) throws DelegateClassException {
            try {
                return dataHandler.getWorldManager().makeWorld(name, env.getDelegate(), seed, options);
            } catch (WorldGenException ex) {
                throw new DelegateClassException(ex);
            }
        }

        public void save() throws DelegateClassException {
            try {
                dataHandler.save();
            } catch (ConfigException ex) {
                throw new DelegateClassException(ex);
            }
        }

        public World loadWorld(String name) {
            return dataHandler.getWorldManager().loadWorld(name);
        }

    }

    public static class WorldGeneratorWrapper implements Delegate<WorldGenerator> {

        private final WorldGenerator worldGenerator;

        public WorldGeneratorWrapper(WorldGenerator worldGenerator) {
            this.worldGenerator = worldGenerator;
        }

        public static WorldGeneratorWrapper getGenByName() throws DelegateClassException {
            try {
                return new WorldGeneratorWrapper(WorldGenerator.getGenByName("PLUGIN"));
            } catch (InvalidWorldGenException ex) {
                throw new DelegateClassException(ex);
            }
        }

        @Override
        public WorldGenerator getDelegate() {
            return worldGenerator;
        }

    }

}
