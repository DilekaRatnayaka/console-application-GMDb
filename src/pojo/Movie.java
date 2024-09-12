package pojo;

public class Movie  implements java.io.Serializable {


     private Integer movieId;
     private String movieName;
     private String director;
     private String actors;
     private Integer runtime;

    public Movie() {
    }

	
    public Movie(String movieName) {
        this.movieName = movieName;
    }
    public Movie(String movieName, String director, String actors, Integer runtime) {
       this.movieName = movieName;
       this.director = director;
       this.actors = actors;
       this.runtime = runtime;
    }
   
    public Integer getMovieId() {
        return this.movieId;
    }
    
    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
    public String getMovieName() {
        return this.movieName;
    }
    
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getDirector() {
        return this.director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    public String getActors() {
        return this.actors;
    }
    
    public void setActors(String actors) {
        this.actors = actors;
    }
    public Integer getRuntime() {
        return this.runtime;
    }
    
    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

}

