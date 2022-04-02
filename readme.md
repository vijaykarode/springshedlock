#To create the runnable JAR

mvn clean install

#In 1st terminal run 

java -jar -DINSTANCE_NAME=INSTANCE_1 target/springshedlock-0.0.1-SNAPSHOT.jar

#In 2nd terminal run 

java -jar -DINSTANCE_NAME=INSTANCE_2 target/springshedlock-0.0.1-SNAPSHOT.jar

