package com.example.examenfinalpmo1_p3.Configuracion;

public class Transacciones {
    public static final String NameDatabase = "DBSitio";
    public static final String NameDatabase2 = "DBvideo";

    public static final String tablaDatos = "sitioweb";

    public static final String id = "id";
    public static final String descripcion = "descripcion";
    public static final String fecha = "fecha";
    public static final String pathImage = "pathImage";
    public static final String image = "image";

    public static final String TablaVideo = "grabarvideo";

    public static final String idvideo = "id";
    public static final String video = "video";

    public static final String CreateTableDatos = "CREATE TABLE "+tablaDatos+"("+
            id+" INTEGER PRIMARY KEY AUTOINCREMENT,"+
            descripcion+" TEXT, "+
            fecha+" TEXT," +
            pathImage+" TEXT, "+
            image+" BLOB) ";

    public static final String CreateTableVideo = "CREATE TABLE grabarvideo (idvideo INTEGER PRIMARY KEY AUTOINCREMENT,video BLOB)";
    public static final String DropTableVideo = "DROP TABLE IF EXISTS grabarvideo";
    public static final String test1 = "SELECT * FROM grabarvideo";


    public static final String DropTableDatos = "DROP TABLE IF EXISTS " + tablaDatos;

    public  static final String consultDatos = "SELECT * FROM "+tablaDatos;
}
