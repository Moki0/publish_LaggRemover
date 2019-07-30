package cn.mokier.soullaggremover.clearitem;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import lombok.Getter;
import org.spongepowered.api.Sponge;
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
    private RunnableClear runnableClear;
    private RunnableClearWarning runnableClearWarning;

    public ClearItems(SoulLaggRemover instance) {
        this.instance = instance;
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearItems"));
        runnableClear = new RunnableClear(this);
        runnableClearWarning = new RunnableClearWarning(this);

        Task.Builder builder = Sponge.getScheduler().createTaskBuilder();
        builder.execute(runnableClear)
                .async()
                .delay(settingsNode.getInterval(),TimeUnit.MINUTES)
                .interval(settingsNode.getInterval(), TimeUnit.MINUTES)
                .name("SoulLaggRemover items clear")
                .submit(instance);
        builder.execute(runnableClearWarning)
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

                    //检测是否清理特殊物品  &&  检测是否是白名单物品
                    if(isClearBySpecialItems(item) && isClearByWhiteList(item)) {
                        clearItems.add(item);
                        entity.remove();
                    }

                    /*//检测是否清理特殊物品
                    if(!settingsNode.isClearSpecialItems()) {
                        Optional<Text> name = item.get(Keys.DISPLAY_NAME);
                        Optional<List<Text>> lore = item.get(Keys.ITEM_LORE);
                        if(name.isPresent() || lore.isPresent()) {
                            continue;
                        }
                    }

                    //检测是否是白名单物品
                    if(!isClearByWhiteList(item)) {
                        clearItems.add(item);
                        entity.remove();
                    }*/
                }
            }
        }

        Chat.sendBroadcast("clearItems.clearItems", true, "{number}", ""+clearItems.size());
        return clearItems.size();
    }

    public void stateClearWarning() {
        runnableClearWarning.stateClearWarning();
    }

    public void reloadConfig() {
        settingsNode = new SettingsNode(instance.getConfig().getConfigurationNode().getNode("clearItems"));
    }

    private boolean isClearBySpecialItems(Item item) {
        if(!settingsNode.isClearSpecialItems()) {
            Optional<Text> name = item.get(Keys.DISPLAY_NAME);
            Optional<List<Text>> lore = item.get(Keys.ITEM_LORE);
            return !(name.isPresent() || lore.isPresent());
        }

        return true;
    }

    private boolean isClearByWhiteList(Item item) {
        String type = item.getItemType().getId();
        for(String key : settingsNode.getWhiteList()) {
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
