package cn.mokier.soullaggremover.clearitem;

class RunnableClear implements Runnable {

    private ClearItems manager;

    public RunnableClear(ClearItems manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.getRunnableClearWarning().stateClearWarning();
    }
}
