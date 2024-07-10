package factory.model;

import factory.model.storage.CarStockControllerListener;
import factory.model.storage.StockListener;

public interface ModelListener extends StockListener, CarStockControllerListener {
}
