package controllers;

import models.ProductosBean;
import dao.ProductoDao;
import dao.ConexionDb;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProductoController {

    private JdbcTemplate jdbcTemplate;

    public ProductoController() {
        ConexionDb dbCon = new ConexionDb();
        this.jdbcTemplate = new JdbcTemplate(dbCon.conectar());

    }

    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\public\\images\\photos";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    // ProductosBean Producto = new ProductosBean();
    //mostrar info
    @RequestMapping(value = "formAuth.html", method = RequestMethod.POST)
    public ModelAndView listProductos(HttpServletRequest request) {

        ModelAndView mav = new ModelAndView();

        String sql = "select * from listaproductos";
        List datosP = this.jdbcTemplate.queryForList(sql);

        mav.addObject("datos", datosP);
        mav.setViewName("viewsProductos/listProductos");
        return mav;
    }

    //Mostrar View para el rol Users
    @RequestMapping(value = "listUsers.html", method = RequestMethod.GET)
    public ModelAndView listUsers() {

        ModelAndView mav = new ModelAndView();

        String sql = "select * from listaproductos";
        List datosP = this.jdbcTemplate.queryForList(sql);

        mav.addObject("datos", datosP);
        mav.setViewName("viewsProductos/listProductsPublic");

        return mav;
    }

    //Mostrar View para el rol Users Filtrado
    @RequestMapping(value = "filterList.html", method = RequestMethod.POST)
    public ModelAndView listUsersFilter(HttpServletRequest req) {

        ModelAndView mav = new ModelAndView();

        String sql = req.getParameter("sql");
        List datosP = this.jdbcTemplate.queryForList(sql);

        mav.addObject("datos", datosP);
        mav.setViewName("viewsProductos/filterPages");

        return mav;
    }

    // Eliminar Producto
    @RequestMapping(value = "deleteProd.html", method = RequestMethod.GET)
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ModelAndView mav = new ModelAndView();
        ProductoDao pr = new ProductoDao();

        int id = Integer.parseInt(request.getParameter("id"));
        String deletePath = request.getServletContext().getRealPath("") + File.separator;
        String img = request.getParameter("img");

        pr.borrarImagen(img, deletePath, id);

        response.sendRedirect("http://localhost:8080/CrudProducts/listUsers.html");
        mav.setViewName("viewsProductos/listProductsPublic");

        return mav;
    }

    // Insertar Producto | Mostrar form
    @RequestMapping(value = "/insert.html", method = RequestMethod.GET)
    public ModelAndView Mostrar() {

        ModelAndView mav = new ModelAndView();
        mav.addObject("producto", new ProductosBean());

        mav.setViewName("viewsProductos/insertForm");

        return mav;

    }

    public String searchLastId() {
        String sql = "select idProducto from listaproductos order by idProducto desc";

        List datosP = this.jdbcTemplate.queryForList(sql);

        if (datosP.isEmpty()) {
            datosP.add("{idProducto=0}");
        };

        
        String newInfo = datosP.get(0).toString().substring(12, datosP.get(0).toString().length() - 1);

        int resul = Integer.parseInt(newInfo) + (int)  Math.random();
        String dd =  String.valueOf(resul);
        return dd.replace(".", "f");

    }

    // Insertar Producto | Funcion de Insertar un dato
    @RequestMapping(value = "/insert.html", method = RequestMethod.POST)
    public ModelAndView Mostrar(ProductosBean product, HttpServletRequest request, HttpServletResponse response) throws IOException {

        ProductosBean prod = new ProductosBean();
        ModelAndView mav = new ModelAndView();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);

        ArrayList<String> listados = new ArrayList<>();

        if (isMultipart) {

            DiskFileItemFactory file = new DiskFileItemFactory();
            file.setSizeThreshold(MEMORY_THRESHOLD);
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));

            ServletFileUpload fileUpload = new ServletFileUpload(file);

            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);

            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            List<FileItem> items = null;

            try {
                items = fileUpload.parseRequest(request);

            } catch (FileUploadException ex) {
                System.out.println("carga..." + ex.getMessage());
            }

            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);

                if (!fileItem.isFormField()) {
                    String fileName = new File(fileItem.getName()).getName();
                    String filePath = uploadPath + File.separator + searchLastId() + fileName;

                    File uploadFile = new File(filePath);
                    String nameFile = ("public/images/photos/" + searchLastId() + fileName);

                    try {
                        fileItem.write(uploadFile);
                        prod.setImg(nameFile);

                    } catch (Exception e) {
                        System.out.println("escritura..." + e.getMessage());
                    }

                } else {
                    listados.add(fileItem.getString());

                }

            }
            prod.setNombre(listados.get(0));
            prod.setDesc(listados.get(1));
            prod.setValor(Integer.parseInt(listados.get(2)));

        }
        // ----------------------------------------------------------------
        String sql = "insert into listaproductos(nombre, img, descr, valor) values (?,?,?,?)";
        this.jdbcTemplate.update(sql,
                prod.getNombre(),
                prod.getImg(),
                prod.getDesc(),
                prod.getValor()
        );

        mav.addObject("producto", new ProductosBean());
        response.sendRedirect("/CrudProducts/listUsers.html");
        mav.setViewName("viewsProductos/listProductsPublic");
        return mav;

    }

    // Actualizar PrAoducto | mostrar form con campos llenos
    @RequestMapping(value = "edit.html", method = RequestMethod.GET)
    public ModelAndView editForm(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        int id = Integer.parseInt(request.getParameter("id"));
        String ima = request.getParameter("ima");

        ProductosBean Producto = consultarProducto(id);
        Producto.setImgOld(ima);
        mav.addObject("producto", Producto);

        mav.setViewName("viewsProductos/editForm");

        return mav;
    }

    // Actualizar Producto | Funcion de actualizar 
    @RequestMapping(value = "edit.html", method = RequestMethod.POST)
    public ModelAndView sqlUpdate(ProductosBean producto, HttpServletResponse response, HttpServletRequest request) throws IOException {
        ModelAndView mav = new ModelAndView();
        ArrayList<String> listados = new ArrayList<>();
        ProductoDao pr = new ProductoDao();

        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        DiskFileItemFactory file = new DiskFileItemFactory();
        ServletFileUpload fileUpload = new ServletFileUpload(file);
        List<FileItem> items = null;

        String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

        String deletePath = request.getServletContext().getRealPath("") + File.separator;

        try {

            items = fileUpload.parseRequest(request);
            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);
                listados.add(fileItem.getString());

            }

        } catch (FileUploadException ex) {
            System.out.println("error en la carga de la imagen" + ex.getMessage());
        }

        System.out.println("listados 3333:    " + listados.get(1));

        if (listados.get(1).isEmpty() || listados.get(1).equals("") || listados.get(1).equals(null)) {
            pr.updateUserWithoutPhoto(producto, items);
        } else {

            pr.updateUserConPhoto(producto, isMultipart, request, items);
            System.out.println("Algoooo23232:" + producto.getImg());

        }

        response.sendRedirect("http://localhost:8080/CrudProducts/listUsers.html");
        mav.setViewName("viewsProductos/listProductsPublic");

        return mav;

    }

    @RequestMapping(value = "details.html", method = RequestMethod.POST)
    public ModelAndView details(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView();

        String img = request.getParameter("img");
        String nom = request.getParameter("nom");
        String des = request.getParameter("des");
        String val = request.getParameter("val");

        mav.addObject("img", img);
        mav.addObject("nom", nom);
        mav.addObject("des", des);
        mav.addObject("val", val);

        mav.setViewName("viewsProductos/infoProducto");
        return mav;
    }

    // Función para obtener los datos de los campos de los formularios.
    public ProductosBean consultarProducto(int id) {
        ProductosBean product = new ProductosBean();

        String sql = "select * from listaproductos where idProducto = " + id;

        return (ProductosBean) jdbcTemplate.query(sql,
                new ResultSetExtractor<ProductosBean>() {
            public ProductosBean extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    product.setId(rs.getInt("idProducto"));
                    product.setNombre(rs.getString("nombre"));
                    product.setImg(rs.getString("img"));
                    product.setDesc(rs.getString("descr"));
                    product.setValor(rs.getInt("valor"));
                }

                return product;
            } // fin del exData
        }// fin ResultSetExtractor
        ); // fin del query
    }// fin del metodo
}
