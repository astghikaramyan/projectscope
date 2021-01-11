package am.itspace.projectscope.controllertest;

import am.itspace.projectscope.ProjectscopeApplication;
import am.itspace.projectscope.entity.UserEntity;
import am.itspace.projectscope.model.Type;
import am.itspace.projectscope.util.EncriptionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URI;
import java.net.URISyntaxException;

@ContextConfiguration(classes = ProjectscopeApplication.class)
//@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void test_for_user_registration() throws URISyntaxException {
        final String baseUrl = "http://localhost:"+randomServerPort+"/api/user/register";
        java.net.URI uri = new URI(baseUrl);
        UserEntity el = new UserEntity();
        el.setEmail("a7@mail.ru");
        el.setPassword("a7");
        el.setUserName("a7");
        el.setType(Type.MEMBER);
        ResponseEntity<?> responseEntity = restTemplate.postForEntity(uri, new HttpEntity<>(el),String.class);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
    }
}
