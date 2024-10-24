package seedu.address.ui.testutil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import guitests.guihandles.ResultDisplayHandle;
import guitests.guihandles.StudentCardHandle;
import guitests.guihandles.StudentListPanelHandle;
import seedu.address.model.student.Student;

/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(StudentCardHandle expectedCard, StudentCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getAddress(), actualCard.getAddress());
        assertEquals(expectedCard.getEmail(), actualCard.getEmail());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getPhone(), actualCard.getPhone());
        assertEquals(expectedCard.getSubjectAndSchedule(), actualCard.getSubjectAndSchedule());
        assertEquals(expectedCard.getRateAndPaidAmountAndOwedAmount(), actualCard.getRateAndPaidAmountAndOwedAmount());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedStudent}.
     */
    public static void assertCardDisplaysStudent(Student expectedStudent, StudentCardHandle actualCard) {
        assertEquals(expectedStudent.getName().fullName, actualCard.getName());
        assertEquals(expectedStudent.getPhone().value, actualCard.getPhone());
        assertEquals(expectedStudent.getEmail().value, actualCard.getEmail());
        assertEquals(expectedStudent.getAddress().value, actualCard.getAddress());
        assertEquals((expectedStudent.getSubject().toString() + ": " + expectedStudent.getSchedule().toString()) ,
                actualCard.getSubjectAndSchedule());
        assertEquals(("Rate: $" + expectedStudent.getRate().toString() + "/h, "
            + "Paid: $" + expectedStudent.getPaidAmount().toString() + ", "
            + "Owed: $" + expectedStudent.getOwedAmount().toString()),
                actualCard.getRateAndPaidAmountAndOwedAmount());
    }

    /**
     * Asserts that the list in {@code studentListPanelHandle} displays the details of {@code students} correctly and
     * in the correct order.
     */
    public static void assertListMatching(StudentListPanelHandle studentListPanelHandle, Student... students) {
        for (int i = 0; i < students.length; i++) {
            studentListPanelHandle.navigateToCard(i);
            assertCardDisplaysStudent(students[i], studentListPanelHandle.getStudentCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code studentListPanelHandle} displays the details of {@code students} correctly and
     * in the correct order.
     */
    public static void assertListMatching(StudentListPanelHandle studentListPanelHandle, List<Student> students) {
        assertListMatching(studentListPanelHandle, students.toArray(new Student[0]));
    }

    /**
     * Asserts the size of the list in {@code studentListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(StudentListPanelHandle studentListPanelHandle, int size) {
        int numberOfPeople = studentListPanelHandle.getListSize();
        assertEquals(size, numberOfPeople);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
