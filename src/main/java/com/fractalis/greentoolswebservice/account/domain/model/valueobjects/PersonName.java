package com.fractalis.greentoolswebservice.account.domain.model.valueobjects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record PersonName(
        @Column(name = "firstName")
        String firstName,
        @Column(name = "lastName")
        String lastName)
{
    public PersonName()
    {
        this(null,null);
    }

    public PersonName(String firstName, String lastName)
    {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("firstName is blank");
        }
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName is blank");
        }

        this.firstName = firstName;
        this.lastName = lastName;
    }
}

