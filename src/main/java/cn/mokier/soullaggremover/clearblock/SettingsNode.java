package cn.mokier.soullaggremover.clearblock;

import cn.mokier.soullaggremover.Utils.Chat;
import com.google.common.reflect.TypeToken;
import lombok.Getter;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
class SettingsNode {

    //检测间隔  单位：秒
    private int interval;
    //在间隔内刷新次数达到这个量后将被清理
    private int maxChange;
    //运行的世界
    private List<World> worlds;
    //清理的方块
    private List<String> clearBlock;

    public SettingsNode(ConfigurationNode configurationNode) {
        interval = configurationNode.getNode("interval").getInt();
        maxChange = configurationNode.getNode("maxChange").getInt();


        try {
            clearBlock =  configurationNode.getNode("clearBlock").getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            Chat.sendLangLoggerError("clearBlock.error-settings-clearBlock", false);
        }

        try {
            worlds = new ArrayList<>();
            List<String> worldNames =  configurationNode.getNode("worlds").getList(TypeToken.of(String.class));

            if(worldNames.contains("all")) {
                worlds.addAll(Sponge.getServer().getWorlds());
            }else {
                for(String name : worldNames) {
                    Optional<World> world = Sponge.getServer().getWorld(name);
                    world.ifPresent(world1 -> worlds.add(world1));
                }
            }
        } catch (ObjectMappingException e) {
            Chat.sendLangLoggerError("clearBlock.error-settings-worlds", false);
        }
    }


}
