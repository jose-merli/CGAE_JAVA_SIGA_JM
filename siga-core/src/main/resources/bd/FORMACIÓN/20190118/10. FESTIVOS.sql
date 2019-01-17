INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'cron.pattern.scheduled.forCurso', '0 0 1 ? * *', 'Periodo de ejecuciÛn del cron para la comprobacion de cursos', '0 0 1 ? * *', 1);

INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'url.rapis', 'https://mobile.redabogacia.org/rapis/rest/', 'URL Base para la llamada al servicio RAPIS', 'https://mobile.redabogacia.org/rapis/rest/', 0);

INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'cron.pattern.scheduled.festivos', '0 0 0 9 JAN ?', 'Periodo de ejecuciÛn del cron para la insercion de festivos', '0 0 0 9 JAN ?', 1);

ALTER TABLE CEN_POBLACIONES ADD SEDEJUDICIAL NUMERIC(2);

update  cen_poblaciones set sedejudicial = 1
where idpoblacion in (select distinct PO.IDPOBLACION from cen_partidojudicial ju
inner join  cen_poblaciones po  on (TRANSLATE(LOWER( ju.nombre ),'·ÈÌÛ˙¸Ò¡…Õ”⁄‹—','aeiouunAEIOUUN') = (TRANSLATE(LOWER( po.nombre ),'·ÈÌÛ˙¸Ò¡…Õ”⁄‹—','aeiouunAEIOUUN')) and  ju.IDPARTIDO = po.idpartido)
where idpoblacion = idpoblacionmunicipio 
); 

update cen_poblaciones set sedejudicial = 1 where idpoblacion = '15030000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '36017000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '27018000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '32063000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02003000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02008000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '03009000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '03014000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02009000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '36008000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02024000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '12040000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '39020000300' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08266000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08073000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '20069000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '04902000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08169000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '11027000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '43163000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08089000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02037000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '50025000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '24010000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '17022000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '23024000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '11022000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '38026000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '21054000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02069000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '25203000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '35016000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08101000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '38024000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '07032000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '36026000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08124000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '32009000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '32019050100' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '36039000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '15065000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '07040000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '17141000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '03122000500' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08200000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '08305000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '03139000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '02081000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '12138000000' ;
update cen_poblaciones set sedejudicial = 1 where idpoblacion = '46145000000' ;

UPDATE CEN_POBLACIONES SET IDPARTIDO = '201' WHERE IDPOBLACION = '02003000000';
UPDATE CEN_POBLACIONES SET IDPARTIDO = '202' WHERE IDPOBLACION = '02008000000';
UPDATE CEN_POBLACIONES SET IDPARTIDO = '203' WHERE IDPOBLACION = '02009000000';
UPDATE CEN_POBLACIONES SET IDPARTIDO = '207' WHERE IDPOBLACION = '02024000000';
UPDATE CEN_POBLACIONES SET IDPARTIDO = '204' WHERE IDPOBLACION = '02037000000';
UPDATE CEN_POBLACIONES SET IDPARTIDO = '205' WHERE IDPOBLACION = '02069000000';
UPDATE CEN_POBLACIONES SET IDPARTIDO = '206' WHERE IDPOBLACION = '02081000000';