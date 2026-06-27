package kg.Timur;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ccwcTest extends ccwc {

    @org.junit.jupiter.api.Test
    void testMain() throws IOException {
        main(new String[]{"-c", "test.txt src/main/resources/"});
    }
}
