package com.phoneook.tests.restassured;

import com.phonebook.dto.ContactDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PutContactsTests extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() {
        RestAssured.defaultParser = Parser.JSON;
        ContactDto contactDto = ContactDto.builder()
                .name("Olga")
                .lastName("Bil")
                .email("fffffff@gm.com")
                .phone("1234567890")
                .address("Lust.doroga")
                .description("")
                .build();

        String message = given()
                .header(AUTHORIZATION, TOKEN)
                .body(contactDto)
                .contentType(ContentType.JSON)
                .when()
                .post("contacts")
                .then()
                .extract().path("message");

        String[] split = message.split(": ");
        id = split[1];
    }

    @Test
    public void updateContactSuccessTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Olga")
                .lastName("Bil")
                .email("fffffff@gm.com")
                .phone("1234567890")
                .address("Lust.doroga")
                .description("")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts/")
                .then()
                .assertThat().statusCode(200);
        //    .assertThat().body("message", equalTo("Contact was updated!"));
    }

    @Test
    public void updateContactWithWrongId() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id("wrong id")
                .name("Olga")
                .lastName("Bil")
                .email("fffffff@gm.com")
                .phone("1234567890")
                .address("Lust.doroga")
                .description("")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400);
        //.assertThat().body("message", containsString("not found in your contacts"));
    }

    @Test
    public void updateContactUnauthorizedTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("Olga")
                .lastName("Bil")
                .email("fffffff@gm.com")
                .phone("1234567890")
                .address("unauthorized")
                .description("")
                .build();

        given()
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(403);
               // .assertThat().body("message", containsString("Unauthorized"));
    }
    @Test
    public void updateContactNotFoundTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id("0")
                .name("Not")
                .lastName("not")
                .email("fffffff@gm.com")
                .phone("111111111")
                .address("not")
                .description("not")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400);
              //  .assertThat().body("message", containsString("not found"));
    }
    @Test
    public void updateContactWithEmptyFieldsTest() {
        ContactDto updatedContactDto = ContactDto.builder()
                .id(id)
                .name("")
                .lastName("")
                .email("")
                .phone("")
                .address("")
                .description("")
                .build();

        given()
                .header(AUTHORIZATION, TOKEN)
                .body(updatedContactDto)
                .contentType(ContentType.JSON)
                .when()
                .put("contacts")
                .then()
                .assertThat().statusCode(400);
            //    .assertThat().body("message", containsString("Validation failed"));
    }

}
