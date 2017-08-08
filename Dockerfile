FROM maven:alpine
WORKDIR /code
COPY . /code
RUN ["mvn","clean","package"]
EXPOSE 8080
CMD  ["java","-jar", "/code/target/loan-1.jar"]