spool 05_Traducciones_Guardias_rollback.log
prompt 05_Traducciones_Guardias_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .



DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.apellidosNombreAscendente';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.minimoLetradosGrupo';
commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
