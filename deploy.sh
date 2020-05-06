mvn -f ~/Devel/workspace/com.project.contract/ clean install
mvn -f ~/Devel/workspace/com.project.slugify/ clean install

mvn clean install package -DskipTests -Pprod

scp target/data-alfa.jar root@do.sevais.com:/opt/www/data