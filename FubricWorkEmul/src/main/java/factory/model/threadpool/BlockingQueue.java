package factory.model.threadpool;

import java.util.ArrayList;
import java.util.List;

public class BlockingQueue<T> {
    private final List<T> tasks = new ArrayList<>();

    synchronized public T get() {
        while (tasks.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        T task = tasks.get(0);
        tasks.remove(task);
        return task;
    }

    synchronized public void put(T task) {
        tasks.add(task);
        notify();
    }
}
