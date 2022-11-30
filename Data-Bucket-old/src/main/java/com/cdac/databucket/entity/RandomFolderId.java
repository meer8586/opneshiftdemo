package com.cdac.databucket.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

@Entity
@SequenceGenerator(name = "seq_generator", sequenceName = "seq_generator", initialValue = 123456)
public class RandomFolderId {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seq_generator")
	private long id;
	
	@ManyToOne
	private Folder folder;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Folder getFolder() {
		return folder;
	}

	public void setFolder(Folder folder) {
		this.folder = folder;
	}
	
	
}
