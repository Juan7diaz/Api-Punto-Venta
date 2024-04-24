package org.unimagdalena.tallermicroservicioapi.config;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

@Component
public class CustomMetricsBinder implements MeterBinder {
    private final Counter customersCounter;

    public CustomMetricsBinder(MeterRegistry registry) {
        customersCounter = Counter.builder("customers.requests")
                .tag("uri", "/api/v1/customers")
                .tag("method", "GET")
                .tag("controller", "ClienteController.getAllCliente")
                .description("Total number of customers requests")
                .baseUnit("requests")
                .register(registry);
    }

    @Override
    public void bindTo(MeterRegistry registry) {
    }

    public void incrementCustomersCounter() {
        customersCounter.increment();
    }
}
