package DSA;

public class NumberPrinter {
    public void printZero() {
        System.out.print("0");
    }

    public void printEven(int num) {
        System.out.print(num);
    }

    public void printOdd(int num) {
        System.out.print(num);
    }
}


class ThreadController {
    private final NumberPrinter printer = new NumberPrinter(); 
    private int n; 
    private int current = 1; 
    private int turn = 0; 

    public ThreadController(int n) {
        this.n = n;
    }

    public void zeroThread() throws InterruptedException {
        synchronized (this) {
            while (current <= n) {
                
                while (turn != 0) {
                    wait();
                }
               
                if (current <= n) {
                    printer.printZero();
                }
                
                turn = (current % 2 == 0) ? 2 : 1;
                notifyAll(); 
            }
        }
    }

    
    public void evenThread() throws InterruptedException {
        synchronized (this) {
            while (current <= n) {
                
                while (turn != 2) {
                    wait();
                }
               
                if (current <= n) {
                    printer.printEven(current);
                    current++; 
                }
                turn = 0; 
                notifyAll(); 
            }
        }
    }

   
    public void oddThread() throws InterruptedException {
        synchronized (this) {
            while (current <= n) {
                
                while (turn != 1) {
                    wait();
                }
                
                if (current <= n) {
                    printer.printOdd(current);
                    current++; 
                }
                turn = 0; 
                notifyAll(); 
            }
        }
    }
}


class Main {
    public static void main(String[] args) {
        int n = 5; 
        ThreadController controller = new ThreadController(n);

        
        Thread zeroThread = new Thread(() -> {
            try {
                controller.zeroThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

   
        Thread oddThread = new Thread(() -> {
            try {
                controller.oddThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        
        Thread evenThread = new Thread(() -> {
            try {
                controller.evenThread();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

       
        zeroThread.start();
        oddThread.start();
        evenThread.start();

        
        try {
            zeroThread.join();
            oddThread.join();
            evenThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
