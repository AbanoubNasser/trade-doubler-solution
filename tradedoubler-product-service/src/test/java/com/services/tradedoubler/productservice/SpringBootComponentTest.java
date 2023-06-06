package com.services.tradedoubler.productservice;

import com.services.tradedoubler.productservice.repository.ProductsFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.function.Supplier;

import static org.testcontainers.shaded.org.awaitility.Awaitility.await;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Testcontainers
public abstract class SpringBootComponentTest {

    protected static String BASE_URL_TEMPLATE = "/api/v1";
    protected final String LOCATION_HEADER = "Location";
    @Autowired
    protected ProductsFileRepository productsFileRepository;
    private static final PostgreSQLContainer<?> postgreSQLContainer =
            SpringBootPostgresContainer.getInstance();

    @DynamicPropertySource
    static void applicationProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", awaitContainer(postgreSQLContainer::getJdbcUrl));
        registry.add("spring.datasource.username", awaitContainer(postgreSQLContainer::getUsername));
        registry.add("spring.datasource.password", awaitContainer(postgreSQLContainer::getPassword));
    }

    static Supplier<Object> awaitContainer(Supplier<Object> getter) {
        return () -> {
            await().until(postgreSQLContainer::isRunning);
            return getter.get();
        };
    }
}
