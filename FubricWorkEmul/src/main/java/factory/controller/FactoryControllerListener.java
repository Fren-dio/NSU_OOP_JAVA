package factory.controller;

public interface FactoryControllerListener {
    void accessoryTimeoutChanged(int newTimeout);

    void bodyTimeoutChanged(int newTimeout);

    void engineTimeoutChanged(int newTimeout);

    void requestTimeoutChanged(int newTimeout);
}
