package com.company;

import com.company.exceptions.ThrottleException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.ext.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

@Provider
public class ThrottleInterceptor implements WriterInterceptor, ReaderInterceptor {

    private static AtomicInteger counter = new AtomicInteger(0);
    private final int MAX_PARALLEL_REQUESTS = 5;
    private static final Object lock = new Object();
    @Override
    public Object aroundReadFrom(ReaderInterceptorContext context)
            throws IOException, WebApplicationException {

        int currentCount = counter.get();
        if (currentCount >= MAX_PARALLEL_REQUESTS) throw ThrottleException.DEFAULT_INSTANCE;
        synchronized (lock) {
            currentCount = counter.get();
            if (currentCount >= MAX_PARALLEL_REQUESTS) throw ThrottleException.DEFAULT_INSTANCE;
            counter.incrementAndGet();
        }

        try {
            return context.proceed();
        }
        finally {
            counter.decrementAndGet();
        }
    }

    @Override
    public void aroundWriteTo(WriterInterceptorContext context)
            throws IOException, WebApplicationException {
        context.proceed();
    }
}