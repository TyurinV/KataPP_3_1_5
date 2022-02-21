package academy.kata.LastWork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@SpringBootApplication
public class LastWorkApplication {

    static RestTemplate restTemplate = new RestTemplate();
    static String url = "http://91.241.64.178:7081/api/users/";
    static User user;
    static public String coockie;
    static String goal;


    public static void main(String[] args) {
        SpringApplication.run(LastWorkApplication.class, args);
        useExchange();
    }

    public static void useExchange() {

        HttpHeaders headers = new HttpHeaders();

        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
// allusers: Cookie
        System.out.println("\n--------------Get all юзера----------");
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
        HttpStatus status = responseEntity.getStatusCode();
        System.out.println("Status ----> " + status);

        String users = responseEntity.getBody();
        System.out.println("Body ---> " + users);

        HttpHeaders responseHeaders = responseEntity.getHeaders();
        coockie = responseHeaders.toString().substring(13, 74);
        headers.add("Cookie", coockie);
        System.out.println("coockie -->" + coockie);
        System.out.println("requestHeaders ---> " + requestEntity.getHeaders());
        System.out.println("--------------Конец Get all юзера----------\n");


        user = new User(3L, "James", "Brown", (byte) -22);
        requestEntity = new HttpEntity<>(user, headers);
        addUser(requestEntity);
        System.out.println(user + " до изменения");

        user.setName("Thomas");
        user.setLastName("Shelby");
        System.out.println(user + " после");
        editUser(requestEntity);
        deleteUser(requestEntity);
        System.out.println("\n---------------------------------------\n---------------------------------------\n" + "Ответ = " + goal + "\n---------------------------------------\n---------------------------------------");

    }

    public static void addUser(HttpEntity<Object> requestEntity) {
        System.out.println("\n--------------добавляем юзера----------");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        HttpStatus status = responseEntity.getStatusCode();
        System.out.println("Status ----> " + status);

        String user = responseEntity.getBody();
        System.out.println("Body ---> " + user + "<-----");
        goal = user;
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String coockie = responseHeaders.toString().substring(13, 74);
        headers.set("Cookie", coockie);
        System.out.println("coockie -->" + coockie);
        System.out.println("requestHeaders ---> " + requestEntity.getHeaders());
        System.out.println("responseHeaders ---> " + responseHeaders);
        System.out.println("--------------Конец добавляем юзера----------\n");
    }

    //изменяем юзера
    public static void editUser(HttpEntity<Object> requestEntity) {
        System.out.println("\n--------------Изменяем юзера----------");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, String.class);

        HttpStatus status = responseEntity.getStatusCode();
        System.out.println("Status ----> " + status);

        String user = responseEntity.getBody();
        System.out.println("Body ---> " + user + "<-----");
        goal += user;
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String coockie = responseHeaders.toString().substring(13, 74);
        headers.set("Cookie", coockie);

        System.out.println("coockie -->" + coockie);
        System.out.println("responseHeaders ---> " + responseHeaders);
        System.out.println("--------------Конец Изменяем юзера----------\n");
    }

    public static void deleteUser(HttpEntity<Object> requestEntity) {
        System.out.println("\n--------------Удаляем юзера----------");
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<String> responseEntity = restTemplate.exchange(url + user.getId(), HttpMethod.DELETE, requestEntity, String.class);

        HttpStatus status = responseEntity.getStatusCode();
        System.out.println("Status ----> " + status);

        String user = responseEntity.getBody();
        System.out.println("Body ---> " + user + "<-----");
        goal += user;
        HttpHeaders responseHeaders = responseEntity.getHeaders();
        String coockie = responseHeaders.toString().substring(13, 74);
        headers.set("Cookie", coockie);

        System.out.println("coockie -->" + coockie);
        System.out.println("responseHeaders ---> " + responseHeaders);
        System.out.println("--------------Конец удаляем юзера----------\n");
    }

}
