import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Restaurant {
    final List<Waiter> waiters = new ArrayList<>();
    final Cook cook = new Cook("Повар");;

    int cookingTime = 5000;
    int eatingTime = 5000;

    public Restaurant() {
        waiters.add(new Waiter("Официант1"));
        waiters.add(new Waiter("Официант2"));
        waiters.add(new Waiter("Официант3"));
    }

    public void makeOrder() {
        int i;
        for (i = 0; i < waiters.size(); i++) {

            if (waiters.get(i).lock.tryLock()) {
                try {
                    callWaiter(waiters.get(i), cook);
                    break;
                } finally {
                    waiters.get(i).lock.unlock();
                    eating();
                    break;
                }
            }
            i = 0;
        }
    }

    public void callWaiter(Waiter waiter, Cook cook) {
        System.out.println(Thread.currentThread().getName() + " пришел в ресторан");
        System.out.println(waiter.getName() + " взял заказ");
        while(true){
            if (cook.lock.tryLock()) {
                try {
                    cook.cookDish();
                } finally {
                    cook.lock.unlock();
                    break;
                }
            }
        }
        System.out.println(waiter.getName() + " несет заказ");
    }

    public void eating() {
        try {
            Thread.currentThread().sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " уходит");
    }
}
