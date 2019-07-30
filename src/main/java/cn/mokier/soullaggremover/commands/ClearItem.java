package cn.mokier.soullaggremover.commands;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import cn.mokier.soullaggremover.clearitem.ClearItems;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

class ClearItem implements CommandExecutor {

    private SoulLaggRemover instance;

    public ClearItem(SoulLaggRemover instance) {
        this.instance = instance;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        ClearItems clearItems = instance.getClearItems();
        if(clearItems !=null) {
            clearItems.stateClearWarning();
        }else {
            Chat.sendLangMessage(src, "commands.disableClearItems", false);
        }

        return CommandResult.success();
    }

}
