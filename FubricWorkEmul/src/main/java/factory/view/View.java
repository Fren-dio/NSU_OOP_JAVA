package factory.view;

import factory.controller.*;
import factory.model.ModelListener;
import factory.model.dealers.Dealer;
import factory.model.storage.StockId;
import factory.model.suppliers.Supplier;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame implements ModelListener {
    public final static int WINDOW_WIDTH = 1000;
    public final static int WINDOW_HEIGHT = 600;

    private final CounterLabel accessoryCounter;
    private final CounterLabel bodyCounter;
    private final CounterLabel carCounter;
    private final CounterLabel engineCounter;

    private final CounterLabel accessoryProducedCounter;
    private final CounterLabel bodyProducedCounter;
    private final CounterLabel carProducedCounter;
    private final CounterLabel engineProducedCounter;

    private final CounterLabel pendingCounter;

    public View(FactoryControllerListener factory) {
        super("Car factory");

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(9, 2));
        setResizable(false);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLocationRelativeTo(null);

        TimeoutSlider accessorySlider = new TimeoutSlider(
                Supplier.MIN_TIMEOUT, Supplier.MAX_TIMEOUT, Supplier.DEFAULT_TIMEOUT);
        accessorySlider.addChangeListener(new AccessorySliderListener(factory));
        add(new TextLabel("Интервал поставок аксессуаров"));
        add(accessorySlider);

        TimeoutSlider bodySlider = new TimeoutSlider(
                Supplier.MIN_TIMEOUT, Supplier.MAX_TIMEOUT, Supplier.DEFAULT_TIMEOUT);
        bodySlider.addChangeListener(new BodySliderListener(factory));
        add(new TextLabel("Интервал поставок кузовов"));
        add(bodySlider);

        TimeoutSlider engineSlider = new TimeoutSlider(
                Supplier.MIN_TIMEOUT, Supplier.MAX_TIMEOUT, Supplier.DEFAULT_TIMEOUT);
        engineSlider.addChangeListener(new EngineSliderListener(factory));
        add(new TextLabel("Интервал поставок двигателей"));
        add(engineSlider);

        TimeoutSlider dealerSlider = new TimeoutSlider(
                Dealer.MIN_TIMEOUT, Dealer.MAX_TIMEOUT, Dealer.DEFAULT_TIMEOUT);
        dealerSlider.addChangeListener(new DealerSliderListener(factory));
        add(new TextLabel("Интервал запросов от дилеров"));
        add(dealerSlider);

        accessoryCounter = new CounterLabel("Аксессуаров на складе", 0);
        add(accessoryCounter);

        bodyCounter = new CounterLabel("Кузовов на складе", 0);
        add(bodyCounter);

        engineCounter = new CounterLabel("Двигателей на складе", 0);
        add(engineCounter);

        carCounter = new CounterLabel("Машин на складе", 0);
        add(carCounter);

        accessoryProducedCounter = new CounterLabel("Всего произведено аксессуаров", 0);
        add(accessoryProducedCounter);

        bodyProducedCounter = new CounterLabel("Всего произведено кузовов", 0);
        add(bodyProducedCounter);

        engineProducedCounter = new CounterLabel("Всего произведено двигателей", 0);
        add(engineProducedCounter);

        carProducedCounter = new CounterLabel("Всего произведено машин", 0);
        add(carProducedCounter);

        pendingCounter = new CounterLabel("Ожидают производства", 0);
        add(pendingCounter);

        setVisible(true);
    }

    @Override
    public void pendingUpdated(int newPending) { // запросы от дилеров, ожидающие исполнения
        SwingUtilities.invokeLater(() -> pendingCounter.setCounter(newPending));
    }

    @Override
    public void numStoredUpdated(int newNumStored, StockId stockId) {
        switch (stockId) {
            case ACCESSORY : {
                accessoryCounter.setCounter(newNumStored);
                break;
            }
            case BODY : {
                bodyCounter.setCounter(newNumStored);
                break;
            }
            case CAR : {
                carCounter.setCounter(newNumStored);
                break;
            }
            case ENGINE : {
                engineCounter.setCounter(newNumStored);
                break;
            }
        }
    }

    @Override
    public void newItem(StockId stockId) {
        switch (stockId) {
            case ACCESSORY : {
                accessoryProducedCounter.increment();
                break;
            }
            case BODY : {
                bodyProducedCounter.increment();
                break;
            }
            case ENGINE : {
                engineProducedCounter.increment();
                break;
            }
            case CAR : {
                carProducedCounter.increment();
                break;
            }
        }
    }
}
