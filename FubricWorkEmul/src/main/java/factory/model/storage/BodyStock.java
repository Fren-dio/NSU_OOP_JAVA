package factory.model.storage;

import factory.model.products.Body;

public class BodyStock extends AbstractStock<Body> {
    public BodyStock(int capacity) {
        super(capacity);
        this.id = StockId.BODY;
    }
}
