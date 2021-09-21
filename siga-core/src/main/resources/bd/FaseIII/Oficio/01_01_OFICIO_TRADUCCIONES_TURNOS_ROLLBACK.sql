spool TRADUCCIONES TURNOS.log
prompt TRADUCCIONES TURNOS.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

DELETE FROM gen_diccionario where idrecurso = 'justiciaGratuita.oficio.turnos.numeroguardias';

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off