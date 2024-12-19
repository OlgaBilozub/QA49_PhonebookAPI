package com.phoneook.tests.restassured;

import com.phonebook.dto.AllContactsDto;
import com.phonebook.dto.ContactDto;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetAllContactsRATests extends TestBase {

    @Test
    public void getAllContactsSuccessTest() {

        AllContactsDto contactsDto = given()
                .header(AUTHORIZATION, TOKEN)
                .when()
                .get("contacts")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(AllContactsDto.class);

        for (ContactDto contact : contactsDto.getContacts()) {
            System.out.println(contact.getId() + " *** " + contact.getName());
            System.out.println("**************************************************");
        }
    }
}
