## notebook-server
Java / Spring Boot Notebook Server can execute and include a code from differents language within there interpretors on java.

 ### 1- Installation
Clone project into a local folder.

$ git clone https://github.com/amineraf/notebook-server

#### Maven Installation

Maven
Install the last version of Maven.

Build and Run project

Once every thing is installed, run maven package in the project so the jar file can be generated. (You can skip Tests).

**$ mvn package -DskipTests**

Then You can run the project using the java -jar command.

**$ java -jar notebook-server-webapp/target/notebook-server-webapp-1.0-SNAPSHOT.jar**

The server of notebook-server is up and runing on the endpoint http://localhost:8080

_If the port 8080 is already used you can change the port under notebook-server-webapp>>application.properties server.port_


### 2- Usage

- You can use postman to test the app :
    - make the url and choose POST as method (Get is selected by default)
    - Use the Body and enter the payload (The json object)
    - Choose row and Json as language
    - Click on send

- The json object must have the following format `{"code": "string", "sessionId": "string"}`

- With code is mandatory and sessionid is optional (can be generated from the app)

- Code should respect the following format %(as key word)+_[supported language]_+_[script code]_

- example `{"code": "%python print 1+1 ", "sessionId": "96385214524588888"}`

- The code to be interpreted must have the format:%language code knowing that language should be a supported language.

- If the code is null or didn't respect the above format or include not supported language the error message will be displayed

- If the request exceeds the time out parameter an error message will ocurr. The paramater of the timeout is `pring.mvc.async.request-timeout` under notebook-server-webapp>>application.properties

### 3- Adding new Language
- Use the template package to add new language : notebook-server-new-lang-templ

- Add this language to the Enum LanguageEnum  (see example commented)

- Add the interpretor language in the local pom file of the module

- Add the new module in the general pom.xml
