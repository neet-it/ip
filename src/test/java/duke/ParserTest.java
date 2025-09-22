package duke;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParserTest {
    //Solution below Ai-assisted using ChatGPT
    @Test
    public void parseDateTime_validFormats() {
        LocalDateTime result1 = Parser.parseDateTime("2/12/2024 1800");
        assertEquals(LocalDateTime.of(2024, 12, 2, 18, 0), result1);

        LocalDateTime result2 = Parser.parseDateTime("1/1/2025 0900");
        assertEquals(LocalDateTime.of(2025, 1, 1, 9, 0), result2);

        LocalDateTime result3 = Parser.parseDateTime("25/12/2022 0000");
        assertEquals(LocalDateTime.of(2022, 12, 25, 0, 0), result3);

        LocalDateTime result4 = Parser.parseDateTime("15/6/2021");
        assertEquals(LocalDateTime.of(2021, 6, 15, 0, 0), result4);
    }

    //Solution below Ai-assisted using ChatGPT
    @Test
    public void parseDateTime_edgeCases() {
        LocalDateTime result1 = Parser.parseDateTime("29/2/2020 1200");
        assertEquals(LocalDateTime.of(2020, 2, 29, 12, 0), result1);


        LocalDateTime result2 = Parser.parseDateTime("31/12/2025 2359");
        assertEquals(LocalDateTime.of(2025, 12, 31, 23, 59), result2);


        LocalDateTime result3 = Parser.parseDateTime("10/10/2020 1234");
        assertEquals(LocalDateTime.of(2020, 10, 10, 12, 34), result3);
    }

    //Solution below Ai-assisted using ChatGPT
    @Test
    public void parseDateTime_nullAndEmpty() {
        assertThrows(NullPointerException.class, () -> {
            Parser.parseDateTime(null);
        });

        assertThrows(DateTimeParseException.class, () -> {
            Parser.parseDateTime("");
        });

        assertThrows(DateTimeParseException.class, () -> {
            Parser.parseDateTime("   ");
        });
    }

}
