package cn.mokier.soullaggremover.clearitem;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import lombok.Getter;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Getter
public class ClearItems {

    private SoulLaggRemover instance;
    private SettingsNode settingsNode;
    private Task.Builder runnableClear;
    private Task.Builder runnableClearWarning;

    public ClearItems(SoulLaggRemover instance) {
        this.instance = instance;

        runnableClear = Task.builder().execute(new RunnableClear(this));
        runnableClearWarning = Task.builder().execute(new RunnableClearWarning(this));

        reloadConfig();
    }

    public void reloadConfig() {
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearItems"));

        runnableClear.reset();
        runnableClearWarning.reset();

        runnableClear
                .async()
                .delay(settingsNode.getInterval(),TimeUnit.MINUTES)
                .interval(settingsNode.getInterval(), TimeUnit.MINUTES)
                .name("SoulLaggRemover items clear")
                .submit(instance);
        runnableClearWarning
                .async()
                .delay(1,TimeUnit.SECONDS)
                .interval(1, TimeUnit.SECONDS)
                .name("SoulLaggRemover items clear warning")
                .submit(instance);
    }

    public int clear() {
        List<Item> clearItems = new ArrayList<>();

        for(World world : settingsNode.getWorlds()) {
            for(Entity entity : world.getEntities()) {
                if(entity instanceof Item) {
                    Item item = (Item) entity;

                    //检测是否清理特殊物品
                    if(!settingsNode.isClearSpecialItems()) {
                        Optional<Text> name = item.get(Keys.DISPLAY_NAME);
                        Optional<List<Text>> lore = item.get(Keys.ITEM_LORE);
                        if(name.isPresent() || lore.isPresent()) {
                            continue;
                        }
                    }

                    //检测是否是白名单物品
                    if(!isWhiteList(item.getItemType().getId())) {
                        clearItems.add(item);
                        entity.remove();
                    }
                }
            }
        }

        Chat.sendBroadcast("clearItems.clearItems", true, "{number}", ""+clearItems.size());
        return clearItems.size();
    }

    public void stateClearWarning() {
        runnableClearWarning.stateClearWarning();
    }


    private boolean isWhiteList(String type) {
        System.out.println(settingsNode.getWhiteList());
        System.out.println(type);
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
