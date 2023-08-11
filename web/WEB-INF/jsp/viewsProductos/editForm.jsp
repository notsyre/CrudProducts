<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Formulario | Update</title>
        <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/3447/3447560.png" type="image/x-icon">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
        <style>
            .fo {
                border: dashed 1px black;
                font-family: monospace;
            }

        </style>
    </head>
    <body class="bg-dark w-100 min-vh-100 bg-light d-flex justify-content-center align-items-center flex-column">
        <h1 class="text-center text-white">Actualizar Producto</h1> 

        <form:form cssClass="w-25 rounded p-3 fo" enctype="multipart/form-data" commandName="producto">
            <div class="textico "></div>
            <div>
                 <img width="60%" class="img-fluid m-auto d-block" src='<c:url value="${producto.img}" />'/>
            </div>
            <form:label path="nombre" class="text-white">Nombre del Producto:</form:label>
            <form:input cssClass="form-control nom" path="nombre"></form:input>



            <form:label path="img" class="text-white">URL del Producto:</form:label>
            <form:input cssClass="form-control arch" multiple="false" type="file"  path="img"></form:input>
          
            <form:label path="desc" class="text-white">Descripción:</form:label>
            <form:textarea cssClass="form-control des" rows="4" path="desc"/>

            <form:label path="valor" class="text-white">Valor del Producto: </form:label>
            <form:input cssClass="form-control val" type="number"  path="valor"></form:input>

            <form:label cssClass="d-none" path="imgOld">URL del Producto:</form:label>
            <form:input cssClass="form-control" multiple="false" type="hidden"   path="imgOld"></form:input>


                <button class="btn btn-secondary d-block mx-auto mt-1 w-50 btnEnviar">Enviar</button>
        </form:form>

        <script>
            const Nombre = document.querySelector(".nom")
            const Archivo = document.querySelector(".arch")
            const Des = document.querySelector(".des")
            const Valor = document.querySelector(".val")
            const Enviar = document.querySelector(".btnEnviar")
            const Textico = document.querySelector(".textico")

            const messages = {
                msg0: `<div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>¡Error! </strong>Campo vacio.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                      </div>`,
                msg1: `<div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>¡Error! </strong>Archivo Invalido.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                      </div>`,
                msg2: `<div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>¡Error! </strong>Valor Fuera de Rango.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                      </div>`,
                msg3: `<div class="alert alert-danger alert-dismissible fade show" role="alert">
                        <strong>¡Error! </strong>Número Invalido.
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                      </div>`
            }

            function validarr(e, t) {
                e
                Textico.innerHTML = t
            }

            Enviar.addEventListener("click", (e) => {
                if (Nombre.value == "" || Des.value == ""  || Valor.value == "") {
                    validarr(e.preventDefault(), messages.msg0)
                } else if (Archivo.value.slice(-3).toLowerCase() !== "jpg"
                        && Archivo.value.slice(-3).toLowerCase() !== "png"
                        && Archivo.value.slice(-3).toLowerCase() !== "")
                {
                    validarr(e.preventDefault(), messages.msg1)
                } else if (Nombre.value.length > 30 || Des.value.length >= 65535 || Archivo.value.length > 65535 || Valor.value.length > 10) {
                    validarr(e.preventDefault(), messages.msg2)
                } else if (!parseInt(Valor.value) || Valor.value.includes(".") || Valor.value.includes(",")) {
                    validarr(e.preventDefault(), messages.msg3)

                } else {

                }
            })
        </script>
    </body>
</html>
