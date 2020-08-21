package sia.tacocloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loggable {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void i(String msg) {
        log.info(msg);
    }


    public void w(String msg) {
        log.warn(msg);
    }

    public void d(String msg) {
        log.debug(msg);
    }

    public void e(String msg) {
        log.error(msg);
    }


}
