package model;

import java.time.LocalDateTime;
/**
 * appointments class
 */

public class appointments {
    /**
     *appointmentId
     */
    private int appointmentId;
    /**
     *title
     */
    private String title;
    /**
     *description
     */
    private String description;
    /**
     *location
     */
    private String location;
    /**
     *type
     */
    private String type;
    /**
     *start
     */
    private LocalDateTime start;
    /**
     *end
     */
    private LocalDateTime end;
    /**
     *customerId
     */
    private int customerId;
    /**
     *userId
     */
    private int userId;
    /**
     *contactId
     */
    private int contactId;

    /**
     *
     * @param appointmentId appointmentId
     * @param title title
     * @param description description
     * @param location location
     * @param type type
     * @param start start
     * @param end end
     * @param customerId customerId
     * @param userId userId
     * @param contactId contactId
     */

    public appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * return appointmentId
     * @return appointmentId
     */
    public int getAppointmentId() {
        return appointmentId;
    }
    /**
     * return title
     * @return title
     */
    public String getTitle() {
        return title;
    }
    /**
     *return description
     * @return description
     */
    public String getDescription() {
        return description;
    }
    /**
     *return location
     * @return location
     */
    public String getLocation() {
        return location;
    }
    /**
     *return type
     * @return location
     */
    public String getType() {
        return type;}
    /**
     *return start
     * @return start
     */
    public LocalDateTime getStart() {
        return start;
    }
    /**
     *return end
     * @return start
     */
    public LocalDateTime getEnd() {
        return end;
    }
    /**
     *return customerId
     * @return customerId
     */
    public int getCustomerId() {
        return customerId;
    }
    /**
     *return userId
     * @return userId
     */
    public int getUserId() {
        return userId;
    }
    /**
     *return contactId
     * @return userId
     */
    public int getContactId() {
        return contactId;
    }

    /**
     *  toString method to translate data in the combobox to a string
     * @return translated data
     */
    @Override
    public String toString(){
        return(Integer.toString(userId));
    }

}
