1.  默认当前线程执行：
    >By default, Reactor is mostly single-threaded. The publisher and subscriber do not create additional threads for their execution. All life cycle hooks, and most operators, perform single-threaded execution.

2. delayElement 之后的执行线程在 parallel
    >The rest of the chain now executes in the thread pool, rather than the main thread.
    
3. immediate
    >The Schedulers.immediate scheduler executes work on the currently executing thread. All tasks are executed on the caller thread, and no task is performed in parallel. This is the default execution model for most Reactor tasks.

4. single , 不允许block
    >single scheduler is meant to execute non-blocking, computation-intensive operations. This can be treated as an event loop, executing non-blocking tasks in its queue. If we invoke any reactive blocking APIs, the scheduler throws back the following error
    
5. parallel , 基于cpu的内核数
    >Schedulers.parallel scheduler executes work on a multiple-worker thread pool. It creates workers based on the number of available processors. This is the default scheduler used in various Reactor operators.
    System.setProperty("reactor.schedulers.defaultPoolSize","100");
    
6. publishOn, 影响下面的流
     >The publishOn operator intercepts events from a publisher at a configured point in the execution chain, and sends them to a different scheduler for the rest of the chain. As a result, the operator changes the threading context of the downstream reactive chain. It is important to note that the operator only influences the downstream event chain. It does not alter the upstream chain, and leaves the upstream execution to the default execution model.

7. subscribeOn
    >The subscribeOn operator intercepts events from a publisher in the execution chain and sends them to a different scheduler for the complete chain. It is important to note that the operator changes the execution context for the complete chain, unlike the publishOn operator, which only alters the execution of a downstream chain
    
## 总结

1. subscribeOn 影响全部的流， 但是会被 publishOn覆盖， publishOn 影响之后的stream流

## q&a
1. What are the different types of schedulers that are available in Reactor?
    1. single,parallel,immediate,io,elastic
2. What scheduler should be used for blocking operations?
    1. elastic
3. What scheduler should be used for computation-intensive operations?
    1. single
4. How are PublishOn and SubscriberOn different from each other?
    1. publishOn alter the below, subscriberOn alter the others
5. What is the limitation of ParallelFlux?
    1. finally
6. Which operators are available for generating a ConnectedFlux?
    1. publish() replay()