package pojo;

import java.util.Date;

public class Review  implements java.io.Serializable {


     private Integer reviewId;
     private Integer userId;
     private Integer movieId;
     private Integer rating;
     private String reviewDescription;
     private Date reviewDate;

    public Review() {
    }

    public Review(Integer userId, Integer movieId, Integer rating, String reviewDescription, Date reviewDate) {
       this.userId = userId;
       this.movieId = movieId;
       this.rating = rating;
       this.reviewDescription = reviewDescription;
       this.reviewDate = reviewDate;
    }
   
    public Integer getReviewId() {
        return this.reviewId;
    }
    
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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
    public Integer getRating() {
        return this.rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getReviewDescription() {
        return this.reviewDescription;
    }
    
    public void setReviewDescription(String reviewDescription) {
        this.reviewDescription = reviewDescription;
    }
    public Date getReviewDate() {
        return this.reviewDate;
    }
    
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

}


