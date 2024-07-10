package factory.model.suppliers;

import factory.model.products.Engine;
import factory.model.storage.EngineStock;

public class EngineSupplier extends Supplier<Engine> {
    public EngineSupplier(int supplyTimeout, EngineStock stock) {
        super(supplyTimeout, stock);
    }

    @Override
    public Engine produce() {
        return new Engine();
    }
}
