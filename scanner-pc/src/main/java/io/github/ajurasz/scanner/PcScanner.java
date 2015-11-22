package io.github.ajurasz.scanner;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.main.Main;
import org.apache.camel.model.rest.RestBindingMode;

public class PcScanner {
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.enableHangupSupport();
        main.addRouteBuilder(new ScannerRoute());
        main.run();
    }

    private static class ScannerRoute extends RouteBuilder {
        @Override
        public void configure() throws Exception {
            restConfiguration().component("netty4-http").host("0.0.0.0").port(8081).bindingMode(RestBindingMode.json);

            rest("/api/devices").get().route().to("bluetooth://scan?serviceDiscovery=true");
        }
    }
}
