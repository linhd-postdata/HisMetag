<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>File Upload</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="keywords" content="poesia"/>
    <meta name="description" content="Postdata"/>
    <meta name="autor" content="mluisadiezplatas"/>
    <meta name="robots" content="all,follow"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
    <!-- Optional theme -->


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="shortcut icon" href="../images/favicon.ico" type="image/x-icon"/>
    
    </head>
    <body>
        <div style="width:100%; borde:1px solid #e0922e;padding-top: 25px"><img src="/api/images/logodos.png" width="150"/></div>
        <span style="text-align: center">
            <h1>HisMetag</h1>
        <form method="post" action="rest/process/text"  id="form">
        <fieldset>
            <legend>Insert text</legend>
            <textarea  id="texto" name="texto" cols="200" rows="20" style="resize: both;"></textarea>
            <button type="submit" id="subm">Submit</button>
            <input type="button" value="Clear" onclick="clearContent()"> 
        </fieldset>
    </form></span>
        <script>
            function clearContent()
{
    document.getElementById("texto").value='';
}
            
            
        </script>
        
     


    </body>
    
</html>  


