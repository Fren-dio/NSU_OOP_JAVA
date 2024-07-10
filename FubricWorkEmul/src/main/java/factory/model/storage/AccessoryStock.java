package factory.model.storage;

import factory.model.products.Accessory;

public class AccessoryStock extends AbstractStock<Accessory> {
    public AccessoryStock(int capacity) {
        super(capacity);
        this.id = StockId.ACCESSORY;
    }
}
