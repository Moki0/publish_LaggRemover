package cn.mokier.soullaggremover.clearitem;

class RunnableClear implements Runnable {

    private ClearItems manager;

    public RunnableClear(ClearItems manager) {
        this.manager = manager;
    }

    @Override
    public void run() {

        System.out.println("1");
        try {
            Thread.sleep(10*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(2);

//        manager.getRunnableClearWarning().stateClearWarning();
    }
}
