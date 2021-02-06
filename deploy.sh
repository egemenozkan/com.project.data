mvn -f $HOME/devel/workspace/projects/com.project.contract/ clean install

mvn clean install package -DskipTests

scp target/data-alfa.jar webuser@192.168.0.20:/var/www/vantalii/datapool