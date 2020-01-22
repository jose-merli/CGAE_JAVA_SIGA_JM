spool 02_Traducciones_Guardias.log
prompt 02_Traducciones_Guardias.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


	 -- La guardia se ha creado correctamente. Se han establecido valores predeterminados, si desea editarlos acceda a la ficha correspondiente.
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.guardiaCreadaDatosPred','La guardia se ha creado correctamente. Se han establecido valores predeterminados, si desea editarlos acceda a la ficha correspondiente','0','1',to_date('02/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.guardiaCreadaDatosPred','La guàrdia s''ha creat correctament. S''han establert valors predeterminats, si desitja editar-accedeixi a la fitxa corresponent','0','2',to_date('02/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.guardiaCreadaDatosPred','La guardia se ha creado correctamente. Se han establecido valores predeterminados, si desea editarlos acceda a la ficha correspondiente#GL','0','4',to_date('02/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.guardia.gestion.guardiaCreadaDatosPred','La guardia se ha creado correctamente. Se han establecido valores predeterminados, si desea editarlos acceda a la ficha correspondiente#EU','0','3',to_date('02/1/19','DD/MM/RR'),'0','19');

	--  Letrados Guardia
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.letradosGuardia','Letrados Guardia','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.letradosGuardia','Lletrats Guàrdia','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.letradosGuardia','Letrados Guardia#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.letradosGuardia','Letrados Guardia#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');


	--  Grupo
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.grupo','Grupo','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.grupo','Grup','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.grupo','Grupo#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.grupo','Grupo#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');

	--  Fecha Validez
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaValidez','Fecha Validez','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaValidez','Data Validesa','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaValidez','Fecha Validez#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaValidez','Fecha Validez#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');

	--  Fecha Baja
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaBaja','Fecha Baja','0','1',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaBaja','Data Baixa','0','2',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaBaja','Fecha Baja#GL','0','4',to_date('20/11/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fechaBaja','Fecha Baja#EU','0','3',to_date('20/11/19','DD/MM/RR'),'0','19');


	--  Baremos
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.baremos','Baremos','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.baremos','Barems','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.baremos','Baremos#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.baremos','Baremos#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');

	-- Última fecha desde
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaDesde','Última fecha desde','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaDesde','Darrera data des','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaDesde','Última fecha desde#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaDesde','Última fecha desde#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');

	-- Última fecha hasta
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaHasta','Última fecha hasta','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaHasta','Darrera data fins','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaHasta','Última fecha hasta#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.ultimaFechaHasta','Última fecha hasta#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');

	-- Generado
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.generado','Generado','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.generado','Generat','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.generado','Generado#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.generado','Generado#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');
	
	-- Ficha Programación
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fichaProgramacion','Ficha Programación','0','1',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fichaProgramacion','Fitxa Programació','0','2',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fichaProgramacion','Ficha Programación#GL','0','4',to_date('04/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.guardia.guardias.fichaProgramacion','Ficha Programación#EU','0','3',to_date('04/12/19','DD/MM/RR'),'0','19');
commit;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
