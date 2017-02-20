# HandlerThreadTest
HandlerThread的理解和基础使用


/**
 * 在Android中经常需要创建一个循环线程，有耗时操作时候，放到里面去操作，如果没有耗时操作，就让该线程处于等待，但是不要杀死它，
 * 最好不要一旦有耗时任务，就立刻创建一个新线程，因为会有性能问题,所以就出现了handlerThread 这种东西,handlerThread 也是一个线程
 * 只不过里面封装了looper,当然也存在messageQueue,多次传入任务时,会按队列顺序执行(可多次点击按钮查看log信息)
 * 使用步骤
 * 1.实例化handlerThread
 * 2.start 该线程
 * 3.创建Handler,传入handlerThread 的looper 对象,重新handlemessage 方法
 * 4.使用handler.sendMessage等方法发送消息
 * 5.不使用时,可以调用handlerThread.quit() 退出线程
 */
