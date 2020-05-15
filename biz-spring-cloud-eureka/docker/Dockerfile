FROM java:alpine
VOLUME /tmp
ADD biz-spring-cloud-eureka-1.0.2019070901-RELEASE.jar app.jar
ENV JAVA_OPTS="\
-server -Xms256m -Xmx256m -Xmn100m -Xss256k"
ENTRYPOINT ["java","${JAVA_OPTS}","-jar","/app.jar"]
EXPOSE 8080