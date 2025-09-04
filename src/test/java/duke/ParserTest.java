package duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    public void testParseDateTime_validFormats() {
        // Test date with time (4-digit format)
        LocalDateTime result1 = Parser.parseDateTime("2/12/2019 1800");
        assertEquals(LocalDateTime.of(2019, 12, 2, 18, 0), result1);

        // Test single digit day and month
        LocalDateTime result2 = Parser.parseDateTime("1/1/2023 0900");
        assertEquals(LocalDateTime.of(2023, 1, 1, 9, 0), result2);

        // Test midnight time
        LocalDateTime result3 = Parser.parseDateTime("25/12/2022 0000");
        assertEquals(LocalDateTime.of(2022, 12, 25, 0, 0), result3);

        // Test date only (should default to start of day)
        LocalDateTime result4 = Parser.parseDateTime("15/6/2021");
        assertEquals(LocalDateTime.of(2021, 6, 15, 0, 0), result4);
    }
    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    // ------------------ Index Parsing ------------------

    @Test
    void testParseMarkCommand_valid() {
        assertEquals(0, parser.parseMarkCommand("mark 1"));
        assertEquals(4, parser.parseMarkCommand("mark 5"));
    }

    @Test
    void testParseUnmarkCommand_valid() {
        assertEquals(1, parser.parseUnmarkCommand("unmark 2"));
        assertEquals(9, parser.parseUnmarkCommand("unmark 10"));
    }

    @Test
    void testParseDeleteCommand_valid() {
        assertEquals(2, parser.parseDeleteCommand("delete 3"));
        assertEquals(0, parser.parseDeleteCommand("delete 1"));
    }

    // ------------------ Todo ------------------

    @Test
    void testParseTodoCommand_valid() {
        Task task = parser.parseTodoCommand("todo Read book");
        assertNotNull(task);
        assertTrue(task instanceof Todo);
        assertEquals("Read book", task.getDescription());
    }

    @Test
    void testParseTodoCommand_emptyDescription() {
        Task task = parser.parseTodoCommand("todo   ");
        assertNotNull(task);
        assertTrue(task instanceof Todo);
        assertEquals("", task.getDescription());
    }

    // ------------------ Deadline ------------------

    @Test
    void testParseDeadlineCommand_withDateTime() {
        Task task = parser.parseDeadlineCommand("deadline Submit report /by 3/9/2025 1800");
        assertNotNull(task);
        assertTrue(task instanceof Deadline);
        Deadline d = (Deadline) task;
        LocalDateTime expected = LocalDateTime.of(2025, 9, 3, 18, 0);
        assertEquals(expected, d.byDateTime);
        assertEquals("Submit report", d.getDescription());
    }

    @Test
    void testParseDeadlineCommand_invalidFormat() {
        Task task = parser.parseDeadlineCommand("deadline Task /by invalid");
        assertNotNull(task);
        assertTrue(task instanceof Deadline);
        Deadline d = (Deadline) task;
        assertEquals("invalid", d.by);
    }

    // ------------------ Event ------------------

    @Test
    void testParseEventCommand_withDateTime() {
        Task task = parser.parseEventCommand("event Meeting /from 3/9/2025 1400 /to 3/9/2025 1600");
        assertNotNull(task);
        assertTrue(task instanceof Event);
        Event e = (Event) task;
        LocalDateTime from = LocalDateTime.of(2025, 9, 3, 14, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 3, 16, 0);
        assertEquals(from, e.fromDateTime);
        assertEquals(to, e.toDateTime);
        assertEquals("Meeting", e.getDescription());
    }

    @Test
    void testParseEventCommand_withStringDates() {
        Task task = parser.parseEventCommand("event Party /from Friday /to Saturday");
        assertNotNull(task);
        assertTrue(task instanceof Event);
        Event e = (Event) task;
        assertEquals("Friday", e.from);
        assertEquals("Saturday", e.to);
    }



    // ------------------ parseDateTime ------------------

    @Test
    void testParseDateTime_dateAndTime() {
        LocalDateTime dt = Parser.parseDateTime("3/9/2025 1430");
        LocalDateTime expected = LocalDateTime.of(2025, 9, 3, 14, 30);
        assertEquals(expected, dt);
    }

    @Test
    void testParseDateTime_dateOnly() {
        LocalDateTime dt = Parser.parseDateTime("3/9/2025");
        LocalDateTime expected = LocalDateTime.of(2025, 9, 3, 0, 0);
        assertEquals(expected, dt);
    }

    @Test
    void testParseDateTime_invalidTime() {
        assertThrows(DateTimeParseException.class, () -> Parser.parseDateTime("3/9/2025 25:00"));
    }

    @Test
    void testParseDateTime_invalidFormat() {
        assertThrows(DateTimeParseException.class, () -> Parser.parseDateTime("invalid"));
    }


}
