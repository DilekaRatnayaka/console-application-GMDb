package pojo;

public class MovieGenre  implements java.io.Serializable {


     private MovieGenreId id;

    public MovieGenre() {
    }

    public MovieGenre(MovieGenreId id) {
       this.id = id;
    }
   
    public MovieGenreId getId() {
        return this.id;
    }
    
    public void setId(MovieGenreId id) {
        this.id = id;
    }




}


