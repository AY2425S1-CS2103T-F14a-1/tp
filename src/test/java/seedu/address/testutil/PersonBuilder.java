package seedu.address.testutil;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.OwedAmount;
import seedu.address.model.person.Paid;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Rate;
import seedu.address.model.person.Schedule;
import seedu.address.model.person.Subject;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String DEFAULT_SCHEDULE = "Sunday-1800-1900";
    public static final String DEFAULT_SUBJECT = "Mathematics";
    public static final String DEFAULT_FEE = "300";
    public static final String DEFAULT_PAID = "600";
    public static final String DEFAULT_OWED_AMOUNT = "300";

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Schedule schedule;
    private Subject subject;
    private Rate rate;
    private Paid paid;
    private OwedAmount owedAmount;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        schedule = new Schedule(DEFAULT_SCHEDULE);
        subject = new Subject(DEFAULT_SUBJECT);
        rate = new Rate(DEFAULT_FEE);
        paid = new Paid(DEFAULT_PAID);
        owedAmount = new OwedAmount(DEFAULT_OWED_AMOUNT);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        schedule = personToCopy.getSchedule();
        subject = personToCopy.getSubject();
        rate = personToCopy.getRate();
        paid = personToCopy.getPaid();
        owedAmount = personToCopy.getOwedAmount();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }


    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Schedule} of the {@code Person} that we are building.
     */
    public PersonBuilder withSchedule(String schedule) {
        this.schedule = new Schedule(schedule);
        return this;
    }

    /**
     * Sets the {@code Subject} of the {@code Person} that we are building.
     */
    public PersonBuilder withSubject(String subject) {
        this.subject = new Subject(subject);
        return this;
    }

    /**
     * Sets the {@code Rate} of the {@code Person} that we are building.
     */
    public PersonBuilder withRate(String rate) {
        this.rate = new Rate(rate);
        return this;
    }

    /**
     * Sets the {@code Paid} of the {@code Person} that we are building.
     */
    public PersonBuilder withPaid(String paid) {
        this.paid = new Paid(paid);
        return this;
    }

    /**
     * Sets the {@code OwedAmount} of the {@code Person} that we are building.
     */
    public PersonBuilder withOwedAmount(String owedAmount) {
        this.owedAmount = new OwedAmount(owedAmount);
        return this;
    }

    public Person build() {
        return new Person(name, phone, email, address, schedule, subject, rate, paid, owedAmount);
    }

}
