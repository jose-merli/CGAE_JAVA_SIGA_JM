spool VALORES TABLA ROLES JUSTICIABLES.log
prompt VALORES TABLA ROLES JUSTICIABLES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

---------
---- ROLES JUSTICIABLES (COMBOS)
---------

--Solicitante
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.solicitante','Solicitante','0','1',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.solicitante','Sol·licitant','0','2',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.solicitante','Solicitante#EU','0','3',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.solicitante','Solicitante#GL','0','4',sysdate,'0','19');

Insert into SCS_ROLESJUSTICIABLES (IDROL,DESCRIPCION,USUMODIFICACION,FECHABAJA,FECHAMODIFICACION) values (1,'justiciaGratuita.justiciables.rol.solicitante','0',null,sysdate);

--Contrario
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.contrario','Contrario','0','1',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.contrario','Contrari','0','2',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.contrario','Contrario#EU','0','3',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.contrario','Contrario#GL','0','4',sysdate,'0','19');

Insert into SCS_ROLESJUSTICIABLES (IDROL,DESCRIPCION,USUMODIFICACION,FECHABAJA,FECHAMODIFICACION) values (2,'justiciaGratuita.justiciables.rol.contrario','0',null,sysdate);

--Representante 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.representante','Representante','0','1',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.representante','Representant','0','2',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.representante','Representante#EU','0','3',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.representante','Representante#GL','0','4',sysdate,'0','19');

Insert into SCS_ROLESJUSTICIABLES (IDROL,DESCRIPCION,USUMODIFICACION,FECHABAJA,FECHAMODIFICACION) values (3,'justiciaGratuita.justiciables.rol.representante','0',null,sysdate);

--Unidad Familiar 
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.unidadFamiliar','Unidad Familiar','0','1',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.unidadFamiliar','Unitat Familiar','0','2',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.unidadFamiliar','Unidad Familiar#EU','0','3',sysdate,'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('justiciaGratuita.justiciables.rol.unidadFamiliar','Unidad Familiar#GL','0','4',sysdate,'0','19');

Insert into SCS_ROLESJUSTICIABLES (IDROL,DESCRIPCION,USUMODIFICACION,FECHABAJA,FECHAMODIFICACION) values (4,'justiciaGratuita.justiciables.rol.unidadFamiliar','0',null,sysdate);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off