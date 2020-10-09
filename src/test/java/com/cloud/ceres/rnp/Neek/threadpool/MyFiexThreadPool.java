package com.cloud.ceres.rnp.Neek.threadpool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author heian
 * @create 2020-02-03-2:20 下午
 * @description 自定义 有限线程数
 */
public class MyFiexThreadPool {

    //任务仓库
    private LinkedBlockingQueue<Runnable> runnables;

    //线程队列
    private List<Thread> threads;

    private volatile boolean isRunning = true;

    //执行任务的打工仔  通过调用线程的start方法去处理runnable
    class Worker extends Thread{

        //打工仔跑任务必须是从同一个的线程池中取任务
        private MyFiexThreadPool pool;

        public Worker(MyFiexThreadPool pool){
            this.pool = pool;
        }

        @Override
        public void run() {
            Runnable runnable = null;
            while (this.pool.isRunning || this.pool.runnables.size()>0){
                try {
                    //执行了线程关闭，则不需要阻塞
                    if (this.pool.isRunning){
                        runnable = pool.runnables.take();
                    }else {
                        runnable = pool.runnables.poll();
                    }
                } catch (InterruptedException e) {
                    System.out.println("该线程被中断" + Thread.currentThread().getName());
                }
                if (runnable != null)
                    runnable.run();
                    System.out.println("当前线程：" + Thread.currentThread().getName() + "执行完毕");
            }
        }
    }

    /**
     * TODO 这里阻塞的是main线程不是我们线程池中的线程
     * this.runnables.put(runnable); //如果你想任务阻塞,不想失去任务则使用阻塞方法
     */
    public boolean submit(Runnable runnable) throws InterruptedException {
        boolean bool = this.runnables.offer(runnable);// 如果超出队列限制你想丢弃就用这个
        if (!bool)
            System.out.println(Thread.currentThread().getName() + "已经达到任务队列上限："+this.runnables.size()+ "，任务被丢弃" );
        return false;
    }

    public MyFiexThreadPool(int initThreadSize,int queueSize){
        if (initThreadSize <=0 || queueSize<=0)
            throw new IllegalArgumentException("非法参数");
        //初始化两个容器
        this.threads = Collections.synchronizedList(new ArrayList<>());
        this.runnables = new LinkedBlockingQueue<>(queueSize);
        for (int i=0;i<initThreadSize;i++) {
            Worker worker = new Worker(this);
            this.threads.add(worker);
            worker.start();
        }
    }


    /**
     * 关闭：1、停止线程必须停止向阻塞队列放置任务
     *      2、队列中还存在任务则要求，先把任务执行完才关闭
     *      3、假设在关闭之前已经存在线程阻塞，则需要打断该线程
     */
    public void shutdown(){
        isRunning = false;
        this.threads.forEach(thread -> {
            if (thread.getState().equals(Thread.State.BLOCKED)){
                thread.interrupt();
            }
        });
    }

    public static void main(String[] args) throws InterruptedException {
        MyFiexThreadPool pool = new MyFiexThreadPool(2,5);
        for (int i=0;i<10;i++){
            pool.submit(() -> {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        pool.shutdown();


    }


}
