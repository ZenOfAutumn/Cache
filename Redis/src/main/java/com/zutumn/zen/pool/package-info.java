package com.zutumn.zen.pool;


/**
 * GenericObejctPoolConfig 参数
 *
 * lifo：对象池存储空闲对象是使用的LinkedBlockingDeque，它本质上是一个支持FIFO和FILO的双向的队列，common-pool2中的LinkedBlockingDeque不是Java原生的队列，而有common-pool2重新写的一个双向队列。如果为true，表示使用FIFO获取对象。默认值是true.建议使用默认值。
 * fairness：common-pool2实现的LinkedBlockingDeque双向阻塞队列使用的是Lock锁。这个参数就是表示在实例化一个LinkedBlockingDeque时，是否使用lock的公平锁。默认值是false，建议使用默认值。
 * maxActive: 链接池中最大连接数,默认为8。
 * maxIdle: 链接池中最大空闲的连接数,默认为8。
 * minIdle: 连接池中最少空闲的连接数,默认为0。
 * maxWaitMillis: 当连接池资源耗尽时，调用者最大阻塞的时间，超时将抛出异常。单位，毫秒数；默认为-1，表示永不超时。
 * minEvictableIdleTimeMillis: 连接空闲的最小时间，达到此值后空闲连接将可能会被移除。默认： 1000L * 60L * 30L；负值(-1)表示不移除。
 * softMinEvictableIdleTimeMillis: 连接空闲的最小时间，达到此值后空闲链接将会被移除，且保留“minIdle”个空闲连接数。默认： 1000L * 60L * 30L。
 * numTestsPerEvictionRun: 对于“空闲链接”测“链检测线程而言，每次检测的链接资源的个数。默认为3。
 * testOnBorrow: 向调用者输出“链接”资源时，是否检测是有有效，如果无效则从连接池中移除，并尝试获取继续获取。默认为false。建议保持默认值。
 * testOnReturn:  向连接池“归还”链接时，是否检接”对象的有效性。默认为false。建议保持默认值。
 * testWhileIdle:  向调用者输出“链接”对象时，是否检测它的空闲超时；默认为false。如果“链接”空闲超时，将会被移除。建议保持默认值。
 * timeBetweenEvictionRunsMillis:  “空闲链接”检测线程，检测的周期，毫秒数。如果为负值，表示不运行“检测线程”。默认为-1。
 * blockWhenExhausted：当对象池没有空闲对象时，新的获取对象的请求是否阻塞。true阻塞。默认值是true。
 *
 */
