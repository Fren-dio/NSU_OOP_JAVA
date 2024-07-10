package factory.model;

import factory.model.exceptions.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
    private final Properties factoryProperties;

    public ConfigReader(String configFileName) throws IOException {
        this.factoryProperties = new Properties();

        try (InputStream in = ConfigReader.class.getResourceAsStream("/" + configFileName)) {
            factoryProperties.load(in);
        }
    }

    public int readAccessoryStockSize() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("AccessoryStockSize"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid formatting for config file (AccessoryStockSize)", e);
        }
    }

    public int readBodyStockSize() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("BodyStockSize"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid formatting for config file (BodyStockSize)", e);
        }
    }

    public int readEngineStockSize() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("EngineStockSize"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid formatting for config file (EngineStockSize)", e);
        }
    }

    public int readCarStockSize() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("CarStockSize"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid formatting for config file (CarStockSize)", e);
        }
    }

    public int readNumAccessorySuppliers() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("NumAccessorySuppliers"));
        } catch (NumberFormatException e) {
            throw new ConfigException(
                    "Invalid formatting for config file (NumAccessorySuppliers)", e);
        }
    }

    public int readNumWorkers() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("NumWorkers"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid formatting for config file (NumWorkers)", e);
        }
    }

    public int readNumDealers() throws ConfigException {
        try {
            return Integer.parseInt(factoryProperties.getProperty("NumDealers"));
        } catch (NumberFormatException e) {
            throw new ConfigException("Invalid formatting for config file (NumDealers)", e);
        }
    }
}
