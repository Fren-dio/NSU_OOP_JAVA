package factory.model.assembly;

import factory.model.products.Car;
import factory.model.storage.AccessoryStock;
import factory.model.storage.BodyStock;
import factory.model.storage.CarStock;
import factory.model.storage.EngineStock;
import factory.model.threadpool.ThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AssemblyLine { // пусть сборщик кладет на склад
    private final AccessoryStock accessoryStock;
    private final BodyStock bodyStock;
    private final EngineStock engineStock;
    private final CarStock carStock;

    private final ThreadPool workersPool;

    private AssemblyLineListener listener;

    public AssemblyLine(AccessoryStock accessoryStock, BodyStock bodyStock,
                        EngineStock engineStock, CarStock carStock, int numWorkers) {
        this.accessoryStock = accessoryStock;
        this.bodyStock = bodyStock;
        this.engineStock = engineStock;
        this.carStock = carStock;
        workersPool = new ThreadPool(numWorkers);
    }

    public void assemble() {
        Car car = new Car(accessoryStock.get(), bodyStock.get(), engineStock.get());
        carStock.add(car);
        if (listener != null)
            listener.produced();
    }

    public void requestCarProduction() {
        workersPool.submit(this::assemble);
    }

    public void setListener(AssemblyLineListener listener) {
        this.listener = listener;
    }
}
