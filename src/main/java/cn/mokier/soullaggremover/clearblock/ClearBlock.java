package cn.mokier.soullaggremover.clearblock;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import com.flowpowered.math.vector.Vector3i;
import lombok.Getter;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.World;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
public class ClearBlock {

    private SoulLaggRemover instance;
    private RunnableInterval runnableInterval;
    private SettingsNode settingsNode;

    private Map<BlockSnapshot, Integer> changeMap;

    public ClearBlock(SoulLaggRemover instance) {
        this.instance = instance;
        changeMap = new HashMap<>();
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearBlock"));
        runnableInterval = new RunnableInterval(this);

        Task.Builder builder = Sponge.getScheduler().createTaskBuilder();
        builder.execute(runnableInterval)
                .async()
                .delay(settingsNode.getInterval(),TimeUnit.SECONDS)
                .interval(settingsNode.getInterval(), TimeUnit.SECONDS)
                .name("SoulLaggRemover block clear")
                .submit(instance);

        Sponge.getEventManager().registerListeners(instance, new BlockChangeListener(this));
    }

    public void reloadConfig() {
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearBlock"));
    }

    public void addChange(BlockSnapshot blockSnapshot) {
        int change = changeMap.getOrDefault(blockSnapshot, 0);

        //判断是否符合世界
        World blockWorld = blockSnapshot.getLocation().get().getExtent();
        if(settingsNode.getWorlds().contains(blockWorld)) {
            if(isClearByClearBlock(blockSnapshot)) {
                if(++change<settingsNode.getMaxChange()) {
                    changeMap.put(blockSnapshot, change);
                }else {
                    Vector3i vector3i = blockSnapshot.getPosition();
                    blockWorld.setBlockType(blockSnapshot.getPosition(), BlockTypes.AIR);
                    changeMap.remove(blockSnapshot);
                    Chat.sendBroadcast("clearBlock.clearBlock", true,
                            "{world}", blockWorld.getName(),
                            "{x}", ""+vector3i.getX(),
                            "{y}", ""+vector3i.getY(),
                            "{z}", ""+vector3i.getZ());
                }
            }
        }
    }

    public void interval() {
        changeMap = new HashMap<>();
    }


    private boolean isClearByClearBlock(BlockSnapshot blockSnapshot) {
        String type = blockSnapshot.getState().getType().getId();
        for(String key : settingsNode.getClearBlock()) {
            if(key.contains("[all]")) {
                if(type.equalsIgnoreCase(key.split("\\[all]")[1])) {
                    return false;
                }
            }else if(key.contains("[contains]")) {
                if(type.contains(key.split("\\[contains]")[1])) {
                    return false;
                }
            }
        }

        return true;
    }

}
