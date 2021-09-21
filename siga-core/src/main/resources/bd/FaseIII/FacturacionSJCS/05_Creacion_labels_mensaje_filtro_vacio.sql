spool 05_Creacion_labels_mensaje_filtro_vacio.log
prompt 05_Creacion_labels_mensaje_filtro_vacio.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--HACEMOS EL INSERT DE LAS LABELS UTILIZADAS
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeFiltroVacio','Debe rellenar al menos un filtro de búsqueda','0','1',to_date('28/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeFiltroVacio','Ha d''omplir a l''almenys un filtre de cerca','0','2',to_date('28/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeFiltroVacio','Debe rellenar al menos un filtro de búsqueda #EU','0','3',to_date('28/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.buscarFacturacion.mensajeFiltroVacio','Debe rellenar al menos un filtro de búsqueda #GL','0','4',to_date('28/11/19','DD/MM/RR'),'0','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
