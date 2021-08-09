spool 10_Creacion_mensajes.log
prompt 10_Creacion_mensajes.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--HACEMOS EL INSERT DE LAS LABELS UTILIZADAS
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepto de facturaci�n introducido ya existe.','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepte de facturaci� introdu�t ja existeix.','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepto de facturaci�n introducido ya existe. #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepto de facturaci�n introducido ya existe. #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No se han introducido Conceptos de facturaci�n. El importe resultante ser� 0 �. �Desea continuar?','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No s''han introdu�t Conceptes de facturaci�. L''import resultant ser� 0 �. Voleu continuar?','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No se han introducido Conceptos de facturaci�n. El importe resultante ser� 0 �. �Desea continuar? #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No se han introducido Conceptos de facturaci�n. El importe resultante ser� 0 �. �Desea continuar? #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
