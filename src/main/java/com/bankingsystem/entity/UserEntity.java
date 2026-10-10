package com.bankingsystem.entity;

import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;   // unique + valid format

    @NotBlank(message = "Mobile number is required")
    @Size(min = 10, max = 10, message = "Mobile must be exactly 10 digits")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile must contain only digits")
    @Column(nullable = false)
    private String mobile;  // repeat allowed but fixed length

    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password; // BCrypt encoded

    // Each user has one role
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private RoleEntity role;

    // One user → Many forms
    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL)
    private List<FormEntity> forms;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RoleEntity getRole() {
		return role;
	}

	public void setRole(RoleEntity role) {
		this.role = role;
	}

	public List<FormEntity> getForms() {
		return forms;
	}

	public void setForms(List<FormEntity> forms) {
		this.forms = forms;
	}

   
}
