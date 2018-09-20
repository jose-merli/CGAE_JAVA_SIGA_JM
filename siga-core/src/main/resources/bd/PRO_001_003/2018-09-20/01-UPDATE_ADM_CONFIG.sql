spool 01-UPDATE_ADM_CONFIG.log
prompt 01-UPDATE_ADM_CONFIG.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .


update ADM_CONFIG set VALOR = '/oracle/certificados/siga/certificate-filter/access-control.xml' where clave = 'cert-conf-path' ;
update ADM_CONFIG set VALOR = '/applications/logs/siga-web/siga-web.log' where clave = 'log4j.siga.web.file' ;

prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off