

public class Restaurant {
    final Waiter waiter1 = new Waiter("Официант1");
    final Waiter waiter2 = new Waiter("Официант2");
    final Waiter waiter3 = new Waiter("Официант3");
    final Cook cook = new Cook("Повар");

   
    int eatingTime = 5000;

    public void makeOrder() {
        while (true) {
            if (waiter1.lock.tryLock()) {
                try {
                    callWaiter(waiter1);
                } finally {
                    waiter1.lock.unlock();
                    break;
                }

            } else if (waiter2.lock.tryLock()) {
                try {
                    callWaiter(waiter2);
                } finally {
                    waiter2.lock.unlock();
                    break;
                }
            } else if (waiter3.lock.tryLock()) {
                try {
                    callWaiter(waiter3);
                } finally {
                    waiter3.lock.unlock();
                    break;
                }
            }
        }

    }

    public void callWaiter(Waiter waiter) {
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
        try {
            Thread.currentThread().sleep(eatingTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " уходит");
    }
}
