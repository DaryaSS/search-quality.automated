package com.alumisky.search.test;

import com.alumisky.search.quality.app.api.Connector;
import com.alumisky.search.quality.app.api.Result;
import com.alumisky.search.quality.app.api.ValidationResult;
import com.alumisky.search.quality.app.api.Verdict;
import com.alumisky.search.quality.app.connector.ConnectorFactory;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;
import java.util.Objects;

public class BaseTest {

    @AfterEach
    public void tearDown() throws Exception {
        Thread.sleep(100);
    }

    Connector createConnector() {
        Connector c = ConnectorFactory.defaultCachingConnector();
        Objects.requireNonNull(c, "Cannot create connector");
        return c;
    }

    @Step("{result.title}")
    protected void assertResultIsRelevant(Result result) {
        Assertions.assertEquals(Verdict.RELEVANT, result.getVerdict(), () -> buildErrorMessage(result));
    }

    protected String buildErrorMessage(Result searchResult) {
        Iterator<ValidationResult> it = searchResult.getMessages();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next().getMessage());
        }
        return sb.toString();
    }

}
