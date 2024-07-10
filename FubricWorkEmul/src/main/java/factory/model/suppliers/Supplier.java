package factory.model.suppliers;

import factory.model.products.AbstractProduct;
import factory.model.storage.AbstractStock;

public abstract class Supplier<T extends AbstractProduct> {
    public final static int DEFAULT_TIMEOUT = 200;
    public final static int MAX_TIMEOUT = 500;
    public final static int MIN_TIMEOUT = 1;

    protected int supplyTimeout;
    protected AbstractStock<T> associatedStock;

    protected Supplier(int supplyTimeout, AbstractStock<T> stock) {
        this.supplyTimeout = supplyTimeout;
        this.associatedStock = stock;
    }

    public abstract T produce();

    public void supplyToStock() {
        while (true) {
            try {
                Thread.sleep(supplyTimeout);
            } catch (InterruptedException e) {
                return;
            }
            T product = produce();
            associatedStock.add(product); // создали продукт, добавили на склад
        }
    }

    public void setSupplyTimeout(int supplyTimeout) {
        this.supplyTimeout = supplyTimeout;
    }
}
