package org.skillforge.dto;

public class signupRequestDTO {
    private String email;
    private String Password;

    public signupRequestDTO(String email, String password) {
        this.email = email;
        Password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "signupRequestDTO{" +
                "email='" + email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
