package cn.mokier.soullaggremover.commands;

import cn.mokier.soullaggremover.SoulLaggRemover;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.text.Text;

public class CommandManager {

    private SoulLaggRemover instance;

    public CommandManager(SoulLaggRemover instance) {
        this.instance = instance;

        CommandSpec clearHostile = CommandSpec.builder()
                .description(Text.of("SoulClear command"))
                .permission("soulLaggRemover.clearHostile")
                .executor(new ClearHostile(instance))
                .build();
        CommandSpec clearItem = CommandSpec.builder()
                .description(Text.of("SoulClear command"))
                .permission("soulLaggRemover.clearItem")
                .executor(new ClearItem(instance))
                .build();

        CommandSpec reload = CommandSpec.builder()
                .description(Text.of("SoulClear command"))
                .permission("soulLaggRemover.reload")
                .executor(new Reload(instance))
                .build();

        CommandSpec help = CommandSpec.builder()
                .description(Text.of("SoulClear command"))
                .executor(new Help())
                .child(clearHostile, "clearHostile")
                .child(clearItem, "clearItem")
                .child(reload, "reload")
                .build();

        Sponge.getCommandManager().register(instance, help, "soullaggremover", "slr");
    }

}
