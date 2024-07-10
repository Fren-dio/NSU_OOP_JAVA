package factory.model.storage;

import factory.model.products.AbstractProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class AbstractStock<T extends AbstractProduct> {
    protected StockId id;
    protected final int capacity;
    protected final Stack<T> storedItems;

    protected final List<StockListener> listeners = new ArrayList<>();

    protected AbstractStock(int capacity) {
        this.capacity = capacity;
        storedItems = new Stack<>();
    }

    public void addListener(StockListener listener) {
        listeners.add(listener);
    }

    synchronized public void add(T item) {
        while (isFull()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return;
            }
        }
        storedItems.add(item);
        notify();

        for (StockListener listener : listeners) {
            listener.numStoredUpdated(storedItems.size(), id);
            listener.newItem(id);
        }
    }

    synchronized public T get() {
        while (isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }
        T item = storedItems.pop();
        notify();

        for (StockListener listener : listeners)
            listener.numStoredUpdated(storedItems.size(), id);

        return item;
    }

    public boolean isFull() {
        return storedItems.size() == capacity;
    }

    public boolean isEmpty() {
        return storedItems.isEmpty();
    }

    public int getNumStored() {
        return storedItems.size();
    }

    public int getCapacity() {
        return capacity;
    }
}
