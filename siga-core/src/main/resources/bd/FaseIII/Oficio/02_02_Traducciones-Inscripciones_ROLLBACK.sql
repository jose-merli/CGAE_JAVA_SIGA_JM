spool 02_Traducciones-Inscripciones_ROLLBACK.log
prompt 02_Traducciones-Inscripciones_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='menu.justiciaGratuita.oficio.comboturno';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='menu.justiciaGratuita.oficio.inscripcionesfechade';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off