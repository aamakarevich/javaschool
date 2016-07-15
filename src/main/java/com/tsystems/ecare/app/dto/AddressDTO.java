package com.tsystems.ecare.app.dto;

import com.tsystems.ecare.app.model.Address;

/**
 * JSON serializable DTO containing Address data.
 */
public class AddressDTO {

    private Long id;

    private String city;
    private String address1;
    private String address2;

    public AddressDTO() {
        // empty constructor to instantiate object from JSON
    }

    public AddressDTO(Long id, String city, String address1, String address2) {
        this.id = id;
        this.city = city;
        this.address1 = address1;
        this.address2 = address2;
    }

    /**
     * Maps Address entity to AddressDTO object.
     *
     * @param address object to map from
     *
     * @return maped DTO
     */
    public static AddressDTO mapFromAddressEntity(Address address) {
        return new AddressDTO(address.getId(), address.getCity(), address.getAddress1(), address.getAddress2());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }
}
