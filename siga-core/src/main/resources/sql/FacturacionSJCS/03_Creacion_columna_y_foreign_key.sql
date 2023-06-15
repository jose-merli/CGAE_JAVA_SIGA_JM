spool 03_Creacion_columna_y_foreign_key.log
prompt 03_Creacion_columna_y_foreign_key.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

--CREAMOS LA COLUMNA Y CREAMOS SU FOREIGN KEY
ALTER TABLE FCS_FACT_GRUPOFACT_HITO ADD IDPARTIDAPRESUPUESTARIA NUMBER(5,0);
ALTER TABLE FCS_FACT_GRUPOFACT_HITO ADD CONSTRAINT fk_partidaPresupuestaria_id FOREIGN KEY (IDPARTIDAPRESUPUESTARIA, IDINSTITUCION) REFERENCES SCS_PARTIDAPRESUPUESTARIA(IDPARTIDAPRESUPUESTARIA, IDINSTITUCION);

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
