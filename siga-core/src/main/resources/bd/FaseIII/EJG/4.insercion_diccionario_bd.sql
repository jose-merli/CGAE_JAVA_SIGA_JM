spool 4.insercion_diccionario_bd.log
prompt 4.insercion_diccionario_bd.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedientesEconomicos','Expedientes Econ�micos','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedientesEconomicos','Expedients Econ�mics','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedientesEconomicos','Expedientes Econ�micos#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedientesEconomicos','Expedientes Econ�micos#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Expedientes','N�mero total de Expedientes','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Expedientes','Nombre total d''Expedients','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Expedientes','N�mero total de Expedientes#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Expedientes','N�mero total de Expedientes#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.justiciable','Justiciable','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.justiciable','Justiciable','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.justiciable','Justiciable#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('menu.justiciaGratuita.justiciable','Justiciable#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.SolicitadoPor','Solicitado por','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.SolicitadoPor','Sol�licitat per','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.SolicitadoPor','Solicitado por#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.SolicitadoPor','Solicitado por#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.FechaRecepcion','Fecha Recepci�n','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.FechaRecepcion','Data Recepci�','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.FechaRecepcion','Fecha Recepci�n#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.FechaRecepcion','Fecha Recepci�n#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Relaciones','Relaciones','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Relaciones','Relacions','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Relaciones','Relaciones#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Relaciones','Relaciones#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Relaciones','N�mero total de Relaciones','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Relaciones','Nombre total d''Relacions','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Relaciones','N�mero total de Relaciones#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.numero.Relaciones','N�mero total de Relaciones#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.literal.interesado','Interessat','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.literal.interesado','Interesado','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.literal.interesado','Interesado#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.literal.interesado','Interesado#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.annioNum','A�o/N�mero','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.annioNum','Any/Nombre','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.annioNum','A�o/N�mero#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.annioNum','A�o/N�mero#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Otros Datos del EJG','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Altres Dades de l''EJG','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Otros Datos del EJG#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Otros Datos del EJG#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DatosExpediente','Datos del Expediente de Insostenbilidad','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DatosExpediente','Dades de l''Expedient de Insostenbilidad','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DatosExpediente','Datos del Expediente de Insostenbilidad#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.DatosExpediente','Datos del Expediente de Insostenbilidad#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.RelacionadoCon','Relacionado con','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.RelacionadoCon','Relacionat amb','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.RelacionadoCon','Relacionado con#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.RelacionadoCon','Relacionado con#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedienteEcon','Expediente Econ�mico','0','1',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedienteEcon','Expedient Econ�mic','0','2',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedienteEcon','Expediente Econ�mico#GL','0','4',to_date('07/01/20','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.ExpedienteEcon','Expediente Econ�mico#EU','0','3',to_date('07/01/20','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 