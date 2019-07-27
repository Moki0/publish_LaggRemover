package cn.mokier.soullaggremover;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;

@Plugin(
        id = "soullaggremover",
        name = "SoulLaggRemover",
        version = "1.0-SNAPSHOT"
)
public class SoulLaggRemover {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
    }
}
