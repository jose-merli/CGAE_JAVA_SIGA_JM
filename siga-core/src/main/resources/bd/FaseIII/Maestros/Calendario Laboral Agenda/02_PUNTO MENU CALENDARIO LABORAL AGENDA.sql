spool PUNTO MENU CALENDARIO LABORAL AGENDA.log
prompt PUNTO MENU CALENDARIO LABORAL AGENDA.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 
 
 Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('939','JGR','1','Y',SYSDATE,'0','Calendario Laboral Agenda','JGR_CalendarioLaboralAgenda','003','10');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendario Laboral Agenda','0','1',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendari Laboral Agenda','0','2',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendario Laboral Agenda#EU','0','3',SYSDATE,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendario Laboral Agenda#GL','0','4',SYSDATE,'0','19');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendario Laboral Agenda','0','1',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendari Laboral Agenda','0','2',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Calendario Laboral Agenda#EU','0','3',SYSDATE,'0','19');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.maestros.calendarioLaboralAgenda','Costes Fijos#GL','0','4',SYSDATE,'0','19');

Insert into GEN_MENU 
(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('939','21823','160','128',SYSDATE,'0',null,'menu.justiciaGratuita.maestros.calendarioLaboralAgenda',null,'939','1','calendarioLaboralAgenda',null,null);

spool PUNTO MENU CALENDARIO LABORAL AGENDA.log
prompt PUNTO MENU CALENDARIO LABORAL AGENDA.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .
 