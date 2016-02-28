#!/bin/sh

echo '>>> Get old container id'

CID=$(docker ps | grep "fly" | awk '{print $1}')

echo $CID

cp AutoDeploy/AutoDeploy.war /home/liulinyu/docker/tomcat7/tomcat/webapps

docker build -t fly /home/liulinyu/docker/ | tee /var/lib/jenkins/jobs/AutoDeploy_Docker/workspace/Docker_build_result.log
RESULT=$(cat /var/lib/jenkins/jobs/AutoDeploy_Docker/workspace/Docker_build_result.log | tail -n 1)

#if [["$RESULT" != *Successfully*]];then
#  exit -1
#fi

echo '>>> Stopping old container'

if [ "$CID" != "" ];then
  docker stop $CID
fi
  
echo '>>> Starting new container'
docker run -d -p 58080:8081 fly

rm -rf /home/liulinyu/docker/tomcat7/tomcat/webapps/*.war