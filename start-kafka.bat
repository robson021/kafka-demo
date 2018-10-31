cd D:\kafka
start cmd /c bin\windows\zookeeper-server-start.bat config\zookeeper.properties
timeout 8
start cmd /c bin\windows\kafka-server-start.bat config\server.properties
timeout 13
call bin\windows\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
echo "TOPIC LIST:"
call bin\windows\kafka-topics.bat --list --zookeeper localhost:2181
