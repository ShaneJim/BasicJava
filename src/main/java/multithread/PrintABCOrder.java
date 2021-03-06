package multithread;

public class PrintABCOrder {
    public static Boolean isThreadA = true;
    public static Boolean isThreadB = true;
    public static Boolean isThreadC = true;

    public static void main(String[] args) {
        final PrintABCOrder abc = new PrintABCOrder();
        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (abc) {
                        while (!isThreadA) {
                            try {
                                abc.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("A");
                        isThreadA = false;
                        isThreadB = true;
                        isThreadC = false;
                        abc.notifyAll();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (abc) {
                        while (!isThreadB) {
                            try {
                                abc.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("B");
                        isThreadA = false;
                        isThreadB = false;
                        isThreadC = true;
                        abc.notifyAll();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    synchronized (abc) {
                        while (!isThreadC) {
                            try {
                                abc.wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        System.out.print("C");
                        isThreadA = true;
                        isThreadB = false;
                        isThreadC = false;
                        abc.notifyAll();
                    }
                }
            }
        }).start();
    }
}
