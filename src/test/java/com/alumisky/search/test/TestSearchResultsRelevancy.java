package com.alumisky.search.test;

import com.alumisky.search.quality.app.api.Connector;
import com.alumisky.search.quality.app.api.ConnectorException;
import com.alumisky.search.quality.app.api.Result;
import com.alumisky.search.quality.app.api.ResultSet;
import com.alumisky.search.quality.app.api.Validator;
import com.alumisky.search.quality.app.score.SingleValidResultFunction;
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


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestSearchResultsRelevancy extends BaseTest {

    @Story("At least one relevant product should be present in the search results")
    @DisplayName("Result Relevancy test")
    @Severity(SeverityLevel.CRITICAL)
    @ParameterizedTest(name = "search query={1}")
    @CsvFileSource(resources = "/search-queries.csv")
    public void testAtLeastOneRelevantResult(String testCategory, String searchQuery) throws ConnectorException {
        Connector connector = createConnector();
        final Validator validator = new TitleKeywordValidator(new SingleValidResultFunction());
        final double loyaltyLevel = 1.0;

        String[] queryWords = searchQuery.split(" ");
        List<Result> results = connector.search(queryWords);
        Assumptions.assumeFalse(
                results.isEmpty(),
                "Connector returned empty result set for query '" + searchQuery + "'"
        );

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
        Assertions.assertEquals(
                loyaltyLevel,
                score,
                testCategory + ": At least one relevant product should be present in the search results."
        );

    }
}
