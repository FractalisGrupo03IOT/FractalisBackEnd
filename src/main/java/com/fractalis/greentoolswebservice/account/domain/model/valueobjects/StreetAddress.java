package com.fractalis.greentoolswebservice.account.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
public record StreetAddress(
        String street,
        String number,
        String city,
        String zipCode,
        String country
)
{
    public StreetAddress()
    {
        this(null,null,null,null,null);
    }

    public StreetAddress(String street, String number, String city, String zipCode, String country) {
        if (street == null || street.isBlank()) {
            throw new IllegalArgumentException("Street cannot be null or empty");
        }
        if (number == null || number.isBlank()) {
            throw new IllegalArgumentException("Number cannot be null or empty");
        }
        if (city == null || city.isBlank()) {
            throw new IllegalArgumentException("City cannot be null or empty");
        }
        if (zipCode == null || zipCode.isBlank()) {
            throw new IllegalArgumentException("ZipCode cannot be null or empty");
        }
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or empty");
        }

        this.street = street;
        this.number = number;
        this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }
}
