spool Fase III Modulo Busqueda Asuntos Desarrollo.log
prompt Fase III Modulo Busqueda Asuntos Desarrollo.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturahasta','Al informar la fecha apertura desde, debe informar la fecha apertura hasta para realizar la b�squeda','0','1',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturahasta','A l''informar la data obertura des, ha d''informar la data obertura fins per fer la cerca','0','2',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturahasta','Al informar la fecha apertura desde, debe informar la fecha apertura hasta para realizar la b�squeda#EU','0','3',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturahasta','Al informar la fecha apertura desde, debe informar la fecha apertura hasta para realizar la b�squeda#GL','0','4',to_date('01/02/06','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturadesde','Al informar la fecha apertura hasta, debe informar la fecha apertura desde para realizar la b�squeda','0','1',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturadesde','A l''informar la data obertura fins, ha d''informar la data obertura des per fer la cerca','0','2',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturadesde','Al informar la fecha apertura hasta, debe informar la fecha apertura desde para realizar la b�squeda#EU','0','3',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campofaperturadesde','Al informar la fecha apertura hasta, debe informar la fecha apertura desde para realizar la b�squeda#GL','0','4',to_date('01/02/06','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campoanio','Al informar el n�mero, debe informar el a�o para realizar la b�squeda','0','1',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campoanio','A l''informar el nombre, ha d''informar l''any per fer la cerca','0','2',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campoanio','Al informar el n�mero, debe informar el a�o para realizar la b�squeda#EU','0','3',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campoanio','Al informar el n�mero, debe informar el a�o para realizar la b�squeda#GL','0','4',to_date('01/02/06','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponumero','Al informar el a�o, debe informar el n�mero para realizar la b�squeda','0','1',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponumero','A l''informar l''any, ha d''informar el n�mero per fer la cerca','0','2',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponumero','Al informar el a�o, debe informar el n�mero para realizar la b�squeda#EU','0','3',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponumero','Al informar el a�o, debe informar el n�mero para realizar la b�squeda#GL','0','4',to_date('01/02/06','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campondiligencia','Al informar el n�mero de asunto, debe informar el n�mero de diligencia para realizar la b�squeda','0','1',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campondiligencia','A l''informar el nombre d''assumpte, ha d''informar l''nombre de dilig�ncia per fer la cerca','0','2',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campondiligencia','Al informar el n�mero de asunto, debe informar el n�mero de diligencia para realizar la b�squeda#EU','0','3',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.campondiligencia','Al informar el n�mero de asunto, debe informar el n�mero de diligencia para realizar la b�squeda#GL','0','4',to_date('01/02/06','DD/MM/RR'),'0','15');

Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponasunto','Al informar el n�mero de diligencia, debe informar el n�mero de asunto para realizar la b�squeda','0','1',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponasunto','A l''informar l''nombre de dilig�ncia, ha d''informar el n�mero d''assumpte per fer la cerca','0','2',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponasunto','Al informar el n�mero de diligencia, debe informar el n�mero de asunto para realizar la b�squeda#EU','0','3',to_date('01/02/06','DD/MM/RR'),'0','15');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('scs.busquedaasuntos.error.camponasunto','Al informar el n�mero de diligencia, debe informar el n�mero de asunto para realizar la b�squeda#GL','0','4',to_date('01/02/06','DD/MM/RR'),'0','15');

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
