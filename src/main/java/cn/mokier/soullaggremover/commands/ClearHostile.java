package cn.mokier.soullaggremover.commands;

import cn.mokier.soullaggremover.SoulLaggRemover;
import cn.mokier.soullaggremover.Utils.Chat;
import cn.mokier.soullaggremover.clearhostile.ClearHostiles;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

class ClearHostile implements CommandExecutor {

    private SoulLaggRemover instance;

    public ClearHostile(SoulLaggRemover instance) {
        this.instance = instance;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        ClearHostiles clearHostiles = instance.getClearHostiles();
        if(clearHostiles !=null) {
            if(clearHostiles.clear()==0) {
                Chat.sendLangMessage(src, "commands.notClearHostiles", false);
            }
        }else {
            Chat.sendLangMessage(src, "commands.disableClearHostiles", false);
        }

        return CommandResult.success();
    }

}
