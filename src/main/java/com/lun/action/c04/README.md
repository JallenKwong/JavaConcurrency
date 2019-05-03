# 锁的优化及注意事项 #

## 有助于提高“锁”性能的几点建议 ##

### 减少锁持有时间 ###

	public synchronized void syncMethod(){
		othercode1();
		mutexMethod();
		othercode2();
	}

转变成

	public void syncMethod(){
		othercode1();
		synchronized(this){
			mutexMethod();
		}
		othercode2();
	}

### 减少锁粒度 ###

这种技术典型的使用场景就是ConcurrentHashMap类的实现。

对于HashMap来说，最重要的两方法get()、put()。一种最自然的想法就是对整个HashMap加锁，必然可以得到一个线程安全的对象。但是，这样做的话被认为加大锁粒度。

对于ConcurrentHashMap，它内部进一步细分了若干个小的HashMap，称之为段Segment。

如果需要在ConcurrentHashMap中增加一个新的表项，并不是件整个HashMap加锁，而是首先根据hashcode得到该表项应该被存放到哪个段中，然后对该段加锁，并完成put()操作。在多线程环境下，若多个线程同时进行put()操作，只要被加入的表项不存放在同一段中，则线程间便可以做到真正的并行。

### 读写分离锁来替换独占锁 ###

ReadWriteLock可以提高系统的性能。使用读写分离锁来替代独占锁是减少锁粒度的一种特殊情况。

### 锁分离 ###

一个典型的案例就是java.util.concurrent.LinkedBlockingQueue的实现。

它定义了takeLock和putLock，它们分别take()和put()操作中使用。因此，take()和put()就吃相互独立，它们之间不存在锁竞争关系，只需要在take()和take()、put()和put()间分别对takeLock和putLock进行竞争。从而，削弱锁竞争的可能性。

### 锁粗化 ###

VM在遇到一连串连续地对同一锁不断进行请求和释放的操作是，便会把所有的锁操作整合成对锁的一次请求，从而减少对锁的请求同步次数，这个操作叫做锁的粗化。

	public void demoMethod(){
	
		synchronized(lock){
			//do sth
		}
	
		//做其它不需要的同步工作，但能很快执行完毕
	
		synchronized(lock){
			//do sth
		}
	
	}

会被整合成

	public void demoMethod(){
	
		synchronized(lock){

			//do sth

			//做其它不需要的同步工作，但能很快执行完毕

			//do sth
		}
	
	}

---

	for(int i = 0; i < circle; i++){
		synchronized(lock){
	
		}
	}

更合理做法是在外层只请求一次锁：

	synchronized(lock){
		for(int i = 0; i < circle; i++){

		}
	}

性能优化就是根据运行时的真实情况对各种资源点进行权衡折中的过程。锁粗化的思想和减少锁持有时间是相反的，但在不同的场合，它们的效果并不相同。所以需根据实际情况，进行权衡。

## JVM对锁优化所做的努力 ##

### 锁偏向 ###



### 轻量级锁 ###



### 自旋锁 ###



### 锁消除 ###







