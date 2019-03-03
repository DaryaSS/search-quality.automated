#!/usr/bin/env bash
mvn clean test
cp -R ./report/allure-reports/history/ ./report/allure-results/history/
mvn allure:report
