FROM frolvlad/alpine-oraclejdk8:slim
# Install base packages
RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.ustc.edu.cn/g' /etc/apk/repositories

RUN apk update && apk add curl bash tree tzdata \
    && cp -r -f /usr/share/zoneinfo/Hongkong /etc/localtime \
    && echo -ne "Alpine Linux 3.4 image. (`uname -rsv`)\n" >> /root/.built

ADD *.jar app.jar

ENV JAVA_OPTS="-server -Xms256m -Xmx256m -XX:SurvivorRatio=2 -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:gc.log"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /app.jar " ]