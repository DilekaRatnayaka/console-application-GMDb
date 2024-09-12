package pojo;

public class MovieGenreId  implements java.io.Serializable {


     private int movieId;
     private int genreId;

    public MovieGenreId() {
    }

    public MovieGenreId(int movieId, int genreId) {
       this.movieId = movieId;
       this.genreId = genreId;
    }
   
    public int getMovieId() {
        return this.movieId;
    }
    
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    public int getGenreId() {
        return this.genreId;
    }
    
    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof MovieGenreId) ) return false;
		 MovieGenreId castOther = ( MovieGenreId ) other; 
         
		 return (this.getMovieId()==castOther.getMovieId())
 && (this.getGenreId()==castOther.getGenreId());
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getMovieId();
         result = 37 * result + this.getGenreId();
         return result;
   }   


}


