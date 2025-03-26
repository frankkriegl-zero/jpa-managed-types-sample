package org.example.bookstore;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Disabled
@Import(TestcontainersConfiguration.class)
@SpringBootTest
class JpaManagedTypesSampleApplicationTests {

    @Test
    void givenEntityScanEnabled_whenGetBooks_thenBooksReturned() {
        // Todo: Implement test
    }

    @Test
    void givenManualConfigurationOfManagedTypes_whenGetBooks_thenErrorOccurs() {
        // Todo: demonstrate the error scenario of managed types in a test
    }

}
