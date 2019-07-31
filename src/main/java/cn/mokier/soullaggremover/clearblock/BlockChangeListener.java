package cn.mokier.soullaggremover.clearblock;

import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.data.Transaction;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.ChangeBlockEvent;

public class BlockChangeListener {

    private ClearBlock manager;

    public BlockChangeListener(ClearBlock manager) {
        this.manager = manager;
    }

    @Listener
    public void handle(ChangeBlockEvent.Modify event) {
        for(Transaction<BlockSnapshot> block : event.getTransactions()) {
            BlockSnapshot blockSnapshot = block.getDefault();
            manager.addChange(blockSnapshot);
        }

    }
}
