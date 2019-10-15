package com.ss.lms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_author")
public class Author {
	
    @Id
    private Integer authorId;

    @Column
    private  String authorName;
    
    
    public Author() { }

    public Author(Integer auth, String authName){
    	authorId = auth;
    	authorName = authName;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
}
