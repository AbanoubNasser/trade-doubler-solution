package com.services.tradedoubler.product.processorservice.scheduler;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.services.tradedoubler.product.processorservice.SpringBootComponentTest;
import com.services.tradedoubler.product.processorservice.exception.ServiceException;
import com.services.tradedoubler.product.processorservice.model.ProductEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ProductsProcessorSchedulerIntegrationTests extends SpringBootComponentTest {
    static WireMockServer wireMockServer = new WireMockServer(options().port(8080));

    @Autowired
    ProductsProcessorScheduler productsProcessorScheduler;

    static private UUID id;

    @BeforeAll
    static void init(){
        wireMockServer.start();
        id = UUID.randomUUID();
    }

    @AfterAll
     static void destroy(){
        wireMockServer.stop();
    }

   @Test
    void testProductsProcessorSchedulerExceptionWhileRetrieveFiles(){
        wireMockServer.stubFor(WireMock.get("/api/v1/products/files?status=UPLOADED")
                .withId(id)
                .willReturn(aResponse().withStatus(500)));
        assertThrows(ServiceException.class, () -> productsProcessorScheduler.processProductsFiles());
    }

    @Test
    void testProductsProcessorSchedulerWhileRetrieveZeroFiles(){
        wireMockServer.editStub(WireMock.get("/api/v1/products/files?status=UPLOADED")
                        .withId(id)
                .willReturn(aResponse().withStatus(200).withBody("[]")
                        .withHeader("Content-Type", "application/json")));
        assertDoesNotThrow(() -> productsProcessorScheduler.processProductsFiles());
    }

    @Test
    void testProductsProcessorSchedulerWhileRetrieveFileWithNotValidContent() throws InterruptedException {
        wireMockServer.editStub(WireMock.get("/api/v1/products/files?status=UPLOADED")
                        .withId(id)
                .willReturn(aResponse().withStatus(200)
                        .withBody("""
                                [
                                {
                                "id":"76c49832-055f-11ee-be56-0242ac120002",
                                "fileName":"Products.xml",
                                "status":"UPLOADED"
                                }
                                ]
                                """)
                        .withHeader("Content-Type", "application/json")));

        wireMockServer.stubFor(WireMock.get("/api/v1/products/files/76c49832-055f-11ee-be56-0242ac120002/content")
                .withId(id)
                .willReturn(aResponse().withStatus(200)
                        .withBody("""
                                <Student><Name>Test</Name></Student>
                                """)
                        .withHeader("Content-Type", "application/xml")));

        wireMockServer.stubFor(WireMock.put("/api/v1/products/files/76c49832-055f-11ee-be56-0242ac120002")
                .withId(id)
                .withRequestBody(WireMock.containing("status"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse().withStatus(204)).withHeader("Content-Type", equalTo("application/json")));
        Thread.sleep(30000);

        productsProcessorScheduler.processProductsFiles();

        wireMockServer.verify(exactly(1), putRequestedFor(urlEqualTo("/api/v1/products/files/76c49832-055f-11ee-be56-0242ac120002"))
                .withRequestBody(WireMock.containing("FAILED_TO_PROCESS")));

    }

    @Test
    void testProductsProcessorSchedulerWhileRetrieveFileContentSuccessfully() throws InterruptedException, IOException {
        wireMockServer.stubFor(WireMock.get("/api/v1/products/files?status=UPLOADED")
                .withId(id)
                .willReturn(aResponse().withStatus(200)
                        .withBody("""
                                [
                                {
                                "id":"76c49832-055f-11ee-be56-0242ac120002",
                                "fileName":"Products.xml",
                                "status":"UPLOADED"
                                }
                                ]
                                """)
                        .withHeader("Content-Type", "application/json")));

        wireMockServer.stubFor(WireMock.get("/api/v1/products/files/76c49832-055f-11ee-be56-0242ac120002/content")
                .withId(id)
                .willReturn(aResponse().withStatus(200)
                        .withBody(getResourceFileAsString("Products.xml"))
                        .withHeader("Content-Type", "application/xml")));

        wireMockServer.stubFor(WireMock.put("/api/v1/products/files/76c49832-055f-11ee-be56-0242ac120002")
                .withId(id)
                .withRequestBody(WireMock.containing("status"))
                .withHeader("Content-Type", equalTo("application/json"))
                .willReturn(aResponse().withStatus(204)).withHeader("Content-Type", equalTo("application/json")));
        Thread.sleep(30000);

        productsProcessorScheduler.processProductsFiles();

        wireMockServer.verify(exactly(1), putRequestedFor(urlEqualTo("/api/v1/products/files/76c49832-055f-11ee-be56-0242ac120002"))
                .withRequestBody(WireMock.containing("SUCCEEDED")));

        List<ProductEntity> products = productRepository.findByProductFileId("76c49832-055f-11ee-be56-0242ac120002");
        assertNotNull(products);
        assertEquals(3, products.size());
    }

    private String getResourceFileAsString(String fileName) throws IOException {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(fileName)) {
            if (is == null) return null;
            try (InputStreamReader isr = new InputStreamReader(is);
                 BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        }
    }

}
