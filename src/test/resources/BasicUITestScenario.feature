Feature: Basic url mapping

    Scenario: static file mapping
        When
            url is http://localhost:8081/demo/static/index.html
        Then
            display content of resources/webroot/index.html file

    Scenario: hello world (manual raw response) mapping
        When 
            url is http://localhost:8081/demo/hello
        Then
            display "Hello World from Vertx-web!" message

    Scenario: resource not found mapping
        When
            url is http://localhost:8081/demo
            OR
            url is http://localhost:8081
        Then
            display Vertx error message