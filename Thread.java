
 //线程启动的三种方式
 
//第一种 继承Threads
public class Threadd extends Thread {
    public static void main(String args[]) {
        (new Threadd()).start();
        System.out.println("main thread run ");
    }
    public synchronized  void run() {
        System.out.println("sub thread run ");
    }

}
//实现Runnable接口
public class Threadd implements Runnable{
    public static void main(String args[]) {
        (new Thread(new Threadd())).start();
        System.out.println("main thread run ");
    }
    public void run() {
        System.out.println("sub thread run ");
    }

}
//直接在函数体使用
void java_thread()  {  
  
     Thread t = new Thread(new Runnable(){  
            public void run(){  
            mSoundPoolMap.put(index, mSoundPool.load(filePath, index));  
            getThis().LoadMediaComplete();  
            }});  
        t.start();  
}  
//实现Callable接口
public class Threadd implements Callable<Integer> {
    int i=0;
    public Integer call() throws Exception {
        for (; i < 20; i++) {
            System.out.println(Thread.currentThread().getName()+""+i);
        }
        return i;
    }

}
    我们一般使用thread都是new一个线程，然后调用start方法启动，使用start方法才真正实现了多线程运行，因为这个时候不用等待我们的run方法执行完成就可以
继续执行下面的代码，这才叫多线程嘛！因为thread线程有5种状态，创建-就绪-运行-阻塞-死亡这五种，那么我们的start方法呢就是就绪这一步，因为这个时候我们的
线程并没有立即的执行，而是得等待，等到我们的cpu有空闲的时候，才会执行线程里面的run方法，等run方法执行完了，线程就结束了。
