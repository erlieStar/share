call mvn clean package -DskipTests=true
cd ./target
scp new-blog-1.0.jar root@myhost:/root
cd ../
@pause

