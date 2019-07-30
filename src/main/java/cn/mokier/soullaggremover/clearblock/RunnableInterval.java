package cn.mokier.soullaggremover.clearblock;

class RunnableInterval implements Runnable {

    private ClearBlock manager;

    public RunnableInterval(ClearBlock manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.interval();
    }
}
