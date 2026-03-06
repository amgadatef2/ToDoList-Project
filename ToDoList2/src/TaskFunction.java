import java.time.LocalDateTime;

public class TaskFunction {
    private Task[] tasks;
    private int taskCount;
    private int nextId;
    private final int MAX=10;
    public TaskFunction (){
        tasks = new Task[MAX];
        taskCount =0;
        nextId=1;
    }
    public void addTask(String title, String desc, LocalDateTime dueDate, String priority, String status){
        if (taskCount < MAX){
            tasks[taskCount] = new Task(nextId,title,desc,dueDate,priority,status);
            nextId++;
            taskCount++;
        }
    }
    public void updateTask(int id ,String title, String desc, LocalDateTime data, String priority, String status){
        for (int i = 0 ; i < taskCount ; i++){
            if (tasks[i].getId()==id){
                tasks[i].setTitle(title);
                tasks[i].setTitle(title);
                tasks[i].setDescription(desc);
                tasks[i].setDate(data);
                tasks[i].setPriority(priority);
                tasks[i].setStatus(status);
            }
        }
    }
    public void deleteTask(int id){
        for (int i=0 ;i < taskCount ; i++){
            if (tasks[i].getId() ==id){
                for (int j = i ; j < taskCount-1;j++){
                    tasks[j] =tasks[j+1];
                }
                tasks[taskCount - 1] = null;
                taskCount--;
                break;
            }
        }

    }
    public boolean checkAndMarkOverdue() {
        boolean changed = false;
        LocalDateTime now = LocalDateTime.now();
        for (int i = 0; i < taskCount; i++) {

            if (!tasks[i].getStatus().equals("Done") && !tasks[i].getStatus().equals("OVERDUE")) {
                if (now.isAfter(tasks[i].getDate())) {
                    tasks[i].setStatus("OVERDUE"); // استخدام Setter
                    changed = true;
                }
            }
        }
        return changed;
    }
    public Task[] getTasks() {
        return tasks;
    }
    public int getTaskCount() {
        return taskCount;
    }

}
