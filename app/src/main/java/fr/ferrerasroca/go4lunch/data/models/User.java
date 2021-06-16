package fr.ferrerasroca.go4lunch.data.models;

import androidx.annotation.Nullable;

/**
 * <p>A user is a collaborator working in our company.</p>
 */
public class User {

    private String uid;
    private String username;
    private String email;
    private String bio;
    private String job;
    @Nullable
    private String profilePictureUrl;
    private String placeIDChoice = null;

    /**
     * <p>Empty Firestore constructor.<br>
     * Do not use.</p>
     */
    public User() { }

    /**
     * <p>User constructor, must be used to create any new user.</p>
     * @param uid unique user id.
     * @param username last and first name.
     * @param email user email used to communicate and manage some account features.
     * @param profilePictureUrl user profile picture, it can be null.
     */
    public User(String uid, String username, String email, @Nullable String profilePictureUrl) {
        this.uid = uid;
        this.username = username;
        this.email = email;
        this.profilePictureUrl = profilePictureUrl;
    }

    //#########
    // GETTERS
    //#########

    public String getUid() { return this.uid; }
    public String getUsername() { return this.username; }
    public String getEmail() { return this.email; }
    public String getBio() { return this.bio; }
    public String getJob() { return this.job; }
    @Nullable
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public String getPlaceIDChoice() { return placeIDChoice; }

    // #######
    // SETTERS
    // #######

    public void setUid(String uid) { this.uid = uid; }
    public void setUsername(String username) { this.username = username; }
    public void setEmail(String email) { this.email = email; }
    public void setBio(String bio) { this.bio = bio; }
    public void setJob(String job) { this.job = job; }
    public void setProfilePictureUrl(@Nullable String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    public void setPlaceIDChoice(String placeIDChoice) { this.placeIDChoice = placeIDChoice; }
}
