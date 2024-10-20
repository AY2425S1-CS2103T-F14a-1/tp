package seedu.address.model.student;

import java.util.Objects;

/**
 * Represents a Student's paid tuition fee in the address book.
 */
public class PaidAmount extends Fee {
    public static final String MESSAGE_CONSTRAINTS = "PaidAmount " + Fee.MESSAGE_CONSTRAINTS;

    /**
     * Constructs a {@code PaidAmount}.
     *
     * @param paid A valid paid.
     */
    public PaidAmount(String paid) {
        super(paid);
    }

    /**
     * Constructs a {@code PaidAmount}
     * with a default {@code value} of 0.0
     */
    public PaidAmount() {
        super("0");
    }

    public static boolean isValidPaidAmount(String test) {
        return Fee.isValidFee(test);
    }

    public PaidAmount updateValue(double value) {
        return new PaidAmount(Double.toString(super.value + value));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PaidAmount)) {
            return false;
        }

        PaidAmount otherPaidAmount = (PaidAmount) other;
        return value == otherPaidAmount.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, PaidAmount.class);
    }
}
