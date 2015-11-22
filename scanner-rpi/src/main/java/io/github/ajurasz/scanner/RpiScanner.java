package io.github.ajurasz.scanner;

import io.rhiot.component.pi4j.Pi4jConstants;
import io.rhiot.component.pi4j.gpio.GPIOAction;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;

import static io.rhiot.component.bluetooth.BluetoothPredicates.deviceWithName;

public class RpiScanner {
    private static final String DEVICE_NAME = "arek";
    private static final String DEVICE_FOUND = "DEVICE_FOUND";
    private static final String DEVICE_NOT_FOUND = "DEVICE_NOT_FOUND";

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new ScannerRoute());
        main.run();
    }

    private static class ScannerRoute extends RouteBuilder {

        @Override
        public void configure() throws Exception {
            from("bluetooth://scan?consumer.delay=1000")
                    .to("pi4j-gpio://1?mode=DIGITAL_OUTPUT&state=LOW&action=BLINK")
                    .choice()
                    .when(deviceWithName(DEVICE_NAME))
                        .process(p -> p.getIn().setHeader(DEVICE_FOUND, true))
                    .otherwise()
                        .process(p -> p.getIn().setHeader(DEVICE_NOT_FOUND, true))
                    .end()
                    .multicast().to("direct:green", "direct:red");

            from("direct:green")
                    .process(exchange -> checkHeaderAndSetAction(DEVICE_FOUND, exchange))
                    .to("pi4j-gpio://2?mode=DIGITAL_OUTPUT&state=LOW");

            from("direct:red")
                    .process(exchange -> checkHeaderAndSetAction(DEVICE_NOT_FOUND, exchange))
                    .to("pi4j-gpio://3?mode=DIGITAL_OUTPUT&state=LOW");
        }

        private void checkHeaderAndSetAction(String headerKey, Exchange exchange) {
            if (exchange.getIn().getHeader(headerKey) != null) {
                exchange.getIn().setHeader(Pi4jConstants.CAMEL_RBPI_PIN_ACTION, GPIOAction.HIGH);
            } else {
                exchange.getIn().setHeader(Pi4jConstants.CAMEL_RBPI_PIN_ACTION, GPIOAction.LOW);
            }
        }
    }
}
