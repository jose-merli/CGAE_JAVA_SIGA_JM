spool 3.insercion_diccionario_bd.log
prompt 3.insercion_diccionario_bd.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt . 

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Prestaciones','Prestaciones','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Prestaciones','Prestacions','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Prestaciones','Prestaciones#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.ejg.datosGenerales.Prestaciones','Prestaciones#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.FechaLimPresentacion','Fecha L�mite Presentaci�n','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.FechaLimPresentacion','Data l�mit Presentaci�','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.FechaLimPresentacion','Fecha L�mite Presentaci�n#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.FechaLimPresentacion','Fecha L�mite Presentaci�n#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.DatosIdenEJG','Datos Identificativos del EJG','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.DatosIdenEJG','Dades Identificatives de l''EJG','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.DatosIdenEJG','Datos Identificativos del EJG#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.DatosIdenEJG','Datos Identificativos del EJG#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Otros Datos del EJG','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Altres Dades de l''EJG','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Otros Datos del EJG#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.OtrosDatosEJG','Otros Datos del EJG#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.DatosExpedienteIns','Datos del Expediente de Insostenibilidad','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.DatosExpedienteIns','Dades de l''Expedient de Insostenibilitat','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.DatosExpedienteIns','Datos del Expediente de Insostenibilidad#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.DatosExpedienteIns','Datos del Expediente de Insostenibilidad#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');


Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.NumExpediente','N�mero de Expediente','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.NumExpediente','Nombre d''Expedient','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.NumExpediente','N�mero de Expediente#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.NumExpediente','N�mero de Expediente#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.ServiciosTramit','Servicios de Tramitaci�n','0','1',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.ServiciosTramit','Servicios de Tramitaci�n','0','2',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values
('justiciaGratuita.ejg.datosGenerales.ServiciosTramit','Servicios de Tramitaci�n#GL','0','4',to_date('16/12/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values 
('justiciaGratuita.ejg.datosGenerales.ServiciosTramit','Servicios de Tramitaci�n#EU','0','3',to_date('16/12/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off 