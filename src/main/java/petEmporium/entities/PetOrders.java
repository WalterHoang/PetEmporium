package petEmporium.entities;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class PetOrders {
    @Id
    @GeneratedValue
    private Integer orderId;
    @ManyToOne
    @JoinColumn(name = "petUsersId")
    @ApiModelProperty(required = true, name = "User Id, assuming this is a customer", value = "Pet User id, its used to connect the user to the order")
    private PetUsers petUsers;
    @OneToMany
    @JoinTable(name = "order_cart",
            joinColumns = {@JoinColumn(name = "orderId", referencedColumnName = "orderId")},
            inverseJoinColumns = {@JoinColumn(name = "cartId", referencedColumnName = "cartId")})
    @ApiModelProperty(required = true, name = "SubOrder Items", value = "Pet User id, its used to connect the user to the order")
    Set<ShoppingCart> subOrders = new HashSet<>();

    @ApiModelProperty(value = "Auto-generated value for the total price of all suborders with a order")
    private Double totalPrice;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(updatable = false)
    private Date createdOnDate = new Date();

    @UpdateTimestamp
    @Temporal(TemporalType.DATE)
    @Column(insertable = false)
    private Date updatedOnDate = new Date();

    public PetOrders() {
    }//For Hibernate

    public PetOrders(PetUsers petUsers,
                     Set<ShoppingCart> subOrders,
                     Double totalPrice) {
        this.petUsers = petUsers;
        this.subOrders = subOrders;
        this.totalPrice = totalPrice;
    }

    public PetOrders(Integer orderId, PetUsers petUsers,
                     Set<ShoppingCart> subOrders,
                     Double totalPrice, Date createdOnDate, Date updatedOnDate) {
        this.orderId = orderId;
        this.petUsers = petUsers;
        this.subOrders = subOrders;
        this.totalPrice = totalPrice;
        this.createdOnDate = createdOnDate;
        this.updatedOnDate = updatedOnDate;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    //@JsonIgnore
    public PetUsers getPetUsers() {
        return petUsers;
    }

    //@JsonIgnore
    public void setPetUsers(PetUsers petUsers) {
        this.petUsers = petUsers;
    }

    public Set<ShoppingCart> getSubOrders() {
        return subOrders;
    }

    public void setSubOrders(Set<ShoppingCart> subOrders) {
        this.subOrders = subOrders;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getCreatedOnDate() {
        return createdOnDate;
    }

    public void setCreatedOnDate(Date createdOnDate) {
        this.createdOnDate = createdOnDate;
    }

    public Date getUpdatedOnDate() {
        return updatedOnDate;
    }

    public void setUpdatedOnDate(Date updatedOnDate) {
        this.updatedOnDate = updatedOnDate;
    }
}