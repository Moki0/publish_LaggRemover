package cn.mokier.soullaggremover.clearhostile;

class RunnableClear implements Runnable {

    private ClearHostiles manager;

    public RunnableClear(ClearHostiles manager) {
        this.manager = manager;
    }

    @Override
    public void run() {
        manager.clear();
    }
}
