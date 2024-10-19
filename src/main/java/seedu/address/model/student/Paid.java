package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's paid tuition fee in the address book.
 */
public class Paid extends Fee {
    public static final String MESSAGE_CONSTRAINTS = "Paid " + Fee.MESSAGE_CONSTRAINTS;

    /**
     * Constructs a {@code Paid}.
     *
     * @param paid A valid paid.
     */
    public Paid(String paid) {
        super(paid);
    }

    /**
     * Constructs a {@code Paid}
     * with a default {@code value} of 0.0
     */
    public Paid() {
        super("0");
    }

    public static boolean isValidPaid(String test) {
        return Fee.isValidFee(test);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Paid)) {
            return false;
        }

        Paid otherPaid = (Paid) other;
        return value == otherPaid.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, Paid.class);
    }
}
