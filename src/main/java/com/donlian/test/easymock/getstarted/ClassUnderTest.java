package com.donlian.test.easymock.getstarted;

public class ClassUnderTest {
	 private Collaborator listener;
	    // ...
	    public void setListener(Collaborator listener) {
	        this.listener = listener;
	    }
	    public void addDocument(String title, byte[] document) { 
	        // ... 
	    }
}
