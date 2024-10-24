package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.address.model.student.Student;

/**
 * Provides a handle to a student card in the student list panel.
 */
public class StudentCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String SUBJECTANDSCHEDULE_FIELD_ID = "#subjectAndSchedule";
    private static final String RATEANDPAIDAMOUNTANDOWEDAMOUNT_FIELD_ID = "#rateAndPaidAmountAndOwedAmount";


    private final Label idLabel;
    private final Label nameLabel;
    private final Label addressLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label subjectAndScheduleLabel;
    private final Label rateAndPaidAmountAndOwedAmountLabel;

    /**
     * Handle for Student Card
     * @param cardNode
     */
    public StudentCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        nameLabel = getChildNode(NAME_FIELD_ID);
        addressLabel = getChildNode(ADDRESS_FIELD_ID);
        phoneLabel = getChildNode(PHONE_FIELD_ID);
        emailLabel = getChildNode(EMAIL_FIELD_ID);
        subjectAndScheduleLabel = getChildNode(SUBJECTANDSCHEDULE_FIELD_ID);
        rateAndPaidAmountAndOwedAmountLabel = getChildNode(RATEANDPAIDAMOUNTANDOWEDAMOUNT_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getSubjectAndSchedule() {
        return subjectAndScheduleLabel.getText();
    }

    public String getRateAndPaidAmountAndOwedAmount() {
        return rateAndPaidAmountAndOwedAmountLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code student}.
     */
    public boolean equals(Student student) {
        return getName().equals(student.getName().fullName)
                && getAddress().equals(student.getAddress().value)
                && getPhone().equals(student.getPhone().value)
                && getEmail().equals(student.getEmail().value)
                && getSubjectAndSchedule().equals(student.getSubject().toString() + ": "
                + student.getSchedule().toString())
                && getRateAndPaidAmountAndOwedAmount().equals("Rate: $" + student.getRate().toString() + "/h, "
                + "Paid: $" + student.getPaidAmount().toString() + ", "
                + "Owed: $" + student.getOwedAmount().toString());
    }
}
