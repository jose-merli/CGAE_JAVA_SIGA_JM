
spool TRADUCCIONES COSTE FIJO MENSAJES BACK.log
prompt TRADUCCIONES COSTE FIJO MENSAJES BACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicas','Ya existe un coste fijo con las mismas características','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicas','Ja hi ha un cost fix amb les mateixes característiques','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicas','Ya existe un coste fijo con las mismas características#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicas','Ya existe un coste fijo con las mismas características#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicasDadoBaja','Ya existe un coste fijo con las mismas características dado de baja. Consultar el histórico.','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicasDadoBaja','Ja hi ha un cost fix amb les mateixes característiques donat de baixa. Consultar l''històric.','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicasDadoBaja','Ya existe un coste fijo con las mismas características dado de baja. Consultar el histórico.#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.existeCosteFijoMismasCaracteristicasDadoBaja','Ya existe un coste fijo con las mismas características dado de baja. Consultar el histórico.#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.constesFijosModificadosYaRegistrados','Uno o varios de los costes fijos modificados ya se encuentran registrados','0','1',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.constesFijosModificadosYaRegistrados','Un o diversos dels costos fixos modificats ja es troben registrats','0','2',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.constesFijosModificadosYaRegistrados','Uno o varios de los costes fijos modificados ya se encuentran registrados#GL','0','4',to_date('20/09/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.gestionCostesFijos.constesFijosModificadosYaRegistrados','Uno o varios de los costes fijos modificados ya se encuentran registrados#EU','0','3',to_date('20/09/19','DD/MM/RR'),'0','19');



commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
CostesFijosCosteFijoCaracterísticacoste fijo con las mismas características