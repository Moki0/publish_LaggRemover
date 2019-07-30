package cn.mokier.soullaggremover;

import cn.mokier.soullaggremover.Utils.Chat;
import cn.mokier.soullaggremover.clearhostile.ClearHostiles;
import cn.mokier.soullaggremover.clearitem.ClearItems;
import cn.mokier.soullaggremover.commands.CommandManager;
import cn.mokier.soullaggremover.configurations.Config;
import cn.mokier.soullaggremover.configurations.Lang;
import com.google.inject.Inject;
import lombok.Getter;
import ninja.leaping.configurate.ConfigurationNode;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

import java.nio.file.Path;

@Plugin(
        id = "soullaggremover",
        name = "SoulLaggRemover",
        version = "1.0-SNAPSHOT"
)
@Getter
public class SoulLaggRemover {

    private Path path;
    private Logger logger;
    private Config config;
    private Lang lang;

    private CommandManager commandManager;
    private ClearItems clearItems;
    private ClearHostiles clearHostiles;

    @Inject
    public SoulLaggRemover(@ConfigDir(sharedRoot = false) Path path, Logger logger) {
        this.path = path;
        this.logger = logger;
    }

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        config = new Config(this);
        lang = new Lang(this);
        commandManager = new CommandManager(this);

        enable();

        Chat.initialization(logger, lang.getConfigurationNode());
    }

    /**
     * 启动独立模块
     */
    private void enable() {
        ConfigurationNode configurationNode = config.getConfigurationNode().getNode("enables");

        if(configurationNode.getNode("clearItems").getBoolean()) {
            clearItems = new ClearItems(this);
        }
        if(configurationNode.getNode("clearHostiles").getBoolean()) {
            clearHostiles = new ClearHostiles(this);
        }
    }

    /**
     * 重载配置文件
     */
    public void reloadConfig() {
        config.reload();
        lang.reload();
        clearItems.reloadConfig();
        clearHostiles.reloadConfig();
    }
}
