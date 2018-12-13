#测试环境启动
nohup java -jar -Xms128m -Xmx128m /usr/local/consumer/runjar/consumer.mq.asset.allocation.jar > /usr/local/tomcat/consumer/runjar/consumer.mq.asset.allocation.log 2>&1 &


#生产环境启动
nohup java -jar -Xms1024m -Xmx1024m /usr/local/consumer/runjar/consumer.mq.asset.allocation.jar > /usr/local/tomcat/consumer/runjar/consumer.mq.asset.allocation.log 2>&1 &


#杀死进程
ps -ef|grep runjar|grep -v grep|cut -c 9-15|xargs kill -9