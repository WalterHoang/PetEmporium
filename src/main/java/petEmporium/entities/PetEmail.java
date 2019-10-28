package petEmporium.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
@Table(name = "email")
public class PetEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "", name = "Email Id", required = false, value = "Auto-generated ID for email record")
    private int emailId;
    @ApiModelProperty(notes = "", name = "User Email", required = true, example = "example@example.org", value = "User's email address")
    private String userEmail;
    @ApiModelProperty(notes = "", name = "User role", required = false, example = "Customer", value = "User's role used to determine permissions")
    private String userRole;

    //One to One relationship with the Email ID as a the foreign key, so that the PetEmail can be extended for auth.
    @OneToOne(mappedBy = "email")
    @ApiModelProperty(notes = "", name = "Pet User", required = true, value = "User's personal Information")
    private PetUsers petUser;

    public PetEmail() {
    }//For Hibernate

    public PetEmail(String userEmail, String userRole) {
        this.userEmail = userEmail;
        this.userRole = userRole;
    }

    public PetEmail(int emailId, String userEmail, String userRole, PetUsers petUser) {
        this.emailId = emailId;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.petUser = petUser;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
