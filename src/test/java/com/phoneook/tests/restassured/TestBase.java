package com.phoneook.tests.restassured;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeMethod;

public class TestBase {

    //      eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWxvbmFAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MzUyMjA5NzAsImlhdCI6MTczNDYyMDk3MH0.vTRlszGNduxHBcCX5WyTQ6pwouEdNic5Qp75AODNfqw
    public static final String TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6WyJST0xFX1VTRVIiXSwic3ViIjoiYWxvbmFAZ21haWwuY29tIiwiaXNzIjoiUmVndWxhaXQiLCJleHAiOjE3MzUyMjA5NzAsImlhdCI6MTczNDYyMDk3MH0.vTRlszGNduxHBcCX5WyTQ6pwouEdNic5Qp75AODNfqw";
    public static final String AUTHORIZATION = "Authorization";

    //public static final String TOKEN = System.getenv("TOKEN");

    @BeforeMethod
    public void init() {

        RestAssured.baseURI="https://contactapp-telran-backend.herokuapp.com";
        RestAssured.basePath="v1";
    }
}
