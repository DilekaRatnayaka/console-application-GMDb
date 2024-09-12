package pojo;

import java.util.Date;

public class Purchase  implements java.io.Serializable {


     private Integer purchaseId;
     private Integer userId;
     private Integer movieId;
     private Date purchaseDate;

    public Purchase() {
    }

    public Purchase(Integer userId, Integer movieId, Date purchaseDate) {
       this.userId = userId;
       this.movieId = movieId;
       this.purchaseDate = purchaseDate;
    }
   
    public Integer getPurchaseId() {
        return this.purchaseId;
    }
    
    public void setPurchaseId(Integer purchaseId) {
        this.purchaseId = purchaseId;
    }
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getMovieId() {
        return this.movieId;
    }
    
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public Date getPurchaseDate() {
        return this.purchaseDate;
    }
    
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}


