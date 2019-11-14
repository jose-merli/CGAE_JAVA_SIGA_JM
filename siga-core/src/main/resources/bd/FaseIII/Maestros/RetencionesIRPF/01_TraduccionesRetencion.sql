spool 01_TraduccionesRetencion.log
prompt 01_TraduccionesRetencion.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.tipoSociedad','Tipo Sociedad','0','1',to_date('10/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.tipoSociedad','Tipus de societat','0','2',to_date('10/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.tipoSociedad','Tipo Sociedad#GL','0','4',to_date('10/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.tipoSociedad','Tipo Sociedad#EU','0','3',to_date('10/10/19','DD/MM/RR'),'0','19');



Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.claveModelo','Clave Modelo 190','0','1',to_date('11/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.claveModelo','Clau Model 190','0','2',to_date('11/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.claveModelo','Clave Modelo 190#GL','0','4',to_date('11/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('dato.jgr.maestros.documentacionIRPF.claveModelo','Clave Modelo 190#EU','0','3',to_date('11/10/19','DD/MM/RR'),'0','19');



Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.existeRetencionMismoNombre','Ya existe una retención con ese nombre o sociedad','0','1',to_date('14/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.existeRetencionMismoNombre','Ja hi ha una retenció amb aquest nom o societat','0','2',to_date('14/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.existeRetencionMismoNombre','Ya existe una retención con ese nombre o sociedad#GL','0','4',to_date('14/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.existeRetencionMismoNombre','Ya existe una retención con ese nombre o sociedad#EU','0','3',to_date('14/10/19','DD/MM/RR'),'0','19');



Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.retencionNula','Debe introducir una retención','0','1',to_date('14/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.retencionNula','Podeu entrar una retenció','0','2',to_date('14/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.retencionNula','Debe introducir una retención#GL','0','4',to_date('14/10/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('messages.jgr.maestros.documentacionIRPF.retencionNula','Debe introducir una retención#EU','0','3',to_date('14/10/19','DD/MM/RR'),'0','19');


commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
