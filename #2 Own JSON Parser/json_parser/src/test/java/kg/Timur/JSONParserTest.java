package kg.Timur;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class JSONParserTest {

    @Test
    public void myTest() {
        try {
            JSONParser.main(new String[] {"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/test/pass3.json"});
            //JSONParser.main(new String[] {"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/mytest.json"});
        } catch (ParseException e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    @Test
    public void step1Invalid() {
        Assertions.assertThrows(ParseException.class, () -> JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step1/invalid.json"}));
    }

    @Test
    public void step1Valid() {
        try {
            JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step1/valid.json"});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void step2Invalid1() {
        Assertions.assertThrows(ParseException.class, () -> JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step2/invalid.json"}));
    }

    @Test
    public void step2Invalid2() {
        Assertions.assertThrows(ParseException.class, () -> JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step2/invalid2.json"}));
    }

    @Test
    public void step2Valid1() {
        try {
            JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step2/valid.json"});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void step2Valid2() {
        try {
            JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step2/valid2.json"});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    public void step3Invalid() {
        Assertions.assertThrows(ParseException.class, () -> JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step3/invalid.json"}));
    }

    @Test
    public void step3Valid() {
        try {
            JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step3/valid.json"});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void step4Invalid() {
        Assertions.assertThrows(ParseException.class, () -> JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step4/invalid.json"}));
    }

    @Test
    public void step4Valid() {
        try {
            JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step4/valid.json"});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void step4Valid2() {
        try {
            JSONParser.main(new String[]{"/mnt/new_tom/CodingChallanges/#2 Own JSON Parser/json_parser/src/main/resources/tests/step4/valid2.json"});
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}