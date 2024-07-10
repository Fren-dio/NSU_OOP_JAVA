package factory.model.threadpool;

public class ThreadPool {
    private final BlockingQueue<Runnable> taskQueue = new BlockingQueue<>();

    public ThreadPool(int numWorkingThreads) {
        for (int i = 0; i < numWorkingThreads; i++) {
            Thread workingThread = new Thread(() -> {
                while (true) {
                    Runnable task = taskQueue.get();
                    if (task == null)
                        return;
                    task.run();
                }
            });
            workingThread.start();
        }
    }

    public void submit(Runnable task) {
        taskQueue.put(task);
    }
}
