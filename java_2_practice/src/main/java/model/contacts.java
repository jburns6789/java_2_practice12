package model;

/**
 * contacts class
 */

public class contacts {
    /**
     * contactId
     */
    private int contactId;
    /**
     *contact name
     */
    private String contactName;
    /**
     *email
     */
    private String email;

    /**
     *
     * @param contactId contactId
     * @param contactName contact name
     * @param email email
     */

    public contacts(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * returns contact name
     * @return contact name
     */
    public String getContactName() {return contactName;}
    /**
     * returns email
     * @return email
     */
    public String getEmail() {return email;}
    /**
     * returns contactId
     * @return contactId
     */
    public int getContactId() {return contactId;}

    /**
     * toString method to translate data into a string format
     * @return translated comboboxData
     */
    @Override
    public String toString(){
        return(contactName + " " + email);
    }
}
