package cn.mokier.soullaggremover.commands;

import cn.mokier.soullaggremover.Utils.Chat;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;


class Help implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try {
            Chat.sendLangMessageList(src, "commands.help", false);
        } catch (ObjectMappingException e) {
            Chat.sendLangLoggerError("commands.error-lang-help", false);
        }
        return CommandResult.success();
    }

}
