package com.alaska.todoapi.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.alaska.todoapi.entity.validationInterface.EditUserValidationInterface;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "user", uniqueConstraints = @UniqueConstraint(name = "unique_user_email", columnNames = "email_address"))
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "first_name")
        @NotBlank(message = "First name is required", groups = EditUserValidationInterface.class)
        private String firstName;

        @Column(name = "last_name")
        @NotBlank(message = "Last name is required", groups = EditUserValidationInterface.class)
        private String lastName;

        @Column(name = "email_address", unique = true)
        @NotBlank(message = "Email address is required")
        @Email(message = "Email address is not valid. Please enter a valid email address")
        private String emailAddress;

        @Column(name = "phone_number")
        private String phonenumber;

        @Embedded
        @Valid
        private Address address;

        @CreationTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "created_at")
        private Date createdAt;

        @UpdateTimestamp
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "updated_at")
        private Date updatedAt;

        @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
        @Getter(AccessLevel.NONE)
        List<Todo> todos = new ArrayList<Todo>();
}
