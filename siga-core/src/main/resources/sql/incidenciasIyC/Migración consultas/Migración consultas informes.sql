SET AUTOPRINT ON;
SET SERVEROUTPUT ON;

DECLARE

mod_visible NUMBER(1):=0;
idclasecomunicacion NUMBER(4):=5;
idInstitucion NUMBER(4):=0;
region VARCHAR2(100) := '';
extension varchar2(4) := '.doc';
preseleccionado varchar2(2) := '';
idModelo NUMBER(4):=0;
idPlantillaDocumento NUMBER(4):=0;
sufijoIdioma varchar2(20):= '';
idIdioma NUMBER(1):= 0;
formatoSalida varchar2(2) := '';
contador NUMBER(4) := 0;
v_errm  VARCHAR2(64);
idPlantillaConsulta NUMBER(4):=0;

CURSOR cursorInformes IS
            Select adm.nombre, adm.variaslineas, con.descripcion, admInforme.orden, admInforme.nombrefisico, admInforme.tipoformato, admInforme.nombresalida, adm.idconsulta, adm.idinstitucion_consulta, adm.idinstitucion, admInforme.preseleccionado, admInforme.visible From Adm_Consultainforme adm, Con_Consulta con, Adm_informe admInforme
            Where adm.Idinstitucion_Consulta = con.Idinstitucion
            And adm.Idconsulta = con.Idconsulta
            And con.Tipoconsulta <> 'I'
            and admInforme.idplantilla = adm.idplantilla and admInforme.idinstitucion = adm.idinstitucion;
            
BEGIN
    --DBMS_OUTPUT.ENABLE;
    DBMS_OUTPUT.ENABLE(1000000);
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo empieza: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    
     FOR informe IN cursorInformes
     LOOP
        
        IF informe.visible = 'S' THEN mod_visible := 1;
        ELSE mod_visible:=0;
        END IF;
        IF informe.variaslineas = 1 THEN region := informe.NOMBRE;
        ELSE region:=null;
        END IF;
        IF informe.idinstitucion = 0 THEN idInstitucion:= 2000;
        ELSE idInstitucion := informe.idinstitucion;
        END IF; 
        IF informe.preseleccionado = 'S' THEN preseleccionado:= 'SI';
        ELSE preseleccionado := 'NO';
        END IF; 
        
        --INSERTAMOS EN MOD_MODELOCOMUNICACION LOS DATOS DE ADM_INFORME
        idModelo:= SEQ_MOD_MODELOCOMUNICACION.nextval;
        INSERT INTO MOD_MODELOCOMUNICACION (IDMODELOCOMUNICACION,NOMBRE,ORDEN,IDINSTITUCION,DESCRIPCION,PRESELECCIONAR,IDCLASECOMUNICACION,FECHABAJA,FECHAMODIFICACION,USUMODIFICACION,VISIBLE,PORDEFECTO)
        VALUES(idModelo, informe.descripcion, informe.orden, informe.idinstitucion, informe.descripcion, preseleccionado, idclasecomunicacion, null, sysdate, 0, mod_visible, null);
        
        --INSERTAMOS EN MOD_PLANTILLADOCUMENTO LOS DATOS DEL INFORME ADM_INFORME Y MOD_MODELOCOMUNCACION
        idPlantillaDocumento := SEQ_MOD_PLANTILLADOCUMENTO.nextval;
        
        IF idInstitucion = 2026 OR idInstitucion = 2030 OR idInstitucion = 2041 OR idInstitucion = 2047 OR idInstitucion = 2048 OR idInstitucion = 2057 OR idInstitucion = 2059
        OR idInstitucion = 2061 OR idInstitucion = 2072 OR idInstitucion = 2075 OR idInstitucion = 2079 OR idInstitucion = 3001 THEN sufijoIdioma := '_CA'; idIdioma := 2;
        ELSE sufijoIdioma := '_ES'; idIdioma := 1;
        END IF;
        
        INSERT INTO MOD_PLANTILLADOCUMENTO (FECHAMODIFICACION, IDIOMA, IDPLANTILLADOCUMENTO, PLANTILLA, USUMODIFICACION) 
        VALUES (SYSDATE, idIdioma, idPlantillaDocumento, informe.NOMBREFISICO || sufijoIdioma || extension, 0);
        
        IF informe.TIPOFORMATO = 'W' THEN formatoSalida:='2';
        ELSIF informe.TIPOFORMATO = 'P' THEN formatoSalida := '3';
        ELSIF informe.TIPOFORMATO = 'E' THEN formatoSalida := '1';
        END IF;
        
        INSERT INTO MOD_MODELO_PLANTILLADOCUMENTO (IDPLANTILLADOCUMENTO, IDMODELOCOMUNICACION, IDINFORME, NOMBREFICHEROSALIDA, FORMATOSALIDA)
        VALUES (idPlantillaDocumento, idModelo, 1, informe.NOMBRESALIDA, formatoSalida);
        
        
        idPlantillaConsulta := SEQ_MOD_PLANTILLADOCCONSULTA.nextval;
        INSERT INTO MOD_PLANTILLADOC_CONSULTA (FECHAMODIFICACION,USUMODIFICACION,IDCONSULTA,IDPLANTILLADOCUMENTO,IDMODELOCOMUNICACION,IDINSTITUCION,FECHABAJA,IDPLANTILLACONSULTA,REGION,IDINSTITUCION_CONSULTA)
        VALUES (SYSDATE, 0, informe.idconsulta, idplantillaDocumento, idModelo, idInstitucion, null, idPlantillaConsulta, region, informe.idinstitucion_consulta);        
        
        contador := contador +1;
    
     END LOOP;
     
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo acaba: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE(' Se han actualizado ' || contador || ' filas.');
    EXCEPTION
    WHEN OTHERS THEN  
    v_errm := SUBSTR(SQLERRM, 1, 64);
    DBMS_OUTPUT.PUT_LINE('Error ' || v_errm);

END;