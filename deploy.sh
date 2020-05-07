mvn -f ~/Devel/workspace/com.project.contract/ clean install

mvn clean install package -DskipTests -Pprod

scp target/data-alfa.jar devops@94.237.94.131:/var/www/jee_projects/datapool