/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.entities;
/**
 *
 * @author RoKy-Dev
 */
public class Menu {
    private int id,price;
    private String name,category,description;

    public Menu() {
    }

    public Menu( int price, String name, String category, String description) {
        this.price = price;
        this.name = name;
        this.category = category;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Menu{" + "id=" + id + ", price=" + price + ", name=" + name + ", category=" + category + ", description=" + description + "\n";
    }
 public String idtoString ()
    {
        return String.valueOf(id);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
