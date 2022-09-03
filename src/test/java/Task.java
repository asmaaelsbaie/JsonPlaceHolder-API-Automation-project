import io.restassured.http.ContentType;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class Task {

    //////////////Randmize item
    Random rand = new Random(); //instance of random class
    int upperbound = 100; //////// numbers of PostID
    //generate random values from 0-100
    int randomId = rand.nextInt(upperbound);
    /////////// Attributes for url
    String commentUrl = "https://jsonplaceholder.typicode.com/comments/" + randomId;
    String AlbumUrl = "https://jsonplaceholder.typicode.com/albums/1";
    String postUrl = "https://jsonplaceholder.typicode.com/posts/1";
    String userUrl = "https://jsonplaceholder.typicode.com/users/1";
    String todoUrl = "https://jsonplaceholder.typicode.com/todos/";

    ///////////method for get random comment
    @Test
    public void M1_getRandomComment() {

        System.out.println(commentUrl);
        String email = given().when().get(commentUrl).print();
        System.out.println(email);
    }

    //////////////////method for Album Title
    @Test
    public void M2_getAlbumTitle() {

        System.out.println(AlbumUrl);
        String title = given().when().get(AlbumUrl).then().extract().path("title");
        System.out.println(title);
        System.out.println(title.length());
        Assert.assertTrue("title exceed 300 characters", title.length() < 300);

    }

    //////////////method for post
    @Test
    public void M3_post_using_same_userID() {

        String post = "{\n" + "  \"title\":\"Done by Asmaa\",\n" + "  \"body\":\"every QA Should study RestAssured ^_^\",\n" + "}";

        given().contentType(ContentType.JSON).body(post).log().all().when().post(postUrl).then().assertThat().statusCode(500);
        String statusCode = String.valueOf(given().when().get(userUrl).then().extract().statusCode());
        Assert.assertTrue("status code  not equal 200 ", statusCode.equals("200"));
        System.out.println(statusCode);
    }

    @Test
    public void M4_print_Title_completed_false() {
        for (int i = 1; i <= 200; i++) {
            Boolean completed = given().when().get(todoUrl + i).then().extract().path("completed");
            if (!completed) {
                String title = given().when().get(todoUrl + i).then().extract().path("title");
                System.out.println("title for " + i + "=" + title);
            }
        }

    }

}
