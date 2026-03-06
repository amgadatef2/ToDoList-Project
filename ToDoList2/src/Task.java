import java.time.LocalDateTime;
public class Task {
    private int id;
    private String title;
    private String description;
    private LocalDateTime date ;
    private String priority;
    private String status;
    public Task (int id , String title,String description,LocalDateTime date,String priority,String status){
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public LocalDateTime getDate(){
        return date;
    }
    public String getPriority(){
        return priority;
    }
    public String getStatus(){
        return status;
    }

    void setId(int id){
        this.id=id;
    }
    void setTitle(String title){
        this.title=title;
    }
    void setDate(LocalDateTime date){
        this.date=date;
    }
    void setDescription(String description){
        this.description=description;
    }
    void setPriority(String priority){
        this.priority=priority;
    }
    void setStatus(String status){
        this.status=status;
    }
}
