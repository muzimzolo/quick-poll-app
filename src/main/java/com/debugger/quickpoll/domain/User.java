package com.debugger.quickpoll.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import javax.validation.constraints.NotEmpty;
@Entity
@Table(name="USERS")
public class User {
        @Id
        @GeneratedValue
        @Column(name="USER_ID")
        private Long id;
        
        @Column(name="USERNAME")
        @NotEmpty
        private String username;
        
        @Column(name="PASSWORD")
        @NotEmpty
        @JsonIgnore
        private String password;
        
        @Column(name="FIRST_NAME")
        @NotEmpty
        private String firstName;
        
        @Column(name="LAST_NAME")
        @NotEmpty
        private String lastName;
        
        @Column(name="ADMIN", columnDefinition="char(3)")
        @Type(type="yes_no")
        @NotEmpty
        private boolean isAdmin;
        
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public boolean isAdmin() {
			return isAdmin;
		}
		public void setAdmin(boolean isAdmin) {
			this.isAdmin = isAdmin;
		}
        
        
}