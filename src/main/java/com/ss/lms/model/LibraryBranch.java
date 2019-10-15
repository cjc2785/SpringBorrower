package com.ss.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_library_branch")
public class LibraryBranch {
	
	@Id
	@Column(name="branchId")
	private int branchId;
	
	@Column(name="branchName")
	private String branchName;
	
	@Column(name="branchAddress")
	private String branchAddress;
	
	public LibraryBranch() {};
	
	public LibraryBranch(int Id, String name, String address) {
		branchId = Id;
		branchName = name;
		branchAddress = address;
	}
	
	
	public int getBranchId() {
		return branchId;
	}
	public void setBranchId(int branchId) {
		this.branchId = branchId;
	}
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchAddress() {
		return branchAddress;
	}
	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}
}
