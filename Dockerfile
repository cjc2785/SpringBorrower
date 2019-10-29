FROM fedora

RUN yum install java-1.8.0-openjdk -y
RUN yum install maven -y

WORKDIR /home/ec2-user/SpringBorrower


ENV LMS_DB_URL='jdbc:mysql://host.docker.internal:3306/library?serverTimezone=UTC'
ENV LMS_DB_USER='lmsapp'
ENV LMS_DB_PASSWORD='lmspassword'


COPY . .

EXPOSE 8083

RUN mvn clean package 

ENTRYPOINT java -jar /home/ec2-user/SpringBorrower/target/SpringLMS-0.0.1-SNAPSHOT.jar