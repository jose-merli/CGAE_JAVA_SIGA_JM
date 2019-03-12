------------------MIGRAR INFORMES A MODELOS DE COMUNICACION----------------------------
SET AUTOPRINT ON;
SET SERVEROUTPUT ON;

DECLARE

mod_visible NUMBER(1):=0;
idInstitucion NUMBER(4):=0;
idclasecomunicacion NUMBER(4):=0;
idModelo NUMBER(4):=0;
idPlantillaDocumento NUMBER(4):=0;
sufijoIdioma varchar2(20):= '';
idIdioma NUMBER(1):= 0;
formatoSalida varchar2(2) := '';
contador NUMBER(4) := 0;


CURSOR cursorInformes IS
                    select *
                    from ADM_INFORME
                    where idtipoinforme in ('CENSO', 'SANC', 'ASEPA', 'OSEPA');
        
BEGIN
    --DBMS_OUTPUT.ENABLE;
    DBMS_OUTPUT.ENABLE(1000000);
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo empieza: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    
     FOR informe IN cursorInformes
     LOOP
        
        IF informe.visible = 'S' THEN mod_visible := 1;
        ELSE mod_visible:=0;
        END IF;
        IF informe.idinstitucion = 0 THEN idInstitucion:= 2000;
        ELSE idInstitucion := informe.idinstitucion;
        END IF;
        IF informe.IDTIPOINFORME = 'CENSO' THEN idclasecomunicacion := 1;
        ELSIF informe.IDTIPOINFORME = 'SANC' THEN idclasecomunicacion := 2;
        ELSIF informe.IDTIPOINFORME = 'OSEPA' THEN idclasecomunicacion := 3;
        ELSIF informe.IDTIPOINFORME = 'ASEPA' THEN idclasecomunicacion := 4;
        END IF;
        
        --INSERTAMOS EN MOD_MODELOCOMUNICACION LOS DATOS DE ADM_INFORME
        idModelo:= SEQ_MOD_MODELOCOMUNICACION.nextval;
        INSERT INTO MOD_MODELOCOMUNICACION (IDMODELOCOMUNICACION,NOMBRE,ORDEN,IDINSTITUCION,DESCRIPCION,PRESELECCIONAR,IDCLASECOMUNICACION,FECHABAJA,FECHAMODIFICACION,USUMODIFICACION,VISIBLE,PORDEFECTO)
        VALUES(idModelo, informe.nombrefisico, informe.orden, idInstitucion, informe.descripcion, 'SI', idclasecomunicacion, null, sysdate, 0, mod_visible, null);
        
        --INSERTAMOS EN MOD_PLANTILLADOCUMENTO LOS DATOS DEL INFORME ADM_INFORME Y MOD_MODELOCOMUNCACION
        idPlantillaDocumento := SEQ_MOD_PLANTILLADOCUMENTO.nextval;
        
        IF idInstitucion = 2026 OR idInstitucion = 2030 OR idInstitucion = 2041 OR idInstitucion = 2047 OR idInstitucion = 2048 OR idInstitucion = 2057 OR idInstitucion = 2059
        OR idInstitucion = 2061 OR idInstitucion = 2072 OR idInstitucion = 2075 OR idInstitucion = 2079 OR idInstitucion = 3001 THEN sufijoIdioma := '_CA'; idIdioma := 2;
        ELSE sufijoIdioma := '_ES'; idIdioma := 1;
        END IF;
        
        INSERT INTO MOD_PLANTILLADOCUMENTO (FECHAMODIFICACION, IDIOMA, IDPLANTILLADOCUMENTO, PLANTILLA, USUMODIFICACION) 
        VALUES (SYSDATE, idIdioma, idPlantillaDocumento, informe.NOMBREFISICO || sufijoIdioma, 0);
        
        IF informe.TIPOFORMATO = 'W' THEN formatoSalida:='2';
        ELSIF informe.TIPOFORMATO = 'P' THEN formatoSalida := '3';
        ELSIF informe.TIPOFORMATO = 'E' THEN formatoSalida := '1';
        END IF;
        
        INSERT INTO MOD_MODELO_PLANTILLADOCUMENTO (IDPLANTILLADOCUMENTO, IDMODELOCOMUNICACION, IDINFORME, NOMBREFICHEROSALIDA, FORMATOSALIDA)
        VALUES (idPlantillaDocumento, idModelo, 1, informe.NOMBRESALIDA, formatoSalida);
        contador := contador +1;
    
     END LOOP;
     
    COMMIT;
    DBMS_OUTPUT.PUT_LINE('Script informe --> modelo acaba: '|| to_char(sysdate, 'DD-MM-YYYY HH24:MI:SS'));
    DBMS_OUTPUT.PUT_LINE(' Se han actualizado ' || contador || ' filas.');
END;