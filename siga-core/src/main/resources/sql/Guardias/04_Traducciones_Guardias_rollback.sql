spool 04_Traducciones_Guardias_rollback.log
prompt 04_Traducciones_Guardias_rollback.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.motivos';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.dias';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.diasSeparacion';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO = 'dato.jgr.guardia.guardias.incompatibilidades';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
