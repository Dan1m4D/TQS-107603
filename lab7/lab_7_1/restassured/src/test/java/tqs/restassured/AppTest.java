package tqs.restassured;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class AppTest {
    @Test
    @DisplayName("Test if API is available")
    void testAPI() {
        when().get("https://jsonplaceholder.typicode.com/")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("Test list all Todos from api")
    void testListAllTodos() {
        when().get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .statusCode(200);
    }

    @Test
    @DisplayName("when querying for ToDo #4, the API returns an object with title 'et porro tempora'")
    void testGetTodo4() {
        String title = when().get("https://jsonplaceholder.typicode.com/todos/4")
                .then()
                .statusCode(200)
                .extract()
                .path("title");

        assertEquals("et porro tempora", title);
    }

    @Test
    @DisplayName(" When listing all “todos”, you get id #198 and #199 in the JSON results")
    void testListAllTodos198and199() {
        when().get("https://jsonplaceholder.typicode.com/todos")
                .then()
                .statusCode(200)
                .body("id", hasItems(198, 199));

    }

    @Test
    @DisplayName(" When listing all “todos”, you the results in less than 2secs")
    void testListAllTodosLessThan2secs() {
        long time = given().when().get("https://jsonplaceholder.typicode.com/todos")
                .time();

        assertTrue(time < 2000L);
    }

}
