rm ./bin/*.jar
./mvnw -Pprod clean verify
cp ./target/*.jar ./bin/