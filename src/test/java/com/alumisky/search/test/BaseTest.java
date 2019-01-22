package com.alumisky.search.test;

import com.alumisky.search.quality.app.api.Connector;
import com.alumisky.search.quality.app.connector.ConnectorFactory;
import org.junit.jupiter.api.AfterEach;

import java.util.Objects;

public class BaseTest {

    @AfterEach
    public void tearDown() throws Exception {
        Thread.sleep(100);
    }

    Connector createConnector(String name) {
        Connector c = ConnectorFactory.newCachingConnector(name);
        Objects.requireNonNull(c, "Cannot create connector " + name);
        return c;
    }

}
