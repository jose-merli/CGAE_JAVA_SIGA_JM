alter table MOD_PLANTILLADOCUMENTO modify USUMODIFICACION NUMBER(5);


alter table MOD_MODELO_PLANTILLADOCUMENTO
  drop constraint PK_MODELOPLANTILLADOC cascade;

alter table MOD_MODELO_PLANTILLADOCUMENTO
  add constraint PK_MODELOPLANTILLADOC primary key (IDPLANTILLADOCUMENTO, IDMODELOCOMUNICACION)
  using index 
  tablespace TS_SIGA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
  
ALTER TABLE mod_modelo_plantilladocumento DROP COLUMN IDCONSULTA;


  create table MOD_PLANTILLADOC_FORMATO
(
  IDFORMATOSALIDA   number(2) not null,
  NOMBRE            varchar(30) not null,
  FECHAMODIFICACION date not null,
  USUMODIFICACION   number(5) not null
)
tablespace TS_SIGA
  storage
  (
    initial 128M
    next 1M
    minextents 1
    pctincrease 0
  );
alter table MOD_PLANTILLADOC_FORMATO
  add constraint PK_MOD_PLANTILLADOC_FORMATO primary key (IDFORMATOSALIDA);
  
create table MOD_PLANTILLADOC_SUFIJO
(
  IDSUFIJO   number(2) not null,
  NOMBRE            varchar(30) not null,
  FECHAMODIFICACION date not null,
  USUMODIFICACION   number(5) not null
)
tablespace TS_SIGA
  storage
  (
    initial 128M
    next 1M
    minextents 1
    pctincrease 0
  );
  
alter table MOD_PLANTILLADOC_SUFIJO
  add constraint PK_MOD_PLANTILLADOC_SUFIJO primary key (IDSUFIJO);
  
  
  
-- Add/modify columns 
alter table MOD_PLANTILLADOC_CONSULTA add IDPLANTILLACONSULTA number(10) not null;
-- Create/Recreate primary, unique and foreign key constraints 
alter table MOD_PLANTILLADOC_CONSULTA
  drop constraint PK_PLANTILLADOCCONSULTA cascade;
alter table MOD_PLANTILLADOC_CONSULTA
  add constraint PK_PLANTILLADOCCONSULTA primary key (IDPLANTILLACONSULTA)
  using index 
  tablespace TS_SIGA
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
