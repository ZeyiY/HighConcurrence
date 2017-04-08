package com.zeyi.highconcurrence.thread;

/**
 * Created by yangsen on 2017/4/8.
 *
 */
public class Puzzle {
    static boolean answerReader = false;
    static int answer = 0;
    static Thread threadOne = new Thread(){
        @Override
        public void run() {
            answer = 43;
            answerReader = true;
        }
    };

    static Thread threadTwo = new Thread(){
        @Override
        public void run() {
            if (answerReader){
                System.out.println("The answer value is "+answer);
            }else{
                System.out.println("I do`nt know answer");
            }
        }
    };

    public static void main(String args[]) throws InterruptedException {
        threadOne.start();
        threadTwo.start();
        threadOne.join();
        threadTwo.join();
    }
}
