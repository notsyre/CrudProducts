package dao;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import models.ProductosBean;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author Juan
 */
public class ProductoDao {

    private static final String UPLOAD_DIRECTORY = "..\\..\\web\\public\\images\\photos";
    private static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    private static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    private static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;
    private JdbcTemplate jdbcTemplate;

    public String searchLastId() {
        // String sql2 = "select max(idProducto) + 1 from listaproductos";        
        String sql = "select idProducto from listaproductos order by idProducto desc";

        // List datos22 = this.jdbcTemplate.queryForList(sql2);
        //  System.out.println("dddddddddddd:::::" + datos22);
        List datosP = this.jdbcTemplate.queryForList(sql);
        System.out.println("dffffffffff:::" + datosP.get(0));
        if (datosP.isEmpty()) {
            datosP.add("{idProducto=0}");
        };

        String newInfo = datosP.get(0).toString().substring(12, datosP.get(0).toString().length() - 1);

        double resul = Double.parseDouble(newInfo) / Math.random();
        String dd = "f" + resul;
        return dd.replace(".", "f");

    }

    public void borrarImagen(String foto, String deletePath, int id) {
        ConexionDb dbCon = new ConexionDb();
        final String DELETE_DIRECTORY = "..\\..\\web\\";
        System.out.println("el deletePath:   " + deletePath);
        this.jdbcTemplate = new JdbcTemplate(dbCon.conectar());
        String deleteFile = deletePath + DELETE_DIRECTORY + foto;
        File borrar = new File(deleteFile);
        if (borrar.delete()) {
            String sql = "delete from listaproductos where idProducto = ?";
            jdbcTemplate.update(sql, id);
            System.out.println("lo borrado: " + deleteFile);
        } else {
            String sql = "delete from listaproductos where idProducto = ?";
            jdbcTemplate.update(sql, id);
            System.out.println("no se pudo borrar");
        }
    }

    public void updateUserWithoutPhoto(ProductosBean prod, List lista) {
        ConexionDb dbCon = new ConexionDb();
        this.jdbcTemplate = new JdbcTemplate(dbCon.conectar());
        ArrayList<String> listados = new ArrayList<>();
        for (int i = 0; i < lista.size(); i++) {
            FileItem fileItem = (FileItem) lista.get(i);
            listados.add(fileItem.getString());
        }
        prod.setNombre(listados.get(0));
        prod.setDesc(listados.get(1));
        prod.setValor(Integer.parseInt(listados.get(2)));
        System.out.println("sdffdfgsdhfgsd : " + listados.get(3));
        String sql = "update listaproductos set nombre = ?, descr = ?, valor = ? where idProducto = ?";

        this.jdbcTemplate.update(sql, prod.getNombre(),
                prod.getDesc(), prod.getValor(), prod.getIdProducto());

    }

    public void borrarImagenActualizada(String foto, String deletePath) {
        final String DELETE_DIRECTORY = "..\\..\\web\\";
        String deleteFile = deletePath + DELETE_DIRECTORY + foto;
        File borrar = new File(deleteFile);
        if (borrar.delete()) {
            System.out.println("borrado...");
        } else {
            System.out.println("no se pudo borrar (borrarImagenActualizada) foto: " + foto + "deletePath:" + deleteFile);
        }

    }

    public void updateUserConPhoto(
            ProductosBean prod,
            boolean isMultipart,
            HttpServletRequest request,
            List items) {
        ConexionDb dbCon = new ConexionDb();
        this.jdbcTemplate = new JdbcTemplate(dbCon.conectar());
        //___________________________-
        ArrayList<String> listados = new ArrayList<>();
        if (isMultipart) {
            DiskFileItemFactory file = new DiskFileItemFactory();

            file.setSizeThreshold(MEMORY_THRESHOLD);
            file.setRepository(new File(System.getProperty("java.io.tmpdir")));
            ServletFileUpload fileUpload = new ServletFileUpload(file);
            fileUpload.setFileSizeMax(MAX_FILE_SIZE);
            fileUpload.setSizeMax(MAX_REQUEST_SIZE);

            String uploadPath = request.getServletContext().getRealPath("") + File.separator + UPLOAD_DIRECTORY;

            // en esta linea debe ir el nombre de la ruta 
            String deletePath = request.getServletContext().getRealPath("") + File.separator;

            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            for (int i = 0; i < items.size(); i++) {
                FileItem fileItem = (FileItem) items.get(i);

                if (!fileItem.isFormField()) {
                    String fileName = new File(fileItem.getName()).getName();
                     //Random ss = new Random();

                    String filePath = uploadPath + File.separator + searchLastId() + listados.get(0) + "-" + fileName;

                    File uploadFile = new File(filePath);

                    String nameFile = ("public/images/photos/" + searchLastId() + listados.get(0) + "-" + fileName);

                    try {
                        uploadFile.delete();
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
            prod.setImgOld(listados.get(3));
            borrarImagenActualizada(prod.getImgOld(), deletePath);

        }
        //_______
        String sql = "update listaproductos set nombre = ?, img = ?, descr = ?, valor = ? where idProducto = ?";

        this.jdbcTemplate.update(sql, prod.getNombre(), prod.getImg(), 
                prod.getDesc(),  prod.getValor(), prod.getIdProducto());

    }

}
