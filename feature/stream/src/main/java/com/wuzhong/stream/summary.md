1. 惰性求值
    a. 中间操作有状态／无状态
    b. 终止操作短路 
    c. parallel sequential 不创建流 head 
2. 收集器一分组 
    a. Collectors.summarizingInt
    b. Collectors.partitioningBy
    c. Collectors.groupingBy
    d. Collectors.counting()
3. 运行机制
    a. 链式， Head 一＞ nextstage
    b. 并行 fork / join 阻塞