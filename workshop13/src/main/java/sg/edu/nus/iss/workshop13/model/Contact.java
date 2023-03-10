package sg.edu.nus.iss.workshop13.model;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public class Contact {
    @NotNull(message = "Name cannot be null")
    @Size(min=3,max=64, message = "Name must be between 3 and 64 characters")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    @Size(min=7,message = "Phone number must be at least 7 digit")
    private String phoneNumber;

    private String id;

    @Past(message = "Date of birth must not be future date")
    @NotNull(message = "Date of birth cannot be null")
    @DateTimeFormat(pattern = "MM-dd-yyyy")
    private LocalDate dateOfBirth;

    @Min(value=10,message = "Must be above 10 years old")
    @Max(value=100,message = "Must be below 100 years old")
    private int age;

    public Contact() {
        this.id = generateId(8);
    }

    public Contact(String name, String email, String phoneNumber, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.dateOfBirth = dateOfBirth;
    }

    public Contact(String name, String email, String phoneNumber, String id, LocalDate dateOfBirth) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.id = generateId(8);
        this.dateOfBirth = dateOfBirth;
    }

    // synchronized => blocking code, to guarantee unique ID being generated, not async
    private synchronized String generateId(int numOfChar){
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        // String rVal = UUID.randomUUID().toString().replace("-", "").substring(0,8);
        while(sb.length() < numOfChar){
            sb.append(Integer.toHexString(rand.nextInt()));
        }

        return sb.toString().substring(0,numOfChar);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(LocalDate dateOfBirth) {
        int calculateAge = 0;
        if(dateOfBirth != null){
            calculateAge = Period.between(dateOfBirth, LocalDate.now()).getYears();
        }
        this.dateOfBirth = dateOfBirth;
        this.age = calculateAge;
    }
    public int getAge() {
        return age;
    }
}
