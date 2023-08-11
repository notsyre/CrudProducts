<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;charset=utf-8"">
        <meta charset="UTF-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <meta name="viewport" content="width=device-width, initial-scale=1.0" />
        <title>Lista | Productos</title>
        <link rel="shortcut icon" href="https://cdn-icons-png.flaticon.com/512/6203/6203085.png" type="image/x-icon">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-KK94CHFLLe+nY2dmCWGMq91rCGa5gtU4mk92HdvYe+M/SXH301p5ILy+dN9+nJOZ" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ENjdO4Dr2bkBIFxQpeoTz1HIcje39Wm4jDKdf19U8gI4ddQ3GYNS7NTKfAdVQSZe" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css">
        <style>
            .card2 {
                display: none;

            }

            .limit {
                overflow: hidden;
                text-overflow: ellipsis;
                display: -webkit-box;
                -webkit-line-clamp: 3;
                -webkit-box-orient: vertical;
                font-family: monospace;
            }

            .header {
                background-color: white;
                font-family: monospace;
            }

            .fix {
                position: fixed;
                left: 0;
                

            }

            .filtrar {
                width: 30%;
            }

            body::-webkit-scrollbar{
                width:16px
            }
            body::-webkit-scrollbar-thumb{
                
                height:56px;
                border-radius:8px;
                border:4px solid transparent;
                background-clip:content-box;
                background-color:rgb(90, 90, 90)
            }
        </style>
    </head>
    <body class="bg-dark">
        <header class="">
            <div class="d-flex justify-content-between align-items-center px-4">
                <form action="formAuth.html" method="POST">
                    <button class="btn btn-light"> <i class="">Volver</i></button>

                </form>

                <h1 class="position-absolute top-0 start-50 translate-middle-x text-white">LISTA DE PRODUCTOS</h1>

            </div>

   
        </header>



            </div>
            <div  class="contenedor d-flex flex-wrap justify-content-center">

                <c:if test="${not empty datos}">
                    <c:forEach var="d" items="${datos}">
                        <form action="details.html" method="POST">
                            <input type="hidden" name="nom" value="${d.nombre}"/>
                            <input type="hidden" name="img" value="${d.img}"/>
                            <input type="hidden" name="des" value="${d.descr}"/>
                            <input type="hidden" name="val" value="${d.valor}"/>
                            <div class="card w-100 m-1 card1" style="max-width: 300px;">
                                <img style="object-fit: contain; max-height: 150px" class="img-fuild m-auto d-block" src='<c:url value="${d.img}" />'/>
                                <div class="card-body">
                                    <h1 class="text-center">${d.nombre}</h1>
                                    <p class="limit" style="text-align: justify">${d.descr}</p>
                                    <input class="btn btn-success w-75 d-block m-auto" type="submit" value="$${d.valor}"/>
                                </div>  
                            </div>
                        </form>


                    </c:forEach>
                </c:if>
                <c:if test="${ empty datos}">
                    <h1>NO HAY PRODUCTOS REGISTRADOS</h1>

                </c:if>

                <form action="details.html" method="POST">
                    <div class="contenedor2 w-100 d-flex flex-wrap justify-content-between">
                        <div class="card2 card w-100 m-1" style="max-width: 300px;">
                            <img style="object-fit: contain; max-height: 150px" class="img-fuild m-auto d-block" />
                            <div class="card-body">
                                <h1 class="text-center"></h1>
                                <p style="text-align: justify" class="limit"></p>
                                <input type="submit" class="btn btn-success d-block m-auto" />
                            </div> 
                            <input type="hidden" name="nom" />
                            <input type="hidden" name="img" />
                            <input type="hidden" name="des" />
                            <input type="hidden" name="val"/>

                        </div>
                    </div>
                </form>
            </div>
        </main>



        <script>
            const header = document.querySelector(".header");
            const contenedorPrin = document.querySelector(".contenedorPrin");
            const fix = document.querySelector(".fix");
            const search = document.querySelector(".searhcBar");
            const contenedor = document.querySelector(".contenedor");
            const fill = document.querySelector(".filtrar");
            const contenedor2 = document.querySelector(".contenedor2");
            const lup = document.querySelector(".lup");
            const card1 = document.querySelectorAll(".card1");
            const card2 = document.querySelector(".card2");
            let info = "${datos}";
            let inf1 = info.replaceAll('idProducto=', '"idProducto":');
            let inf2 = inf1.replaceAll('nombre=', '"nombre":"');
            let inf3 = inf2.replaceAll(', img', '", "img"');
            let inf4 = inf3.replaceAll('"img"=', '"img":"');
            let inf5 = inf4.replaceAll(', descr', '", "descr"');
            let inf6 = inf5.replaceAll('"descr"=', '"descr":"');
            let inf7 = inf6.replaceAll(', valor', '", "valor"');
            let inf8 = inf7.replaceAll('"valor"=', '"valor":');

            let datos = JSON.parse(inf8);

            function none(img, titulo, des, val) {

                for (let index = 0; index < card1.length; index++) {
                    card1[index].style.display = "none";
                }

                card2.style.display = "block";
                card2.children[0].src = img;
                card2.children[1].children[0].textContent = titulo;
                card2.children[1].children[1].textContent = des;
                card2.children[1].children[2].value = "$" + val;
                card2.children[2].value = titulo;
                card2.children[3].value = img;
                card2.children[4].value = des;
                card2.children[5].value = val;
            }


            function block() {
                for (let index = 0; index < card1.length; index++) {
                    card1[index].style.display = "block";
                }
                card2.style.display = "none";
            }

            search.addEventListener("input", () => {

                for (let index = 0; index < datos.length; index++) {
                    if (datos[index].nombre.toUpperCase() === search.value.toUpperCase()) {
                        console.log("si");
                        none(datos[index].img, datos[index].nombre, datos[index].descr, datos[index].valor);
                        break
                    } else {
                        block();
                    }
                }
            });

            contenedorPrin.style.marginTop = header.clientHeight + "px"
            fix.style.top = header.clientHeight + "px"
           


        </script>


    </body>
</html>

