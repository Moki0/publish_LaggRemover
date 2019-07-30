package cn.mokier.soullaggremover.clearhostile;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearHostiles {

    private SoulLaggRemover instance;
    private RunnableClear runnableClear;
    private SettingsNode settingsNode;

    public ClearHostiles(SoulLaggRemover instance) {
        this.instance = instance;
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearHostiles"));
        runnableClear = new RunnableClear(this);

        Task.Builder builder = Sponge.getScheduler().createTaskBuilder();
        builder.execute(runnableClear)
                .async()
                .delay(settingsNode.getInterval(),TimeUnit.MINUTES)
                .interval(settingsNode.getInterval(), TimeUnit.MINUTES)
                .name("SoulLaggRemover hostile clear")
                .submit(instance);
    }

    public int clear() {
        List<Entity> clearEntity = new ArrayList<>();

        //检测需要被清理的生物
        for(World world : settingsNode.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(entity instanceof Hostile && !isWhiteList(entity.getType().getId())) {
                    clearEntity.add(entity);
                }
            }
        }
        //检测是否大于限制
        if(clearEntity.size()>settingsNode.getMaxLimit()) {
            for(Entity entity : clearEntity) {
                entity.remove();
            }
            Chat.sendBroadcast("clearHostiles.clearHostiles", true, "{number}", ""+clearEntity.size());
            return clearEntity.size();
        }

        return 0;
    }

    public void reloadConfig() {
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearHostiles"));
    }

    private boolean isWhiteList(String type) {
        for(String key : settingsNode.getWhiteList()) {
            if(key.contains("[all]")) {
                if(type.equalsIgnoreCase(key.split("\\[all]")[1])) {
                    return true;
                }
            }else if(key.contains("[contains]")) {
                if(type.contains(key.split("\\[contains]")[1])) {
                    return true;
                }
            }
        }

        return false;
    }
}
