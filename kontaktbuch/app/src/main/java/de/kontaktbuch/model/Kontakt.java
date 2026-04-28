package de.kontaktbuch.model;

/**
 * This class represents a contact with a full name, call name (nickname), and email address.
 * It provides a constructor for initializing these fields and getter methods to access them.
 * The toString() method is overridden to provide a string representation of the contact in the format:
 * "Full Name (Call Name) <Email>".
 */
public class Kontakt
{

    // #region vars

    // --------------------------

    /**
     * The full name of the contact. This is a required field and should not be null or empty.
     */
    private final String fullName;

    /**
     * The call name (nickname) of the contact. This is a required field and should not be null or empty.
     */
    private final String callName;

    /**
     * The email address of the contact. This is a required field and should not be null or empty.
     */
    private final String email;

    /**
     * The birthday of the contact. This is an optional field and can be null or empty if not provided.
     * The format of the birthday should be YYYY-MM-DD
     */
    private final String birthday;

    // --------------------------

    // #endregion vars

    // #region ctor

    // --------------------------

    /**
    * Initializes a new instance of the Kontakt class with the specified full name, call name, email, and birthday.
    * @param fullName The full name of the contact. Must not be null or empty.
    * @param callName The call name (nickname) of the contact. Must not be null or empty.
    * @param email The email address of the contact. Must not be null or empty.
    * @param birthday The birthday of the contact. Can be null or empty if not provided. The format should be YYYY-MM-DD.
    */
    public
    Kontakt(String fullName, String callName, String email, String birthday)
    {
        this.fullName = fullName;
        this.callName = callName;
        this.email    = email;
        this.birthday = birthday;
    }

    // --------------------------

    // #endregion ctor

    // #region get/set

    // --------------------------

    /**
     * Returns the full name of the contact.
     * @return The full name of the contact.
     */
    public String
    getFullName()
    {
        return fullName;
    }

    // --------------------------

    /**
     * Returns the call name (nickname) of the contact.
     * @return The call name of the contact.
     */
    public String
    getCallName()
    {
        return callName;
    }

    // --------------------------

    /**
     * Returns the email address of the contact.
     * @return The email address of the contact.
     */
    public String
    getEmail()
    {
        return email;
    }

    // --------------------------

    /**
     * Returns the birthday of the contact.
     * @return The birthday of the contact in the format YYYY-MM-DD, or null if not provided.
     */
    public String
    getBirthday()
    {
        return birthday;
    }

    // --------------------------

    // #endregion get/set

    // #region methods

    // --------------------------

    /**
    * Returns a string representation of the contact in the format:
    * "Full Name (Call Name) <Email>".
    * @return A string representation of the contact.
    */
    @Override
    public String
    toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(fullName);
        sb.append(" (");
        sb.append(callName);
        sb.append(") <");
        sb.append(email);
        sb.append(">");
        return sb.toString();
    }

    // --------------------------

    // #endregion methods
}