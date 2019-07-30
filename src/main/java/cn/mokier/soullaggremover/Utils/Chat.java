package cn.mokier.soullaggremover.Utils;

import com.google.common.reflect.TypeToken;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.text.serializer.TextSerializers;

public class Chat {

  private static Logger logger;
  private static ConfigurationNode configurationNode;

  public static void initialization(Logger logger, ConfigurationNode configurationNode) {
    Chat.logger = logger;
    Chat.configurationNode = configurationNode;
  }

  private static void sendMessage(CommandSource source, String msg, boolean isFilter, String... replaces) {
    String message = isFilter ? Utils.filterVar(msg, replaces) : msg;
    source.sendMessage(TextSerializers.FORMATTING_CODE.deserialize(message));
  }

  public static void sendLangMessage(CommandSource source, String node, boolean isFilter, String... replaces) {
    sendMessage(source, toConfigurationNode(node).getString(), isFilter, replaces);
  }

  public static void sendLangMessageList(CommandSource source, String node, boolean isFilter, String... replaces) throws ObjectMappingException {
    for(String msg : toConfigurationNode(node).getList(TypeToken.of(String.class))) {
      sendMessage(source, msg, isFilter, replaces);
    }
  }

  public static void sendBroadcast(String node, boolean isFilter, String... replaces) {
    String msg = toConfigurationNode(node).getString();
    String message = isFilter ? Utils.filterVar(msg, replaces) : msg;
    Sponge.getServer().getBroadcastChannel().send(TextSerializers.FORMATTING_CODE.deserialize(message));
  }

  public static void sendLangLoggerInfo(String node, boolean isFilter, String... replaces) {
    String msg = toConfigurationNode(node).getString();
    String message = isFilter ? Utils.filterVar(msg, replaces) : msg;
    logger.info(message.replace("&", "§"));
  }

  public static void sendLangLoggerError(String node, boolean isFilter, String... replaces) {
    String msg = toConfigurationNode(node).getString();
    String message = isFilter ? Utils.filterVar(msg, replaces) : msg;
    logger.error(message.replace("&", "§"));
  }

  private static ConfigurationNode toConfigurationNode(String node) {
    ConfigurationNode newNode = configurationNode;
    for(String n : node.split("\\.")) {
      newNode = newNode.getNode(n);
    }
    return newNode;
  }

}
