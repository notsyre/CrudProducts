/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author Juan
 */
public class ProductosBean {

    private int idProducto;
    private String img;
      private String imgOld;
    private String nombre;
    private String desc;
    private int valor;

    public ProductosBean() {
    }

    public ProductosBean(int idProducto, String img, String nombre, String desc, int valor) {
        this.idProducto = idProducto;
        this.img = img;
        this.nombre = nombre;
        this.desc = desc;
        this.valor = valor;

    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setId(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
    
    
    
     public String getImgOld() {
        return imgOld;
    }

    public void setImgOld(String imgOld) {
        this.imgOld = imgOld;
    }
    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

}
