package com.faceit.faceit.utilities;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class RequestCounter {
    private final AtomicInteger counter = new AtomicInteger(0);

    public int incrementCounter() {
        return counter.incrementAndGet();
    }
}