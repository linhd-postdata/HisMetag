# CHANGES

## Classes Lexer and Output

Class variables that were being written have the static declarations removed. 
Some of that variables now have the private declaration, and some methods had been created in order to can do get and set.

Classes that were using those variables, like classes in the ContextProcessing folder, now recieve the class by the constructor or as a method parameter.

## Class Main

Now, ejecutar method recieves one more parameter, a boolean called tei that means if the output has to be in json or tei format.

## Endpoints

Endpoints "/uploadnew", "/upload", "/uploadtext" and "/process/processpost" have been removed. 
Some new endpoints have been added:

1. /file
2. /file/{file}
3. /process/text
4. /process/file

Now all the endpoints are located in the rest folder.

## Docker

A docker-compose file have been added, so hismetag can be executed by the command 
```docker-compose up```