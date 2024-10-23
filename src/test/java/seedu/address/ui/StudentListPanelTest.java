package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalStudents.getTypicalStudents;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysStudent;

import org.junit.jupiter.api.Test;

import guitests.guihandles.StudentCardHandle;
import guitests.guihandles.StudentListPanelHandle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.student.Address;
import seedu.address.model.student.Email;
import seedu.address.model.student.Name;
import seedu.address.model.student.OwedAmount;
import seedu.address.model.student.PaidAmount;
import seedu.address.model.student.Phone;
import seedu.address.model.student.Rate;
import seedu.address.model.student.Schedule;
import seedu.address.model.student.Student;
import seedu.address.model.student.Subject;

public class StudentListPanelTest extends GuiUnitTest {
    private static final ObservableList<Student> TYPICAL_STUDENTS =
            FXCollections.observableList(getTypicalStudents());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 3000;

    private final SimpleObjectProperty<Student> selectedStudent = new SimpleObjectProperty<>();
    private StudentListPanelHandle studentListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_STUDENTS);

        for (int i = 0; i < TYPICAL_STUDENTS.size(); i++) {
            studentListPanelHandle.navigateToCard(TYPICAL_STUDENTS.get(i));
            Student expectedStudent = TYPICAL_STUDENTS.get(i);
            StudentCardHandle actualCard = studentListPanelHandle.getStudentCardHandle(i);

            assertCardDisplaysStudent(expectedStudent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    /**
     * Verifies that creating and deleting large number of students in {@code StudentListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Student> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of student cards exceeded time limit");
    }

    /**
     * Returns a list of students containing {@code studentCount} students that is used to populate the
     * {@code StudentListPanel}.
     */
    private ObservableList<Student> createBackingList(int studentCount) {
        ObservableList<Student> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < studentCount; i++) {
            Name name = new Name(i + "a");
            Phone phone = new Phone("81111111");
            Email email = new Email("a@aa");
            Address address = new Address("a");
            Schedule schedule = new Schedule("Sunday-1000-1200");
            Subject subject = new Subject("GP");
            Rate rate = new Rate("300");
            PaidAmount paidAmount = new PaidAmount("100");
            OwedAmount owedAmount = new OwedAmount("100");
            Student student = new Student(name, phone, email, address, schedule, subject, rate,
                    paidAmount, owedAmount);
            backingList.add(student);
        }
        return backingList;
    }

    /**
     * Initializes {@code studentListPanelHandle} with a {@code StudentListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code StudentListPanel}.
     */
    private void initUi(ObservableList<Student> backingList) {
        StudentListPanel studentListPanel =
                new StudentListPanel(backingList);
        uiPartExtension.setUiPart(studentListPanel);

        studentListPanelHandle = new StudentListPanelHandle(getChildNode(studentListPanel.getRoot(),
                StudentListPanelHandle.STUDENT_LIST_VIEW_ID));
    }
}
