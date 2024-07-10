package factory.model.products;

import java.util.UUID;

public abstract class AbstractProduct {
    protected final UUID id;

    protected AbstractProduct() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
