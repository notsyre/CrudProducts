
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista | Productos</title>
        <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/6203/6203085.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <style>
             .limit {
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                font-family: monospace;
            }
            
             body::-webkit-scrollbar{
                width:16px
            }
            body::-webkit-scrollbar-thumb{
                font-family: monospace;
                height:56px;
                border-radius:8px;
                border:4px solid transparent;
                background-clip:content-box;
                background-color:rgb(90, 90, 90)
            }
        </style>
        
    </head>
    <body class="bg-dark">
        <div class="align-items-center">

            <h1 class="text-center text-white shadow p-3 mb-5">LISTA DE PRODUCTOS LÁCTEOS</h1>

      
        </div>

        <a class="btn btn-light mx-2" href="insert.html">Agregar</a>
        <div class="w-100 d-flex flex-wrap justify-content-center p-2">

            <c:forEach var="d" items="${datos}">


                <div class="card w-100 m-1" style="max-width: 300px;">
                    <img class="img-fuild" style="object-fit: contain; max-height: 150px" src='<c:url value="${d.img}" />'/>
                    <div class="card-body">
                        <h1 class="text-center">${d.nombre}</h1>
                        <p style="text-align: justify" class="limit">${d.descr}</p>
                        <input class="btn btn-success d-block m-auto" value="$${d.valor}"/>
                    </div>
                    <div class="d-flex justify-content-between align-items-center px-4"> 
                        <a class="btn btn-dark " href="deleteProd.html?id=${d.idProducto}&img=${d.img}"><i class="">Eliminar</i></a> 
                        <a href="edit.html?id=${d.idProducto}&ima=${d.img}" class="btn btn-dark"><i class="">Editar</i></a>
                    </div>


                </div>





            </c:forEach>
        </div>






        <%--    
    <c:if test="${ empty user || empty pass}">
                <c:redirect  url="formAuth.html">
                    <c:param  name="error" value="Error"/>
                    <c:param name="msj" value="campos vacios"/>
                </c:redirect>
            </c:if>

        <c:if test="${!user.contains('@') && !user.contains('.') }">
            <c:redirect  url="formAuth.html" >
                <c:param name="error" value="Error"/>
                <c:param name="msj" value="Falta el @ o el . en el correo"/>
            </c:redirect>
        </c:if> 
        --%>

    </body>
</html>
