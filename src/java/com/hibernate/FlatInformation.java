package com.hibernate;
// Generated Oct 3, 2016 1:10:48 PM by Hibernate Tools 4.3.1



/**
 * FlatInformation generated by hbm2java
 */
public class FlatInformation  implements java.io.Serializable {


     private Integer flatId;
     private FlatDescription flatDescription;
     private FlatAddress flatAddress;
     private Double pricePerSqure;

    public FlatInformation() {
    }

    public FlatInformation(FlatDescription flatDescription, FlatAddress flatAddress, Double pricePerSqure) {
       this.flatDescription = flatDescription;
       this.flatAddress = flatAddress;
       this.pricePerSqure = pricePerSqure;
    }
   
    public Integer getFlatId() {
        return this.flatId;
    }
    
    public void setFlatId(Integer flatId) {
        this.flatId = flatId;
    }
    public FlatDescription getFlatDescription() {
        return this.flatDescription;
    }
    
    public void setFlatDescription(FlatDescription flatDescription) {
        this.flatDescription = flatDescription;
    }
    public FlatAddress getFlatAddress() {
        return this.flatAddress;
    }
    
    public void setFlatAddress(FlatAddress flatAddress) {
        this.flatAddress = flatAddress;
    }
    public Double getPricePerSqure() {
        return this.pricePerSqure;
    }
    
    public void setPricePerSqure(Double pricePerSqure) {
        this.pricePerSqure = pricePerSqure;
    }




}


