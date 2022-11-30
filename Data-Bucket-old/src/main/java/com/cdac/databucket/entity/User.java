package com.cdac.databucket.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(name="name")
	@NotEmpty(message="*Please provide your name")
	private String name;
	
	@Column(name="email",unique=true,nullable=false)
	@Email(message="*Please provide a valid Email")
	@NotEmpty(message="*Please provide an email")
//	@Pattern(regexp="(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)(\\.[A-Za-z]{2,})$")
	private String email;
	
	@Column(name="password",nullable=false)
	@Length(min=5,message="*Your password must have at least 5 characters")
	@NotEmpty(message="*Please provide your password")
	private String password;
	
	 @Column(name = "role")
	 private String role;
	
	@OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinColumn(name="user_id")
	private List<File> files;
	
	@OneToMany(fetch = FetchType.LAZY,cascade=CascadeType.ALL)
	@JoinColumn(name="user_id")
	private Set<Folder> folders;
	
//	public void addFile(File theFile) {
//		if(files == null)
//		{
//			files = new ArrayList<>();
//		}
//		files.add(theFile);
//		theFile.setUser(this);
//		
//	}
//
//	public void addFolder(Folder newFolder) {
//		
//		if(folders == null) {
//			folders = new HashSet<>();
//		}
//		folders.add(newFolder);
//	}
	
	
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public Set<Folder> getFolders() {
		return folders;
	}

	public void setFolders(Set<Folder> folders) {
		this.folders = folders;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
	
	
}
