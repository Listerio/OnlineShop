package com.example.onlineshopdemo.entity;

import com.example.onlineshopdemo.enumerations.Category;
import com.example.onlineshopdemo.enumerations.Subcategory;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "orderedProduct",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

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
    @OneToMany(mappedBy = "product")
    private List<CartItems> cartItems;
    @ManyToOne
    @JoinColumn(name = "product_owner_id")
    private ProductOwner productOwner;

    public Product(){

    }

    public Product(String productName, double productPrice, Category productCategory,
                   Subcategory productSubcategory, String productDescription, String productImage1,
                   String productImage2,String productImage3, String productImage4)
    {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productCategory=productCategory;
        this.productSubcategory=productSubcategory;
        this.productDescription = productDescription;
        this.productImage1 = productImage1;

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

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product product)) return false;
        return Double.compare(product.getProductPrice(), getProductPrice()) == 0 && Objects.equals(getId(), product.getId()) && Objects.equals(getOrderItems(), product.getOrderItems()) && Objects.equals(getProductName(), product.getProductName()) && getProductCategory() == product.getProductCategory() && getProductSubcategory() == product.getProductSubcategory() && Objects.equals(getProductDescription(), product.getProductDescription()) && Objects.equals(getProductImage1(), product.getProductImage1()) && Objects.equals(getCartItems(), product.getCartItems()) && Objects.equals(getProductOwner(), product.getProductOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderItems(), getProductName(), getProductPrice(), getProductCategory(), getProductSubcategory(), getProductDescription(), getProductImage1(),getCartItems(), getProductOwner());
    }
}

