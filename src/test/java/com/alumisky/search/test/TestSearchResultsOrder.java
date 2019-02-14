package com.alumisky.search.test;

import com.alumisky.search.quality.app.api.Connector;
import com.alumisky.search.quality.app.api.ConnectorException;
import com.alumisky.search.quality.app.api.Result;
import com.alumisky.search.quality.app.api.ResultSet;
import com.alumisky.search.quality.app.api.Validator;
import com.alumisky.search.quality.app.score.MobileScoreFunction;
import com.alumisky.search.quality.app.validator.TitleKeywordValidator;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class TestSearchResultsOrder extends BaseTest {

    @Story("Product title should contain all query terms. Most relevant results should go first.")
    @DisplayName("Result Display Order")
    @Severity(SeverityLevel.CRITICAL)
    @ParameterizedTest(name = "search query={1}")
    @CsvFileSource(resources = "/search-queries.csv")
    public void testProductOrder(String testCategory, String searchQuery) throws ConnectorException {

        final Connector connector = createConnector();
        final Validator validator = new TitleKeywordValidator(new MobileScoreFunction());
        final double loyaltyLevel = .9;

        String[] queryWords = searchQuery.split(" ");
        List<Result> results = connector.search(queryWords);
        Assumptions.assumeFalse(results.isEmpty(), "Connector returned empty result set for query '" + searchQuery + "'");
        ResultSet resultSet = new ResultSet(results);
        validator.validate(queryWords, resultSet);

        //building detailed report including specific search results
        resultSet.getResults().forEach(result -> {
            try {
                assertResultIsRelevant(result);
            } catch (Throwable th) {
                //ignoring this error
            }
        });

        double score = resultSet.getScore();
        Assertions.assertTrue(score >= loyaltyLevel, testCategory + ": Product title should contain all query terms. Most relevant results should go first.");


    }



}
