package cn.mokier.soullaggremover.configurations;

import cn.mokier.soullaggremover.SoulLaggRemover;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import org.spongepowered.api.Sponge;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Lang {

    private SoulLaggRemover instance;
    private Path path;
    private ConfigurationLoader<CommentedConfigurationNode> configurationLoader;
    private ConfigurationNode configurationNode;

    public Lang(SoulLaggRemover instance) {
        this.instance = instance;
        this.path = instance.getPath();
        reload();
    }

    public void reload() {
        try {
            saveDefaultConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            configurationLoader.save(configurationNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConfigurationNode getConfigurationNode() {
        return configurationNode;
    }

    public void saveDefaultConfig() throws IOException {
        if(!path.toFile().exists()) path.toFile().mkdirs();
        Path pathFile = Paths.get(path+"/lang.conf");

        if(!pathFile.toFile().exists()) {
            Sponge.getAssetManager().getAsset(instance, "lang.conf").get().copyToFile(pathFile, false, true);
        }

        configurationLoader = HoconConfigurationLoader.builder().setPath(pathFile).build();
        configurationNode = configurationLoader.load();
    }


}
