spool 10_Creacion_mensajes.log
prompt 10_Creacion_mensajes.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--HACEMOS EL INSERT DE LAS LABELS UTILIZADAS
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepto de facturación introducido ya existe.','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepte de facturació introduït ja existeix.','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepto de facturación introducido ya existe. #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.conceptoExistente','El concepto de facturación introducido ya existe. #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No se han introducido Conceptos de facturación. El importe resultante será 0 €. ¿Desea continuar?','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No s''han introduït Conceptes de facturació. L''import resultant serà 0 €. Voleu continuar?','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No se han introducido Conceptos de facturación. El importe resultante será 0 €. ¿Desea continuar? #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.mensaje.noConceptos','No se han introducido Conceptos de facturación. El importe resultante será 0 €. ¿Desea continuar? #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
