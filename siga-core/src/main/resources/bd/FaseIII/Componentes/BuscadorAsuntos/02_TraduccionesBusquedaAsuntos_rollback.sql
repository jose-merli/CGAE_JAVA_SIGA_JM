spool Fase III Modulo Busqueda Asuntos Desarrollo.log
prompt Fase III Modulo Busqueda Asuntos Desarrollo.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE from GEN_DICCIONARIO where IDRECURSO = 'scs.busquedaasuntos.error.campofaperturahasta';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'scs.busquedaasuntos.error.campofaperturadesde';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'scs.busquedaasuntos.error.campoanio';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'scs.busquedaasuntos.error.camponumero';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'scs.busquedaasuntos.error.campondiligencia';
DELETE from GEN_DICCIONARIO where IDRECURSO = 'scs.busquedaasuntos.error.camponasunto';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
