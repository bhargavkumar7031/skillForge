package org.skillforge.dto;

public class authRequestDTO {
    private String email;
    private String password;

    public authRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "signupRequestDTO{" +
                "email='" + email + '\'' +
                ", Password='" + password + '\'' +
                '}';
    }
}
