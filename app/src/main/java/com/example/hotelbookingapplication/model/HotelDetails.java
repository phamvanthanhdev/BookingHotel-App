package com.example.hotelbookingapplication.model;

import java.math.BigDecimal;

public class HotelDetails {
    private Long id;
    private String name;
    private String address;
    private String city;
    private double rating;
    private BigDecimal price;
    private String photo;
    private String description;

    public HotelDetails(Long id, String name, String address, String city, double rating, BigDecimal price, String photo, String description) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.rating = rating;
        this.price = price;
        this.photo = photo;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", rating=" + rating +
                ", price=" + price +
                ", photo='" + photo + '\'' +
                '}';
    }
}
