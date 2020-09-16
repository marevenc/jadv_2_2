import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Cook {
    String name;
    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    int cookingTime = 5000;

    public String getName() {
        return name;
    }

    public Cook(String name){
        this.name = name;
        System.out.println(this.getName() + " на работе");
    }

    public void cookDish(){
        try {
            Thread.sleep(cookingTime);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
