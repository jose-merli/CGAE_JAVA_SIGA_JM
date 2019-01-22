INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'cron.pattern.scheduled.forCurso', '0 0 1 ? * *', 'Periodo de ejecución del cron para la comprobacion de cursos', '0 0 1 ? * *', 1);

INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'url.rapis', 'https://mobile.redabogacia.org/rapis/rest/', 'URL Base para la llamada al servicio RAPIS', 'https://mobile.redabogacia.org/rapis/rest/', 0);

INSERT INTO ADM_CONFIG (ID, CLAVE, VALOR, DESCRIPCION, VALOR_POR_DEFECTO, NECESITA_REINICIO)
VALUES (SEQ_ADM_CONFIG.NEXTVAL, 'cron.pattern.scheduled.festivos', '0 0 0 9 JAN ?', 'Periodo de ejecución del cron para la insercion de festivos', '0 0 0 9 JAN ?', 1);
