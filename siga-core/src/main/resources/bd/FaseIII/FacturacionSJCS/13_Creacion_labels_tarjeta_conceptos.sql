spool 13_Creacion_labels_tarjeta_conceptos.log
prompt 13_Creacion_labels_tarjeta_conceptos.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--HACEMOS EL INSERT DE LAS LABELS UTILIZADAS
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroCriterios','N�mero de Criterios','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroCriterios','Nombre de Criteris','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroCriterios','N�mero de Criterios #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroCriterios','N�mero de Criterios #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroPagos','N�mero de Pagos','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroPagos','Nombre de Pagaments','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroPagos','N�mero de Pagos #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.numeroPagos','N�mero de Pagos #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.pagos','Pagos','0','1',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.pagos','Pagaments','0','2',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.pagos','Pagos #EU','0','3',to_date('17/12/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('facturacionSJCS.facturacionesYPagos.pagos','Pagos #GL','0','4',to_date('17/12/19','DD/MM/RR'),'0','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
