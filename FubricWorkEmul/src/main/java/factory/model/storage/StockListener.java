package factory.model.storage;

public interface StockListener {
    void numStoredUpdated(int newNumStored, StockId stockId);

    void newItem(StockId stockId);
}
