## Prerequisites

### Setting up Java
- Download and install Java 11 https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html#license-lightbox
- Setup Environment Variables:
  - Add 'JAVA_HOME' as a system variable  ("c:\Program Files\Java\jdk-11\")
  - Add "%JAVA_HOME\%bin" to the 'PATH' variable
  - Add the common binaries to the 'PATH' variable ("c:\Program Files\Common Files\Oracle\Java\javapath\")

### Setting up Maven
- Download from https://maven.apache.org/download.cgi
- Add the Maven\bin folder to the 'PATH' variables ("c:\maven\apache-maven-3.6.3\bin")

### Setting IntelliJ IDEA Community Edition
- Download and install Intellij IDEA Community from https://www.jetbrains.com/idea/download/#section=windows
- Install plugin Cucumber for Java on "File > Settings > Plugins and search for Cucumber for Java"
- Specify the JDK 11 to be used on the Project. 
  - Go to File > Project Structure
  - Select Java 11 on SDK field
- Enable Lombok

## Building

- Git clone `https://engage2excel@dev.azure.com/engage2excel/CXS%20-%20Product%20Development/_git/QA.CXS.Admin.Sanity`

### Running tests from command line
- CD to the folder of the project
- Run all tests `mvn clean test`
- Run tests with specific tags `mvn clean test -Dcucumber.filter.tags="@sanity"`
- Run tests with specific browser `mvn clean test -Dselenide.browser=chrome` supported values: chrome, firefox, and ie
- Run tests with specific number of threads `mvn clean test -Dthreads.number=1` by default is 3
- Run tests with specific number of threads and specific browser `mvn clean test -Dthreads.number=1 -Dselenide.browser=firefox`
- Run tests with specific screen resolution `mvn clean test -Dselenide.browserSize=1920x1080` by default is 1366x768
- Run tests with specific timeout in milliseconds `mvn clean test -Dselenide.defaultTimeOut=15000` by default is 30000 milliseconds
- Run tests in headless mode `mvn clean test -Dselenide.headless=true` by default is false

### Running tests from Intellij Idea
- Open the project from Intellij Idea
- Run the `TestRunner.java` file


## Folder Structure
```bash
    ├── ...
    ├── test                    # Test files 
    │   ├── Constants           # Constants files
    │   ├── Database            # SQL queries
    │   ├── Models              # Objects to store business data test data
    │   ├── UI                  # Page Objects classes, Web elements, Commands, Conditions
    │   ├── API                 # Services and endpoints 
    │   ├── Steps               # Step Definition classes       
    │   └── Utils               # Utilities classes              
    └── ...
```

## SQL Connection
- if contentManagement.db.windows.auth is false then SQL credentials is used
- if contentManagement.db.windows.auth is true then Windows Auth is used
  * Download and Extract Zip file [sqljdbc](https://learn.microsoft.com/en-us/sql/connect/jdbc/release-notes-for-the-jdbc-driver?view=sql-server-ver16) and follow below 2 steps
    1. Copy dll mssql-jdbc_auth-12.2.0.x64.dll from path extracted folder
    2. Paste in bin folder of Java C:\Program Files\Java\jdk-11\bin