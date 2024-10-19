package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.student.Days;
import seedu.address.model.student.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.student.predicates.ScheduleContainsKeywordsPredicate;

public class FindCommandParserTest {

    private static NameContainsKeywordsPredicate namePredicateKeywords;
    private static ScheduleContainsKeywordsPredicate schedulePredicateKeywords;
    private FindCommandParser parser = new FindCommandParser();

    @BeforeAll
    public static void setUp() {
        namePredicateKeywords = new NameContainsKeywordsPredicate(Set.of("Bob", "Alice1"));
        schedulePredicateKeywords = new ScheduleContainsKeywordsPredicate(Set.of(Days.MONDAY, Days.TUESDAY));
    }


    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyNameArgument_throwsParseException() {
        // day argument is present
        assertParseFailure(parser, " n/  d/Saturday",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_NAME_KEYWORDS));
        assertParseFailure(parser, " d/Saturday n/ ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_NAME_KEYWORDS));

        // multiple whitespaces
        assertParseFailure(parser, " n/     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_NAME_KEYWORDS));
    }

    @Test
    public void parse_emptyDayArgumentWithValidNameArgumentInfront_throwsParseException() {
        // name argument is present
        assertParseFailure(parser, " n/Alice BOB d/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_SCHEDULE_KEYWORDS));

    }

    @Test
    public void parse_emptyDayArgument_throwsParseException() {
        assertParseFailure(parser, " d/ n/Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_SCHEDULE_KEYWORDS));

        // multiple whitespaces
        assertParseFailure(parser, " d/     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_NO_SCHEDULE_KEYWORDS));

    }

    @Test
    public void parse_validNameArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(List.of(namePredicateKeywords));

        // alnum characters and extra whitespaces allowed
        assertParseSuccess(parser, " n/Bob Alice1", expectedFindCommand);
    }

    @Test
    public void parse_validDayArgs_returnsFindCommand() {
        FindCommand expectedFindCommand = new FindCommand(List.of(schedulePredicateKeywords));

        // random case and extra whitespaces allowed
        assertParseSuccess(parser, " d/  Monday   tUeSdAy", expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {

        FindCommand expectedFindCommand = new FindCommand(Arrays.asList(
                schedulePredicateKeywords,
                namePredicateKeywords)
        );

        // no leading and trailing whitespaces
        assertParseSuccess(parser, " n/Alice1 Bob d/Monday Tuesday", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " n/\n Alice1 \n \t Bob  \t d/Monday     Tuesday", expectedFindCommand);

        // swapped order of arguments
        assertParseSuccess(parser, " d/Monday Tuesday n/Alice1 Bob", expectedFindCommand);
    }


}
