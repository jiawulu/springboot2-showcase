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
    
6. publishOn

7. 