spool 01_AñadidoPuntoDeMenu-Inscripciones.log
prompt 01_AñadidoPuntoDeMenu-Inscripciones..log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_PROCESOS 
(IDPROCESO,IDMODULO,TRAZA,TARGET,FECHAMODIFICACION,USUMODIFICACION,DESCRIPCION,TRANSACCION,IDPARENT,NIVEL) values 
('96M','JGR','1','Y',SYSDATE,'0','Inscripciones','JGR_Inscripciones','00S','10');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripciones','0','1',SYSDATE,'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripciones#GL','0','4',SYSDATE,'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripcions','0','2',SYSDATE,'0','22');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripciones#EU','0','3',SYSDATE,'0','22');

Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripciones','0','1',SYSDATE,'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripciones#GL','0','4',SYSDATE,'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripcions','0','2',SYSDATE,'0','22');
Insert into GEN_RECURSOS (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.oficio.inscripciones','Inscripciones#EU','0','3',SYSDATE,'0','2');

Insert into GEN_MENU(IDMENU,ORDEN,TAGWIDTH,IDPARENT,FECHAMODIFICACION,USUMODIFICACION,URI_IMAGEN,IDRECURSO,GEN_MENU_IDMENU,IDPROCESO,IDLENGUAJE,PATH,FECHA_BAJA,IDCLASS) values
('144','21101','160','700',SYSDATE,'0',null,'menu.justiciaGratuita.oficio.inscripciones',null,'96M','1','inscripciones',null,null); 

INSERT INTO ADM_TIPOSACCESO VALUES('96M','ADG',SYSDATE,'3','3','2005');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off