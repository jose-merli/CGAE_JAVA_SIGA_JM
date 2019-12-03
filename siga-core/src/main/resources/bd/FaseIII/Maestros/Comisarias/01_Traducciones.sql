spool 01_Traducciones.log
prompt 01_Traducciones.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.mantenimientoGestionComisarias','Gestión Comisarías','0','1',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.mantenimientoGestionComisarias','Gestió Comissaries','0','2',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.mantenimientoGestionComisarias','Gestión Comisarías#EU','0','3',to_date('04/02/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.facturacionSJCS.mantenimientoGestionComisarias','Gestión Comisarías#GL','0','4',to_date('04/02/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.comisaria.info.movilVisible','Móvil visible','0','1',to_date('30/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.comisaria.info.movilVisible','Mòbil visible','0','2',to_date('30/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.comisaria.info.movilVisible','Móvil visible#GL','0','4',to_date('30/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.maestros.comisaria.info.movilVisible','Móvil visible#EU','0','3',to_date('30/09/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.comisarias.existeComisariaMismoNombre','Ya existe una comisaría con ese nombre','0','1',to_date('24/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.comisarias.existeComisariaMismoNombre','Ja hi ha una comissària amb aquest nom','0','2',to_date('24/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.comisarias.existeComisariaMismoNombre','Ya existe una comisaría con ese nombre#GL','0','4',to_date('24/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.comisarias.existeComisariaMismoNombre','Ya existe una comisaría con ese nombre#EU','0','3',to_date('24/10/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
