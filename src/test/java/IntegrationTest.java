import cafe.AppContextListener;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IntegrationTest {

    private Tomcat tomcat;
    private int port = 8080;

    @BeforeAll
    public void setUp() throws Exception {
        tomcat = new Tomcat();
        tomcat.setPort(0);
        tomcat.getConnector();

        // 웹 애플리케이션 루트를 설정합니다.
        Context ctx = tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        // 리스너를 추가합니다.
        ctx.addApplicationListener(AppContextListener.class.getName());

        tomcat.start();
        port = tomcat.getConnector().getLocalPort();
    }

    @AfterAll
    public void tearDown() throws Exception {
        tomcat.stop();
        tomcat.destroy();
    }

    @Test
    public void testUserServlet() throws IOException, InterruptedException, URISyntaxException {
        var client = java.net.http.HttpClient.newHttpClient();
        var request = java.net.http.HttpRequest.newBuilder()
                .uri(new URI("http://localhost:" + port + "/users"))
                .build();
        URI uri = new URI("http://localhost:" + port + "/users");
        var response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());
        System.out.println(response.headers());
//        assertEquals(200, response.statusCode());
    }
}
