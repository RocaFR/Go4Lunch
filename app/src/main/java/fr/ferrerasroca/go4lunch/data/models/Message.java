package fr.ferrerasroca.go4lunch.data.models;

import java.util.Date;

public class Message {

    private User sender;
    private String message;
    private String urlImage;
    private Date date;

    public Message() { }

    public Message(User sender, Date date, String message) {
        this.sender = sender;
        this.date = date;
        this.message = message;
    }

    public Message(User sender, String message, String urlImage, Date date) {
        this.sender = sender;
        this.message = message;
        this.urlImage = urlImage;
        this.date = date;
    }

    // #######
    // GETTERS
    // #######

    public User getSender() { return sender; }
    public String getMessage() { return message; }
    public Date getDate() { return date; }
    public void setUrlImage(String urlImage) { this.urlImage = urlImage; }

    // #######
    // SETTERS
    // #######

    public void setSender(User sender) { this.sender = sender; }
    public void setMessage(String message) { this.message = message; }
    public String getUrlImage() { return this.urlImage; }
}
