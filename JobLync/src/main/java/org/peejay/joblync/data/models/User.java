package org.peejay.joblync.data.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String firstName;
    private String lastName;
    
    @Column(unique = true, nullable = false)
    private String email;
    
    private String phoneNumber;
    private String password;
    private LocalDateTime dateJoined = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String verificationToken;
    
    @ElementCollection
    @CollectionTable(name = "user_skills", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "skill")
    private List<String> skills = new ArrayList<>();
    
    @ElementCollection
    @CollectionTable(name = "user_certifications", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "certification")
    private List<String> certifications = new ArrayList<>();
    
    private String department;
    private String position;
    private LocalDateTime lastPromotionDate;
    
    // Additional employee information
    private String employeeId;
    private LocalDateTime dateOfBirth;
    private String address;
    private String emergencyContactName;
    private String emergencyContactPhone;
    private String bankAccountNumber;
    private String bankName;
    private LocalDateTime hireDate;
    private LocalDateTime terminationDate;
    private String managerId; // ID of the user's manager
    
    @Enumerated(EnumType.STRING)
    private EmploymentStatus employmentStatus = EmploymentStatus.ACTIVE;
    
    private Double salary;
    private String jobLevel; // Junior, Mid-level, Senior, Lead, etc.
    
    // Explicit setters to resolve compilation issue
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public void setDateJoined(LocalDateTime dateJoined) {
        this.dateJoined = dateJoined;
    }
    
    public void setRole(Role role) {
        this.role = role;
    }
    
    public void setVerificationToken(String verificationToken) {
        this.verificationToken = verificationToken;
    }
    
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    
    public void setCertifications(List<String> certifications) {
        this.certifications = certifications;
    }
    
    public void setDepartment(String department) {
        this.department = department;
    }
    
    public void setPosition(String position) {
        this.position = position;
    }
    
    public void setLastPromotionDate(LocalDateTime lastPromotionDate) {
        this.lastPromotionDate = lastPromotionDate;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }
    
    public void setEmergencyContactPhone(String emergencyContactPhone) {
        this.emergencyContactPhone = emergencyContactPhone;
    }
    
    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    
    public void setHireDate(LocalDateTime hireDate) {
        this.hireDate = hireDate;
    }
    
    public void setTerminationDate(LocalDateTime terminationDate) {
        this.terminationDate = terminationDate;
    }
    
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    
    public void setEmploymentStatus(EmploymentStatus employmentStatus) {
        this.employmentStatus = employmentStatus;
    }
    
    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public void setJobLevel(String jobLevel) {
        this.jobLevel = jobLevel;
    }
    
    // Explicit getters to resolve compilation issue
    public String getFirstName() {
        return firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getPassword() {
        return password;
    }
    
    public LocalDateTime getDateJoined() {
        return dateJoined;
    }
    
    public Role getRole() {
        return role;
    }
    
    public String getVerificationToken() {
        return verificationToken;
    }
    
    public List<String> getSkills() {
        return skills;
    }
    
    public List<String> getCertifications() {
        return certifications;
    }
    
    public String getDepartment() {
        return department;
    }
    
    public String getPosition() {
        return position;
    }
    
    public LocalDateTime getLastPromotionDate() {
        return lastPromotionDate;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getEmergencyContactName() {
        return emergencyContactName;
    }
    
    public String getEmergencyContactPhone() {
        return emergencyContactPhone;
    }
    
    public String getBankAccountNumber() {
        return bankAccountNumber;
    }
    
    public String getBankName() {
        return bankName;
    }
    
    public LocalDateTime getHireDate() {
        return hireDate;
    }
    
    public LocalDateTime getTerminationDate() {
        return terminationDate;
    }
    
    public String getManagerId() {
        return managerId;
    }
    
    public EmploymentStatus getEmploymentStatus() {
        return employmentStatus;
    }
    
    public Double getSalary() {
        return salary;
    }
    
    public String getJobLevel() {
        return jobLevel;
    }
}