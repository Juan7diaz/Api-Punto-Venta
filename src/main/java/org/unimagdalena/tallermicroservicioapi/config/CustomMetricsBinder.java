package com.example.myapp.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

@Component
public class CustomMetricsBinder implements MeterBinder {

    private final Counter customersCounter;

    public CustomMetricsBinder(MeterRegistry registry) {
        customersCounter = registry.counter("customers.requests", "uri", "/api/v1/customers");
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        registry.bind(customersCounter);
    }

    public void incrementCustomersCounter() {
        customersCounter.increment();
    }
}