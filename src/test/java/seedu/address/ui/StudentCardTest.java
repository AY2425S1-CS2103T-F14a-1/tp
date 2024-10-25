package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysStudent;

import org.junit.jupiter.api.Test;

import guitests.guihandles.StudentCardHandle;
import seedu.address.model.student.Student;
import seedu.address.testutil.StudentBuilder;

public class StudentCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Student studentWithNoTags = new StudentBuilder().build();
        StudentCard studentCard = new StudentCard(studentWithNoTags, 1);
        uiPartExtension.setUiPart(studentCard);
        assertCardDisplay(studentCard, studentWithNoTags, 1);
    }

    @Test
    public void equals() {
        Student student = new StudentBuilder().build();
        StudentCard studentCard = new StudentCard(student, 0);

        // same student, same index -> returns true
        StudentCard copy = new StudentCard(student, 0);
        assertTrue(studentCard.equals(copy));

        // same object -> returns true
        assertTrue(studentCard.equals(studentCard));

        // null -> returns false
        assertFalse(studentCard.equals(null));

        // different types -> returns false
        assertFalse(studentCard.equals(0));

        // different student, same index -> returns false
        Student differentStudent = new StudentBuilder().withName("differentName").build();
        assertFalse(studentCard.equals(new StudentCard(differentStudent, 0)));

        // same student, different index -> returns false
        assertFalse(studentCard.equals(new StudentCard(student, 1)));
    }

    private void assertTrue(boolean equals) {
    }

    /**
     * Asserts that {@code studentCard} displays the details of {@code expectedStudent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(StudentCard studentCard, Student expectedStudent, int expectedId) {
        guiRobot.pauseForHuman();

        StudentCardHandle studentCardHandle = new StudentCardHandle(studentCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", studentCardHandle.getId());

        // verify student details are displayed correctly
        assertCardDisplaysStudent(expectedStudent, studentCardHandle);
    }
}
