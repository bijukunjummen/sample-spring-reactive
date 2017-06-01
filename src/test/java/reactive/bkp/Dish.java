package reactive.bkp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Dish {
    private final byte[] oneKb = new byte[1_024];
    private final int id;

    private static final Logger LOGGER = LoggerFactory.getLogger(Dish.class);
    Dish(int id) {
        this.id = id;
        LOGGER.info("Created: " + id);
    }

    public String toString() {
        return String.valueOf(id);
    }
}