package com.example.onlineshopdemo.entity;

import com.example.onlineshopdemo.enumerations.Category;
import com.example.onlineshopdemo.enumerations.Subcategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonProperty("name")
    @Column(nullable = false,name = "product_name")
    private String productName;
    @JsonProperty("price")
    @Column(nullable = false,name = "product_price")
    private double productPrice;
    @Enumerated(EnumType.STRING)
    @JsonProperty("cat")
    @Column(nullable = false, name = "product_category")
    private Category productCategory;
    @Enumerated(EnumType.STRING)
    @JsonProperty("subCat")
    @Column(nullable = false, name = "product_sub_category")
    private Subcategory productSubcategory;
    @Column(nullable = false, name = "product_description")
    @JsonProperty("desc")
    private String productDescription;
    @JsonProperty("img1")
    @Column(name = "product_image1")
    private String productImage1;
    @JsonProperty("img3")
    @Column(name = "product_image2")
    private String productImage2;
    @JsonProperty("img2")
    @Column(name = "product_image3")
    private String productImage3;
    @JsonProperty("img4")
    @Column(name = "product_image4")
    private String productImage4;
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;
    @ManyToOne
    @JoinColumn(name = "product_owner_id")
    private ProductOwner productOwner;

    private Product(){

    }

    public Product( String productName, double productPrice, Category productCategory,
                   Subcategory productSubcategory, String productDescription, String productImage1,
                   String productImage2,String productImage3, String productImage4)
    {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory=productCategory;
        this.productSubcategory=productSubcategory;
        this.productDescription = productDescription;
        this.productImage1 = productImage1;
        this.productImage2 = productImage2;
        this.productImage3 = productImage3;
        this.productImage4 = productImage4;
    }
    public Product(Long id, String productName, double productPrice, String productCategory,
                   String productSubcategory, String productDescription, String productImage1,
                   String productImage2, String productImage3, String productImage4) {

        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory = Category.valueOf(productCategory);
        this.productSubcategory = Subcategory.valueOf(productSubcategory);
        this.productDescription = productDescription;
        this.productImage1 = productImage1;
        this.productImage2 = productImage2;
        this.productImage3 = productImage3;
        this.productImage4 = productImage4;

    }




    public ProductOwner getProductOwner() {
        return productOwner;
    }
    public void setProductOwner(ProductOwner productOwner) {
        this.productOwner = productOwner;
    }

    public List<CartItems> getCartItems() {return cartItems;}
    public void setCartItems(List<CartItems> cartItems) {this.cartItems = cartItems;}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public Category getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(Category productCategory) {
        this.productCategory = productCategory;
    }

    public Subcategory getProductSubcategory() {
        return productSubcategory;
    }

    public void setProductSubcategory(Subcategory productSubcategory) {
        this.productSubcategory = productSubcategory;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage1() {
        return productImage1;
    }

    public void setProductImage1(String productImage1) {
        this.productImage1 = productImage1;
    }

    public String getProductImage2() {
        return productImage2;
    }

    public void setProductImage2(String productImage2) {
        this.productImage2 = productImage2;
    }

    public String getProductImage3() {
        return productImage3;
    }

    public void setProductImage3(String productImage3) {
        this.productImage3 = productImage3;
    }

    public String getProductImage4() {
        return productImage4;
    }

    public void setProductImage4(String productImage4) {
        this.productImage4 = productImage4;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productCategory=" + productCategory +
                ", productSubcategory=" + productSubcategory +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}

