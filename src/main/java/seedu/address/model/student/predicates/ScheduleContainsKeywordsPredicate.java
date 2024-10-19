package seedu.address.model.student.predicates;

import java.util.Collection;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.student.Days;
import seedu.address.model.student.Student;

/**
 * Tests that a {@code Student}'s {@code Name} matches any of the keywords given.
 */
public class ScheduleContainsKeywordsPredicate extends AttributeContainsKeywordsPredicate<Days> {

    public ScheduleContainsKeywordsPredicate(Collection<Days> keywords) {
        super(keywords);
    }

    @Override
    public boolean test(Student student) {
        return getKeywords().stream()
                .anyMatch(keyword -> student.getSchedule().isOn(keyword));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ScheduleContainsKeywordsPredicate)) {
            return false;
        }

        ScheduleContainsKeywordsPredicate otherScheduleContainsKeywordsPredicate = (
                ScheduleContainsKeywordsPredicate) other;
        return getKeywords().equals(otherScheduleContainsKeywordsPredicate.getKeywords());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("keywords", getKeywords()).toString();
    }
}