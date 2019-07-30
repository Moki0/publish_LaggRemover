package cn.mokier.soullaggremover.clearhostile;

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

    //检测间隔  单位：分
    private int interval;
    //是否清理带有显示名或者标签的物品
    private boolean isClearSpecialItems;
    //清理需要达到的数量
    private int maxLimit;
    //运行的世界
    private List<World> worlds;
    //清理白名单
    private List<String> whiteList;

    public SettingsNode(ConfigurationNode configurationNode) {
        interval = configurationNode.getNode("interval").getInt();
        isClearSpecialItems = configurationNode.getNode("isClearSpecialItems").getBoolean();
        maxLimit = configurationNode.getNode("maxLimit").getInt();

        try {
            whiteList =  configurationNode.getNode("whiteList").getList(TypeToken.of(String.class));
        } catch (ObjectMappingException e) {
            Chat.sendLangLoggerError("clearHostiles.error-settings-whitelist", false);
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
            Chat.sendLangLoggerError("clearHostiles.error-settings-worlds", false);
        }

    }


}
