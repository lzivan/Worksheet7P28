package com.example.animation;

public class Email {
    public int id;
    public String objectName;

    // constructor for adding sample data
    public Email(int newId, String newname){

        setName( newname );

        setId( newId );

    }

    public void setId( int newId ) {
        id = newId;
    }

    public int getId( ) {
        return id;
    }

    public String getName( ) {
        return objectName;
    }

    public void setName( String newName ) {
        objectName = newName;
    }





}
