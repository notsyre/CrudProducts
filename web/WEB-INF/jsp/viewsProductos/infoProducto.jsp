<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head style="scroll-behavior: smooth" lang="es">
        <meta http-equiv="Content-Type" content="text/html">
        <meta charset="utf-8">
     
        <title>Info | Productos</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
        <style>
            .con {
                position: relative;
                font-family: monospace;
            }
            .equisd {
                position: absolute;
                right: 0;
                top: 0;
                font-size: 2rem;
                color: red;
                cursor: pointer;
            }
        </style>
    </head>
    <body class="bg-dark  w-100 min-vh-100 d-flex justify-content-center align-items-center">
        <div class="bg-light con border p-4 d-flex card1" style="max-width: 800px;">
            <a href="/CrudProducts/listUsers.html"><i class="bi bi-x equisd"></i></a>
            <img style="object-fit: contain; max-height: 150px" class="img-fuild m-auto d-block" src='<c:url value="${img}" />'/>
            <div class="card-body m-3">
                <h1 class="text-center">${nom}</h1>
                <p class="limit" style="text-align: justify">${des}</p>
                <input class="btn btn-success w-75 d-block m-auto" type="submit" value="$${val}"/>
            </div>  
        </div>
    </body>
</html>
