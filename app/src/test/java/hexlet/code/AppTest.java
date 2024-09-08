package hexlet.code;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hexlet.code.model.Url;
import hexlet.code.repository.UrlCheckRepository;
import hexlet.code.repository.UrlRepository;
import hexlet.code.util.NamedRoutes;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

import io.javalin.Javalin;
import io.javalin.testtools.JavalinTest;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class AppTest {

    private static Javalin app;
    private static MockWebServer mockWebServer;
    private static String testUrl;

    private static final String TEST_PAGE = "TestPage.html";

    private static Path getFixturePath(String fileName) {
        return Paths.get("src", "test", "resources", "fixtures", fileName)
                .toAbsolutePath().normalize();
    }

    private static String readFixture(String fileName) throws Exception {
        Path filePath = getFixturePath(fileName);
        return Files.readString(filePath).trim();
    }

    @BeforeAll
    static void generalSetUp() throws Exception {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
        MockResponse mockResponse = new MockResponse()
                .setBody(readFixture(TEST_PAGE))
                .setResponseCode(200);
        mockWebServer.enqueue(mockResponse);
        testUrl = mockWebServer.url("/").toString();
    }

    @BeforeEach
    public final void setUpForEachTest() throws IOException, SQLException {
        app = App.getApp();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
        app.stop();
    }
    @Test
    public void testMainPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testUrlsPage() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls");
            assertThat(response.code()).isEqualTo(200);
        });
    }

    @Test
    public void testCreateUrl() {
        JavalinTest.test(app, (server, client) -> {
            var requestBody = "url=https://www.hexlet.io";
            var response = client.post("/urls", requestBody);
            assertThat(response.code()).isEqualTo(200);
            assertThat(response.body().string()).contains("https://www.hexlet.io");
            assertThat(UrlRepository.existByName("https://www.hexlet.io"));
        });
    }

    @Test
    public void testUrlNotFound() {
        JavalinTest.test(app, (server, client) -> {
            var response = client.get("/urls/1234567890");
            assertThat(response.code()).isEqualTo(404);
        });
    }

    @Test
    public void testCheckPath() throws SQLException {
        var url = new Url(testUrl);
        UrlRepository.save(url);
        JavalinTest.test(app, (server, client) -> {
            try (var response = client.post(NamedRoutes.urlCheck(1L))) {
                assertThat(response.code()).isEqualTo(200);
                assertThat(response.body().string()).contains("Test description", "Test page h1", "Test title");
                var actualCheck = UrlCheckRepository.findUrlChecks(1L).getFirst();
                assertThat(actualCheck.getStatusCode()).isEqualTo(200);
                assertThat(actualCheck.getTitle()).isEqualTo("Test title");
                assertThat(actualCheck.getH1()).isEqualTo("Test page h1");
                assertThat(actualCheck.getDescription()).isEqualTo("Test description");
                assertThat(Timestamp.valueOf(actualCheck.getCreatedAt())).isToday();
            }
        });
    }
}
