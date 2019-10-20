# Tekken

Tekken is a framework web in Java language using technologies VertX, MySQL, Redis and MongoDB.   

## How is work ?

Tekken is composed of two parts : Back-end and front-end
The front-end is the part of web html. We always keep the basic html code except that we add a configuration at the beginning of the page 

## Part of front-end ?

This part allows for display page html, here is how to configure Tekken in the html code:
```html
<html>
  <tekken>
    <backend>com.projet.className</backend>
    <getter>/home</getter>
  </tekken>
  <body>
    <h1>Welcome on Tekken</h1>
    <p>This variable is edit by backend : '{myVariable}'</p>
  </body>
</html>
```
Tekken's configuration does not appear when displayed on the web

Signposting                                       |Usefulness
--------------------------------------------------|------
```<backend>com.example.class<backend>```         | Setup a backend
```<cache>true/false</cache>```                   | Enable/Disable cache on this page
```<getter>/rooter<getter>```                     | Url for access, It's possible to accumulate them


## Part of back-end ?
The part allows for make the page more dynamic, it's made in Java or one implement a Java class ```Website```
Here is an example code:
```java
public class Home implements Website {

    @Override
    public Response handler(Controller controller, Request request, Response response) {

        response.setValue("valor", "AntoZzz");
        return response;
    }
}
```
The method ```handler``` is implemented by Website, It proposes 3 arguments:
 - Controller allows you to access the module as mysql etc ...
 - Request allows you to access the information on request
 - Response allows you to edit the response which will be sent later on

