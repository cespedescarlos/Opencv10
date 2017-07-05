package com.example.cespe.opencv10.Conexion;

import org.json.JSONArray;

/**
 * Created by cespe on 10/06/2017.
 */

public interface Retorno_Consulta {
    public static final String D_Documento="D_Documento";
    public static final String D_Usuario="D_Usuario";
    public static final String D_Tipo_Trabajo="D_Tipo_Trabajo";
    public static final String D_Empresa="D_Empresa";
    public static final String D_Rol="D_Rol";
    public static final String D_Imagen="D_Imagen";
    public static final String D_Permiso="D_Permiso";
    public static final String N_Documento="N_Documento";
    public static final String P_Rol="P_Rol";
    public static final String Navi="Navi";
    public static final String MainActivity="MainActivity";

    public static final String Operacion_Especifica_Insertar_Tipo_trabajo ="insertar_tipo_trabajo";
    public static final String Operacion_Especifica_Insertar_Usuario ="insertar_usuario";
    public static final String Operacion_Especifica_Insertar_Rol ="insertar_rol";
    public static final String Operacion_Especifica_Insertar_Empresa ="insertar_empresa";
    public static final String Operacion_Especifica_get_id_tipo_trabajo ="get_id_tipo_trabajo";
    public static final String Operacion_Especifica_get_id_empresa ="get_id_empresa";
    public static final String Operacion_Especifica_get_id_rol ="get_id_rol";
    public static final String Operacion_Especifica_get_id_permiso ="get_id_permiso";
    public static final String Operacion_Especifica_Insertar_Imagen ="insertar_imagen";
    public static final String Operacion_Especifica_Insertar_MomentosHu ="insertar_momentoshu";
    public static final String Operacion_Especifica_Max_Id_Imagen="max_id_imagen";
    public static final String Operacion_Especifica_Id_segun_momento_Imagen="id_imagen_segun_momento";
    public static final String Operacion_Especifica_Insertar_Documento ="insertar_documento";
    public static final String Operacion_Especifica_Max_Id_Documento ="max_id_documento";
    public static final String Operacion_Especifica_Max_Id_Rol ="max_id_rol";
    public static final String Operacion_Especifica_todos_Documento ="todos_documento";
    public static final String Operacion_Especifica_get_documento_segun_nombre ="documentos segun nombre";
    public static final String Operacion_Especifica_todas_empresas ="todas empresas";
    public static final String Operacion_Especifica_todos_permisos ="todos permiso";
    public static final String Operacion_Especifica_todos_rol ="todoss rol";
    public static final String Operacion_Especifica_todos_usuario ="todoss usuario";
    public static final String Operacion_Especifica_todos_tipos_trabajo ="todos tipos trabajos";
    public static final String Operacion_Especifica_get_segun_id_imagen_Documento ="get_documento_id_imagen";
    public static final String Operacion_Especifica_get_id_validar_usuario ="get_id validar_usuario";
    public static final String Operacion_Especifica_get_id_usuario ="get_id_usuario";
    public static final String Operacion_Especifica_verificar_permiso_rol ="verificar permiso rol";
    public static final String Operacion_Especifica_asignar_permiso_rol ="asignar permiso rol";

    public void finalizo(JSONArray respuesta,int volley_operacion,String mensaje,
                         String mensaje_extra,String Clase_origen,String operacion_especificacion);
}
