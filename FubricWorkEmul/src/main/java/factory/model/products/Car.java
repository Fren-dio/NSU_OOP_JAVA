package factory.model.products;

public class Car extends AbstractProduct {
    private Accessory accessory;
    private Body body;
    private Engine engine;

    public Car(Accessory accessory, Body body, Engine engine) {
        this.accessory = accessory;
        this.body = body;
        this.engine = engine;
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public Body getBody() {
        return body;
    }

    public Engine getEngine() {
        return engine;
    }
}
