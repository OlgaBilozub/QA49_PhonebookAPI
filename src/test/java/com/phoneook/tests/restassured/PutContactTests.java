package com.phoneook.tests.restassured;

import com.phonebook.dto.ContactDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class PutContactTests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() {
        RestAssured.defaultParser = Parser.JSON;
        ContactDto contactDto = ContactDto.builder()
                .name("amir")
                .lastName("amir")
                .email("amir@gm.com")
                .phone("12345678900")
                .address("odessa")
                .description("home")
                .build();

        String message = given()
                .header(AUTHORIZATION, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");
        System.out.println(message);

        String[] split = message.split(": ");
        id = split[1].trim();
    }

    @Test
    public void updateContactsSuccessTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("amir")
                .lastName("amir")
                .email("amir@gm.com")
                .phone("12345678900")
                .address("odessa")
                .description("Update home")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts/" )
                .then()
                .assertThat().statusCode(200)
                .assertThat().body("message", equalTo("Contact update successfully"));

    }

    @Test
    public void updateContactsFormatError() {
        ContactDto invalidContactDto = ContactDto.builder()
                .id(id)
                .name("")
                .lastName("amir")
                .email("amir@gm.")
                .phone("12345678900")
                .address("odessa")
                .description("home")
                .build();
        given()
                .header(AUTHORIZATION, TOKEN)
                .body(invalidContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts/")
                .then()
                .assertThat().statusCode(400)
                .assertThat().body("message", equalTo("Any format error"));

    }

    @Test
    public void updateContactsUnauthorizedTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("amir")
                .lastName("amir")
                .email("amir@gm.com")
                .phone("12345678900")
                .address("odessa")
                .description("home")
                .build();
        given()
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts/")
                .then()
                .assertThat().statusCode(401)
                .assertThat().body("message", equalTo("Unauthorized"));


    }

    @Test
    public void updateContactsBadRequestTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id("non-existent-id")
                .name("amir")
                .lastName("amir")
                .email("amir@gm.com")
                .phone("")
                .address("odessa")
                .description("Updated contact")
                .build();
        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(404)
                .assertThat().body("message", equalTo("Contact not found"));

    }
}
