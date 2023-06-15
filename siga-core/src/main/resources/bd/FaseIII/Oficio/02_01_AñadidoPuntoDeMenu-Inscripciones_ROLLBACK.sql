spool 01_AñadidoPuntoDeMenu-Inscripciones_ROLLBACK.log
prompt 01_AñadidoPuntoDeMenu-Inscripciones_ROLLBACK.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM gen_menu where idmenu = '144';

DELETE FROM ADM_TIPOSACCESO WHERE IDPROCESO = '96M';

DELETE FROM GEN_PROCESOS WHERE IDPROCESO = '96M';

DELETE FROM GEN_RECURSOS WHERE IDRECURSO = 'menu.justiciaGratuita.oficio.inscripciones';

DELETE FROM GEN_DICCIONARIO WHERE IDRECURSO ='menu.justiciaGratuita.oficio.inscripciones';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off