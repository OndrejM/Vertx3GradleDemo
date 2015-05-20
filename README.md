# Vertx3GradleDemo
Demo of a Vertx 3 application with Gradle build

## Development in Netbeans

Prerequisites: Netbeans Gradle support. Git support recommended.

1. Import as Gradle project
2. Build, Run and Debug work from context menu on the project
3. Execute Run/Debug. When code is changed, do Build and code is redeployed (because of Vertx auto-redeploy option)
4. Optionally in Netbeans options, mark compile on save in Gradle plugin -> Other

## Build and execute JAR

The build.gradle uses the Gradle shadowJar plugin to assemble the application and all it’s dependencies into a single "fat" jar.

To build the "fat jar"

    ./gradlew shadowJar

To run the fat jar:

    java -jar build/libs/Vertx3GradleDemo-3.0.0-SNAPSHOT-fat.jar

## Application documentation

After executio, point browser to http://localhost:8080