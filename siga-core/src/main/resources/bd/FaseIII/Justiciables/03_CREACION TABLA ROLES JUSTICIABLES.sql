spool CREACION TABLA ROLES JUSTICIABLES.log
prompt CREACION TABLA ROLES JUSTICIABLES.log
select to_char(sysdate, 'hh24:mi:ss') as "Inicio" from dual;
prompt .

CREATE TABLE scs_rolesjusticiables  (
    idrol               NUMBER(10) NOT NULL,
    descripcion         VARCHAR2(200),
    usumodificacion     NUMBER(10),
    fechamodificacion   DATE,
    fechabaja           DATE
);

ALTER TABLE scs_rolesjusticiables ADD CONSTRAINT scs_rolesjusticiables_pk PRIMARY KEY ( idrol );

commit;
prompt .
select to_char(sysdate, 'hh24:mi:ss') as "Fin" from dual;
spool off
