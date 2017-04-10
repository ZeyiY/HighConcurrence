package reentrantlock;

/**
 * Created by yangsen1 on 2017/4/10.
 * 死锁程序
 */
public class Uninterruptible {
    public static void main(String args[]) throws InterruptedException {
        final Object objectOne          = new Object();
        final Object objectTwo          = new Object();
              Thread threadOne          = new Thread(){
                  @Override
                  public void run() {
                      try{
                          synchronized (objectOne){
                              Thread.sleep(1000);
                              synchronized (objectTwo){}
                          }
                      }catch (InterruptedException e){
                          System.out.println("threadOne interrupt");
                      }
                  }
              };
              Thread threadTwo          = new Thread(){
                  @Override
                  public void run() {
                     try{
                         synchronized (objectTwo){
                             Thread.sleep(1000);
                             synchronized (objectOne){
                             }
                         }
                     }catch (InterruptedException e){
                         System.out.println("threadTwo interrupt");
                     }
                  }
              };
        threadOne.start();
        threadTwo.start();
        Thread.sleep(2000);
        threadOne.interrupt();
        threadTwo.interrupt();
        threadOne.join();
        threadTwo.join();
    }
}
