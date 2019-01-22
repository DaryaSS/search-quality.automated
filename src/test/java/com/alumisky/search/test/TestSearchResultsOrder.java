package com.alumisky.search.test;

import com.alumisky.search.quality.app.api.Connector;
import com.alumisky.search.quality.app.api.ConnectorException;
import com.alumisky.search.quality.app.api.Result;
import com.alumisky.search.quality.app.api.ResultSet;
import com.alumisky.search.quality.app.api.ValidationResult;
import com.alumisky.search.quality.app.api.Validator;
import com.alumisky.search.quality.app.api.Verdict;
import com.alumisky.search.quality.app.score.MobileScoreFunction;
import com.alumisky.search.quality.app.validator.TitleKeywordValidator;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Step;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@TestInstance(PER_CLASS)
public class TestSearchResultsOrder extends BaseTest {

    @Story("Product title should contain all query terms. Most relevant results should go first.")
    @DisplayName("Result Display Order")
    @Severity(SeverityLevel.CRITICAL)
    @ParameterizedTest(name = "{0}: search query={2}")
    @CsvFileSource(resources = "/search-queries.csv")
    public void testProductOrder(String connectorId, String lang, String searchQuery) throws ConnectorException {

        final Connector connector = createConnector(connectorId);
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
        Assertions.assertTrue(score >= loyaltyLevel, "Search result Score is lower than expected. Actual score is " + score + "; expected score is " + loyaltyLevel);


    }

    @Step("{result.title}")
    private void assertResultIsRelevant(Result result) {
        Assertions.assertEquals(Verdict.RELEVANT, result.getVerdict(), () -> buildErrorMessage(result));
    }

    private String buildErrorMessage(Result searchResult) {
        Iterator<ValidationResult> it = searchResult.getMessages();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext()) {
            sb.append(it.next().getMessage());
        }
        return sb.toString();
    }

}
