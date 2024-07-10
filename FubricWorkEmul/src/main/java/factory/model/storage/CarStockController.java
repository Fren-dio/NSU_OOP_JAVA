package factory.model.storage;

import factory.model.assembly.AssemblyLine;
import factory.model.assembly.AssemblyLineListener;
import factory.model.products.Car;

import java.util.ArrayList;
import java.util.List;

public class CarStockController implements AssemblyLineListener, CarStockListener { // отвечает за запросы на создание новых машин
    private final AssemblyLine assemblyLine;
    private int numPendingRequests;

    private final List<CarStockControllerListener> listeners = new ArrayList<>();

    public CarStockController(int capacity, AssemblyLine assemblyLine) {
        this.assemblyLine = assemblyLine;
        assemblyLine.setListener(this); // лисенер сборщиков - наш контроллер
        placeRequests(capacity); // запросы на создание новых машин
    }

    public void addListener(CarStockControllerListener listener) {
        listeners.add(listener);
    }

    synchronized private void placeRequests(int numRequests) {
        for (int i = 0; i < numRequests; i++) {
            assemblyLine.requestCarProduction();
            numPendingRequests++;
        }

        for (CarStockControllerListener listener : listeners)
            listener.pendingUpdated(numPendingRequests);
    }

    @Override
    synchronized public void produced() {
        numPendingRequests--;
        for (CarStockControllerListener listener : listeners)
            listener.pendingUpdated(numPendingRequests);
    }

    @Override
    public void requestCar() {
        placeRequests(1);
    }
}
