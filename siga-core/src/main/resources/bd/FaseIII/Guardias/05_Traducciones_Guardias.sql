spool 05_Traducciones_Guardias.log
prompt 05_Traducciones_Guardias.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


	--  Apellidos y nombre ascendente
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.apellidosNombreAscendente','Apellidos y nombre ascendente','0','1',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.apellidosNombreAscendente','Cognoms i nom ascendent','0','2',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.apellidosNombreAscendente','Apellidos y nombre ascendente#GL','0','4',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.apellidosNombreAscendente','Apellidos y nombre ascendente#EU','0','3',to_date('10/12/19','DD/MM/RR'),'0','19');


	--  Mínimo de letrados por grupo
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.minimoLetradosGrupo','Mínimo de letrados por grupo','0','1',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.minimoLetradosGrupo','Mínim de lletrats per grup','0','2',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.minimoLetradosGrupo','Mínimo de letrados por grupo#GL','0','4',to_date('10/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.minimoLetradosGrupo','Mínimo de letrados por grupo#EU','0','3',to_date('10/12/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
