package pojo;

public class User  implements java.io.Serializable {


     private Integer userId;
     private String username;
     private String email;
     private String password;

    public User() {
    }

    public User(String username, String email, String password) {
       this.username = username;
       this.email = email;
       this.password = password;
    }
   
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getUsername() {
        return this.username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }




}


