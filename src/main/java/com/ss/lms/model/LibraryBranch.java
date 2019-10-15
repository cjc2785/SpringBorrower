package com.ss.lms.model;

public class LibraryBranch {
	
	private int branchId;
	private String branchName;
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
