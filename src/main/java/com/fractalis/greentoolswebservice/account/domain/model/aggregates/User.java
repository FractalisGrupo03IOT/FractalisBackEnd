package com.fractalis.greentoolswebservice.account.domain.model.aggregates;

import com.fractalis.greentoolswebservice.account.domain.model.valueobjects.EmailAddress;
import com.fractalis.greentoolswebservice.account.domain.model.valueobjects.PersonName;
import com.fractalis.greentoolswebservice.account.domain.model.valueobjects.StreetAddress;
import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;

    @Embedded
    private PersonName name;

    @Embedded
    private EmailAddress email;

    @Column(name="profileImage")
    private String profileImage;


    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street", column = @Column(name = "address_street")),
            @AttributeOverride(name = "city", column = @Column(name = "address_city")),
            @AttributeOverride(name = "zipCode", column = @Column(name = "address_zipCode")),
            @AttributeOverride(name = "country", column = @Column(name = "address_country"))
    })
    private StreetAddress address;


    public User(String firstName, String lastName, String email, String street, String number, String city, String zipCode, String country, String profileImage)
    {
        this.name = new PersonName(firstName, lastName);
        this.email = new EmailAddress(email);
        this.address = new StreetAddress(street,number,city,zipCode,country);
        this.profileImage = profileImage;
    }

    public void updateName(String firstName, String lastName)
    {
        this.name = new PersonName(firstName, lastName);
    }

    public void updateEmail(String email)
    {
        this.email = new EmailAddress(email);
    }

    public void updateAddress(String street, String number, String city, String zipCode, String country)
    {
        this.address = new StreetAddress(street, number,city,zipCode,country);
    }

    public void updateProfileImage(String profileImage){
        this.profileImage = profileImage;
    }
}
