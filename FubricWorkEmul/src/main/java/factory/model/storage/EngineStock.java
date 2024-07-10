package factory.model.storage;

import factory.model.products.Engine;

public class EngineStock extends AbstractStock<Engine> {
    public EngineStock(int capacity) {
        super(capacity);
        this.id = StockId.ENGINE;
    }
}
