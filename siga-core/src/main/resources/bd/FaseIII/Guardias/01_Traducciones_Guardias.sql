spool 01_Traducciones_Guardias.log
prompt 01_Traducciones_Guardias.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


	 --Rotar Componentes automaticamente
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.rotarComponentesAuto','Rotar Componentes Automáticamente','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.rotarComponentesAuto','Rotar Components Automàticament','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.rotarComponentesAuto','Rotar Componentes Automáticamente#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.rotarComponentesAuto','Rotar Componentes Automáticamente#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');

 	--Por grupos
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.porGrupos','Por Grupos','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.porGrupos','Per Grups','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.porGrupos','Por Grupos#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.porGrupos','Por Grupos#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');

	-- Configuración cola
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.configuracionCola','Configuración de la Cola','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.configuracionCola','Configuració de la Cua','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.configuracionCola','Configuración de la Cola#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.configuracionCola','Configuración de la Cola#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');




commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
