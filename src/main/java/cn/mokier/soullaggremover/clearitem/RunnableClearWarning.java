package cn.mokier.soullaggremover.clearitem;

import cn.mokier.soullaggremover.Utils.Chat;

import java.util.ArrayList;
import java.util.List;

class RunnableClearWarning implements Runnable {

    private ClearItems manager;
    private List<Integer> warning;
    private int second;
    private int bout;

    public RunnableClearWarning(ClearItems manager) {
        this.manager = manager;
        warning = new ArrayList<>();
    }

    public void stateClearWarning() {
        if(warning.isEmpty()) {
            warning = manager.getSettingsNode().getWarning();
            bout = 0;
            second = warning.get(0);
            Chat.sendBroadcast("clearItems.clearItemsWarning", true, "{time}", ""+second);
        }
    }

    @Override
    public void run() {
        if(!warning.isEmpty()) {
            if(warning.size()>bout) {
                second--;
                if(second<=0) {
                    bout++;
                    if(warning.size()>bout) Chat.sendBroadcast("clearItems.clearItemsWarning", true, "{time}", ""+warning.get(bout));
                }
            }else {
                manager.clear();
                warning = new ArrayList<>();
            }
        }
    }

}
