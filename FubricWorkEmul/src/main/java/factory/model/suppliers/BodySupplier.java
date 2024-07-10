package factory.model.suppliers;

import factory.model.products.Body;
import factory.model.storage.BodyStock;

public class BodySupplier extends Supplier<Body> {
    public BodySupplier(int supplyTimeout, BodyStock stock) {
        super(supplyTimeout, stock);
    }

    @Override
    public Body produce() {
        return new Body();
    }
}
