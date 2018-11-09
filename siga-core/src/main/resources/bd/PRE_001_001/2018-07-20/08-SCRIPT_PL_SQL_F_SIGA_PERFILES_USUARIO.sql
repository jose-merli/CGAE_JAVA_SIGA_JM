create or replace function        F_SIGA_PERFILES_USUARIO(CODIGO_INSTITUCION IN NUMBER,
       CODIGO_USUARIO IN NUMBER)
return varchar2 is
/****************************************************************************************************************/
/* Nombre:        F_SIGA_PERFILES_USUARIO                                                                   */
/* Descripcion:   Funcion para la optencion de los codigos de los perfiles a los que pertenece un usuario,      */
/*                separados por ; y con fecha_baja a null                                                                        */
/*                                                                                                          */
/* Parametros            IN/OUT   Descripcion                                                    Tipo de Datos  */
/* -------------------   ------   ------------------------------------------------------------   -------------  */
/* CODIGO_INSTITUCION    IN       Codigo de institucion                                             NUMBER(5)      */
/* CODIGO_USUARIO        IN       Codigo de usuario                                             NUMBER(5)      */
/*                                                                                                  */
/* Version:        1.1                                                                              */
/* Fecha Creacion: 11/01/2005                                                                                   */
/* Autor:         Jose Zulueta                                                                        */
/* Fecha Modificacion   Autor Modificacion                  Descripcion Modificacion                            */
/* ------------------   ---------------------------------   --------------------------------------------------- */
/****************************************************************************************************************/


 V_RESULT          VARCHAR2(4000);
 V_PERFILES        VARCHAR2 (5);
 TYPE CTIPO        IS REF CURSOR ;
 CL                CTIPO;
 BEGIN
 OPEN CL for 'SELECT IDPERFIL FROM ADM_USUARIOS_EFECTIVOS_PERFIL WHERE IDUSUARIO='||CODIGO_USUARIO||' AND IDINSTITUCION='||CODIGO_INSTITUCION||' AND FECHA_BAJA IS NULL';
  LOOP
    FETCH CL INTO V_PERFILES;
    EXIT WHEN CL%NOTFOUND;
     V_RESULT := V_RESULT || V_PERFILES || '; ';
    END LOOP;
  CLOSE CL;
  V_RESULT := RTRIM((V_RESULT));
  V_RESULT :=(substr(V_RESULT,1,(length(V_RESULT)-1)));
  RETURN V_RESULT;

END F_SIGA_PERFILES_USUARIO;