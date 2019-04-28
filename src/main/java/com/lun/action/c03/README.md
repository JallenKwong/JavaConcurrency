# JDK并发包 #



## 多线程团队协作：同步控制 ##

### synchronized的功能扩展：重入锁 ###

重入锁可完全替代synchronized关键字。在JDK5的早期版本中，重入锁的性能远远好于synchronized，但从JDK6开始，JDK在synchronized上做了大量的优化，使得两者的性能差距并不大。

[重入锁实现两线程对i进行累加](ReenterLock.java)

重入锁ReentrantLock的名字有重入的原因：**该锁可以反复进入**。

    lock.lock();
    lock.lock();
    try {
        i++;
    } finally {
        lock.unlock();
		lock.unlock();
    }

上述代码中，一个线程连续两次获得同一把锁。

若不允许这么操作，那么同一个线程在第2次获得锁时，将会和自己产生死锁。程序就会“卡死”在第2次申请锁的过程中。

但需要注意的是，**若同一个线程多次获得锁，那么在释放锁的时候，也必须释放相同次数**。若释放锁的次数多，那么会得到一个`java.lang.IllegalMonitorStateException`，反之，若释放锁的次数少了，那么相当于线程还持有这个锁，因此，其他线程也无法进入临界区。


重入锁另外的功能：

- 中断响应
- 锁申请等待限时
- 公平锁

#### 中断响应 ####

若一线程正等待锁，那么它依然可以收到一个通知，被告知无须再等待，可停止工作了。这种情况对于处理死锁是有一定帮助的。

[IntLock](IntLock.java)

#### 锁申请等待限时 ####

tryLock()

这是避免死锁的另一种方法

[带参tryLock()的例程](TimeLock.java)

[无参tryLock()的例程](TryLock.java)

#### 公平锁 ####

	//默认不公平锁
	public ReentrantLock()
	
	//fair = true时，表示锁公平。否则，不公平。
	public ReentrantLock(boolean fair)

[FairLock](FairLock.java)

#### 重入锁小结 ####

ReentrantLock重要方法：

- lock() 获得锁，若锁已经被占用，则等待
- lockInterruptibly() 获得锁，但优先响应中断
- tryLock() 尝试获得锁，若成功，返回true，失败返回false。该方法不等待，立即返回
- tryLock(long time, TimeUnit unit) 给定时间内尝试获得锁
- unlock() 释放锁

---

重入锁的实现来看，它主要集中在Java层面，它主要包含三个要素

1. 原子状态 原子状态使用CAS操作来存储当前锁的状态，判断锁是否已经被别的线程持有。
2. 等待队列 所有没有请求到锁的线程，会进入等待队列进行等待。带有线程释放锁后，系统就能从等待队列中唤醒一个线程，继续工作。
3. 阻塞原语park()和unpark()，用来挂起和恢复线程。

### 重入锁的好搭档：Condition条件 ###

Condition与wait()和notify()方法的作用是大致相同。

但是wait()和notify()方法是和synchronized关键字合作使用的，而Condition是与重入锁相关联的。

	public interface Condition {
		void await() throws InterruptedException;
		void awaitUninterruptibly();
		long awaitNanos(long nanosTimeout) throws InterruptedException;
		boolean await(long time, TimeUnit unit) throws InterruptedException;
		boolean awaitUntil(Date deadline) throws InterruptedException;
		void signal();
		void signalAll();
	}


- await()会**使当前线程等待**，同时释放当前锁，当其他线程中使用signal()或者signalAll()方法时，线程会重新获得锁并继续执行。或者当线程被中断时，也能跳出等待。这和Object.wait()相似
- awaitUninterruptibly() 与await()方法基本相同，但是它并**不会**在等待过程中响应中断
- signal() 用于**唤醒一个在等待中的线程**。相应的signalAll()方法会唤醒所有在等待中的线程。这与Object.notify()方法很类似。

[ReenterLockCondition](ReenterLockCondition.java)

---

在JDK内部，重入锁和Condition对象被广泛地使用，以ArrayBlockingQueue为例

    /** Main lock guarding all access */
    final ReentrantLock lock;

    /** Condition for waiting takes */
    private final Condition notEmpty;

    /** Condition for waiting puts */
    private final Condition notFull;

	//...

    public void put(E e) throws InterruptedException {
        checkNotNull(e);
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == items.length)
                notFull.await();//<-----------------------
            enqueue(e);
        } finally {
            lock.unlock();
        }
    }

    private void enqueue(E x) {
        // assert lock.getHoldCount() == 1;
        // assert items[putIndex] == null;
        final Object[] items = this.items;
        items[putIndex] = x;
        if (++putIndex == items.length)
            putIndex = 0;
        count++;
        notEmpty.signal();//<-----------------------
    }


相对应的take()方法如下：

    public E take() throws InterruptedException {
        final ReentrantLock lock = this.lock;
        lock.lockInterruptibly();
        try {
            while (count == 0)
                notEmpty.await();//<-----------------------
            return dequeue();
        } finally {
            lock.unlock();
        }
    }

    private E dequeue() {
        // assert lock.getHoldCount() == 1;
        // assert items[takeIndex] != null;
        final Object[] items = this.items;
        @SuppressWarnings("unchecked")
        E x = (E) items[takeIndex];
        items[takeIndex] = null;
        if (++takeIndex == items.length)
            takeIndex = 0;
        count--;
        if (itrs != null)
            itrs.elementDequeued();
        notFull.signal();//<-----------------------
        return x;
    }

上述源码来自JDK8

### 允许多个线程同时访问：信号量（Semaphore） ###

>semaphore/ˈseməfɔːr/ || /send 么 for/ n.信号标;旗语 v.打旗语;(用其他类似的信号系统)发信号