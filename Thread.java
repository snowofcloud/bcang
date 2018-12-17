
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

