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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sun.istack.NotNull;

@Entity
@Table(name="folder", schema = "public")
public class Folder {

	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "folder_id")
	private Long id;
	
	@Column(name = "folder_name")
	@NotNull
	private String folderName;
	
	 @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
	            CascadeType.DETACH, CascadeType.REFRESH})
	 @JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
	            CascadeType.DETACH, CascadeType.REFRESH})
	@JoinColumn(name = "parent_id")
	private Folder parent;
	
	@OneToMany(mappedBy = "parent")
	private Set<Folder> childs;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "folder_id")
	private List<File> files;
	
	@OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
	private List<SharedFolder> sharedFolder;
	
	 @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL)
	private List<RandomFolderId> randomFolder;
	
	public void addChildFolder(Folder theChild) {
		if(childs == null) {
			childs = new HashSet<>();
		}
		childs.add(theChild);
		theChild.setParent(this);
	}
	
	public void addFile(File newFile) {
		if(files == null) {
			files = new ArrayList<>();
		}
		files.add(newFile);
		newFile.setFolder(this);
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFolderName() {
		return folderName;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Folder getParent() {
		return parent;
	}

	public void setParent(Folder parent) {
		this.parent = parent;
	}

	public Set<Folder> getChilds() {
		return childs;
	}

	public void setChilds(Set<Folder> childs) {
		this.childs = childs;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<SharedFolder> getSharedFolder() {
		return sharedFolder;
	}

	public void setSharedFolder(List<SharedFolder> sharedFolder) {
		this.sharedFolder = sharedFolder;
	}

	public List<RandomFolderId> getRandomFolder() {
		return randomFolder;
	}

	public void setRandomFolder(List<RandomFolderId> randomFolder) {
		this.randomFolder = randomFolder;
	}
	
	
}
