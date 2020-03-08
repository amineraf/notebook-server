## notebook-server
Java / Spring Boot Notebook Server can execute and include a code from differents language within there interpretors on java.

 ### 1- Installation
Clone project into a local folder.

$ git clone https://github.com/amineraf/notebook-server

####  Maven Installation

Maven
First Step will be to install latest version of Maven.

Build and Run project
Once every thing is installed, run a maven clean install package or maven package in the project so the jar file can be generated. (You can skip Tests).

**$ mvn clean install**

Then You can run the project using the java -jar command.

**$ java -jar notebook-server-webapp/target/notebook-interpreter-1.0-SNAPSHOT.jar**

### 2- Usage
- Api End-PointThe Interpreter API is available via http POST method at:/executeInterpreter request bodyThe /execute interpreter End-Point accepts JSON as request body.

- The json object must have the following format `{"code": "string", "sessionId": "string"}`

- With code is mandatory and sessionid is optional (can be generated from the app)

- Code should respect the following format %(as key word)+_[supported language]_+_[script code]_

- example `{"code": "%python print 1+1 ", "sessionId": "96385214524588888"}`

- The Pyaload should be as follow  via curl:
 $ `curl -X POST http://localhost:8080/execute -d '{"code": "%python print 1+1 ", "sessionId": "96385214524588888"}'`
- The code to be interpreted, it must have the format:%language code knowing that  language is the supported languages.
- If the code is null or it didn't the above format or include not supported language the error message will be displayed
- If the request excedd the time out parameter an error message will ocurr. The paramater of the time out is `pring.mvc.async.request-timeout` under notebook-server-webapp>>application.properties


