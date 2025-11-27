package org.example.api._base;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.Filter;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.RequiredArgsConstructor;
import org.example.env.config.ApiConfig;

import java.util.List;

@RequiredArgsConstructor
public class _BaseApi {

    protected final ApiConfig CONFIG;

    protected RequestSpecification jsonAutoAuth() {
        return buildRequest(ContentType.JSON);
    }

    protected RequestSpecification buildRequest(ContentType contentType) {

        return RestAssured.given()
                .config(createConfig())
                .relaxedHTTPSValidation()
                .contentType(contentType)
                .baseUri(CONFIG.baseUrl())
                .filters(getFilters());

    }

    private List<Filter> getFilters() {

        return List.of(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    private RestAssuredConfig createConfig() {

        return RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", 5000));
    }
}
