


update gen_tablas_maestras set flagborradologico = '1' where idtablamaestra in 
(
'SCS_DE_EJERCICIO',
'SCS_DE_ORIGENVAL_BI',
'SCS_DE_ORIGENVAL_BM',
'SCS_DE_PERIODICIDAD',
'SCS_PRESTACION',
'SCS_ORIGENCAJG',
'CEN_COMUNIDADESAUTONOMAS',
'ADM_LENGUAJES',
'CEN_PAIS'
);


COMMIT;



