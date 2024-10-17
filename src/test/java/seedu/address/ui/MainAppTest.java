package seedu.address.ui;

import static org.hamcrest.core.StringContains.containsString;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.control.LabeledMatchers.hasText;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OWED_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SCHEDULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;

import java.awt.GraphicsEnvironment;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import seedu.address.MainApp;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Subject;
import seedu.address.testutil.PersonBuilder;

public class MainAppTest extends ApplicationTest {

    private final String name = PersonBuilder.DEFAULT_NAME;
    private final String phone = PersonBuilder.DEFAULT_PHONE;
    private final String email = PersonBuilder.DEFAULT_EMAIL;
    private final String address = PersonBuilder.DEFAULT_ADDRESS;
    private final String schedule = PersonBuilder.DEFAULT_SCHEDULE;
    private final String subject = PersonBuilder.DEFAULT_SUBJECT;
    private final String fee = PersonBuilder.DEFAULT_FEE;
    private final String paid = PersonBuilder.DEFAULT_PAID;
    private final String owedAmount = PersonBuilder.DEFAULT_OWED_AMOUNT;

    @BeforeAll
    public static void setup() throws Exception {
        if (System.getenv("CI") != null) {
            // Running in CI, skip UI-related initialization
            return;
        }
        // Use FxToolkit to launch the JavaFX Application correctly
        FxToolkit.registerPrimaryStage();
        FxToolkit.setupApplication(MainApp::new);
    }

    @BeforeEach
    public void clearEverything() throws Exception {
        if (System.getenv("CI") != null) {
            // Running in CI, skip UI-related initialization
            return;
        }
        clickOn("#commandTextField");
        write(ClearCommand.COMMAND_WORD);
        push(KeyCode.ENTER);
    }

    public void addPerson() {
        //adds a person
        //Add a person to the list
        clickOn("#commandTextField");

        write(AddCommand.COMMAND_WORD);
        write(" " + PREFIX_NAME + name);
        write(" " + PREFIX_PHONE + phone);
        write(" " + PREFIX_EMAIL + email);
        write(" " + PREFIX_ADDRESS + address);
        write(" " + PREFIX_SCHEDULE + schedule);
        write(" " + PREFIX_SUBJECT + subject);
        write(" " + PREFIX_RATE + fee);
        write(" " + PREFIX_PAID + paid);
        write(" " + PREFIX_OWED_AMOUNT + owedAmount);

        push(KeyCode.ENTER);
    }

    public static boolean isNotCi() {
        return System.getenv("CI") == null;
    }

    @Test
    public void personHasAllDetailsShown() {
        // Skip test if running in headless mode
        Assumptions.assumeTrue(!GraphicsEnvironment.isHeadless(), "Skipping UI test in headless environment");
        addPerson();

        Subject testSubject = new Subject(subject);
        Schedule testSchedule = new Schedule(schedule);

        //verify visible
        verifyThat("#personList #personListView #name", Node::isVisible);
        verifyThat("#personList #personListView #subjectAndSchedule", Node::isVisible);
        verifyThat("#personList #personListView #phone", Node::isVisible);
        verifyThat("#personList #personListView #address", Node::isVisible);
        verifyThat("#personList #personListView #email", Node::isVisible);
        verifyThat("#personList #personListView #rate", Node::isVisible);
        verifyThat("#personList #personListView #paid", Node::isVisible);
        verifyThat("#personList #personListView #owedAmount", Node::isVisible);

        //verify contains text
        verifyThat("#personList #personListView #name", hasText(name));
        verifyThat("#personList #personListView #subjectAndSchedule", hasText(containsString(testSubject.toString())));
        verifyThat("#personList #personListView #subjectAndSchedule", hasText(containsString(testSchedule.toString())));
        verifyThat("#personList #personListView #phone", hasText(phone));
        verifyThat("#personList #personListView #address", hasText(address));
        verifyThat("#personList #personListView #email", hasText(email));
        verifyThat("#personList #personListView #rate", hasText(containsString(fee)));
        verifyThat("#personList #personListView #paid", hasText(containsString(paid)));
        verifyThat("#personList #personListView #owedAmount", hasText(containsString(owedAmount)));
    }
}
