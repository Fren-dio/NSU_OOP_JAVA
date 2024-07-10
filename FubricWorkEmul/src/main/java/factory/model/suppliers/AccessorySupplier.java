package factory.model.suppliers;

import factory.model.products.Accessory;
import factory.model.storage.AccessoryStock;

public class AccessorySupplier extends Supplier<Accessory> {
    public AccessorySupplier(int supplyTimeout, AccessoryStock stock) {
        super(supplyTimeout, stock);
    }

    @Override
    public Accessory produce() {
        return new Accessory();
    }
}
