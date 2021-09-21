spool 01_Creacion_menu_FacturacionesYPagos.log
prompt 01_Creacion_menu_FacturacionesYPagos.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--CREAMOS EL RECURSO NUEVO PARA EL MENU
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturaciones y Pagos','0','1',to_date('26/11/19','DD/MM/RR'),'0','3');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturacions i Pagaments','0','2',to_date('26/11/19','DD/MM/RR'),'0','3');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturaciones y Pagos#EU','0','3',to_date('26/11/19','DD/MM/RR'),'0','3');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturaciones y Pagos#GL','0','4',to_date('26/11/19','DD/MM/RR'),'0','3');

--HACEMOS EL MISMO INSERT EN GEN_DICCIONARIO PARA EL MENU
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturaciones y Pagos','0','1',to_date('26/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturacions i Pagaments','0','2',to_date('26/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturaciones y Pagos#EU','0','3',to_date('26/11/19','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.facturacionesYPagos','Facturaciones y Pagos#GL','0','4',to_date('26/11/19','DD/MM/RR'),'0','15');


--ACTUALIZAMOS EL RECURSO DEL MENU REUTILIZADO
UPDATE GEN_MENU SET IDRECURSO='menu.facturacionSJCS.facturacionesYPagos', PATH='facturacionesYPagos' WHERE IDMENU='600';

--BORRAMOS LAS PREVISIONES, YA NO SE USARA
DELETE FROM GEN_MENU WHERE IDMENU='616';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off