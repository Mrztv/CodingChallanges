package kg.Timur;

import static org.junit.jupiter.api.Assertions.*;

class ccwcTest extends ccwc {

    @org.junit.jupiter.api.Test
    void testMain() {
        main(new String[]{"-c", "test.txt src/main/resources/"});
    }
}
