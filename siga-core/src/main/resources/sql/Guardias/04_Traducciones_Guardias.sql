spool 04_Traducciones_Guardias.log
prompt 04_Traducciones_Guardias.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


	--  Motivos
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.motivos','Motivos','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.motivos','Motius','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.motivos','Motivos#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.motivos','Motivos#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');


	--  D�as
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.dias','D�as','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.dias','Dies','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.dias','D�as#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.dias','D�as#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');


	--  D�as de Separaci�n
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.diasSeparacion','D�as de separaci�n','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.diasSeparacion','Dies Separaci�','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.diasSeparacion','D�as de separaci�n#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.diasSeparacion','D�as de separaci�n#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');



	--  Incompatibilitats
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.incompatibilidades','Incompatibilidades','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.incompatibilidades','Incompatibilitats','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.incompatibilidades','Incompatibilidades#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.incompatibilidades','Incompatibilidades#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');



commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
