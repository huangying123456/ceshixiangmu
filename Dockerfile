FROM java:8-jre
MAINTAINER Youhujia, Inc. Backend Awesome Team
ADD build/libs/solar-1.0.0.jar /
ADD entrypoint.sh /
VOLUME ["/tmp"]
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone
RUN mkdir /logs
RUN mkdir /logs/applogs
RUN mkdir /logs/accesslogs
ENTRYPOINT ["/entrypoint.sh"]
EXPOSE 6011
EXPOSE 6012
