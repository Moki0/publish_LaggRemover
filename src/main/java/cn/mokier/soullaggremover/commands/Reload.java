package cn.mokier.soullaggremover.commands;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

class Reload implements CommandExecutor {

    private SoulLaggRemover instance;

    public Reload(SoulLaggRemover instance) {
        this.instance = instance;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        instance.reloadConfig();
        Chat.sendLangMessage(src, "commands.reload", false);
        return CommandResult.success();
    }

}
