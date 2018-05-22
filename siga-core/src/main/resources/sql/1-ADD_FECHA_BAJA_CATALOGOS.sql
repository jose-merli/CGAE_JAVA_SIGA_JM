
ALTER TABLE ADM_LENGUAJES ADD FECHA_BAJA DATE NULL;
ALTER TABLE ADM_ROL ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_ACTIVIDADPROFESIONAL ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_CARGO ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_COMUNIDADESAUTONOMAS ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_DOCUMENTACIONMODALIDAD ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_DOCUMENTACIONSOLICITUD ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_ESTADOCIVIL ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_PAIS ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_TIPOCOLEGIO ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_TIPOSANCION ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_TIPOSCV ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_TIPOSSEGURO ADD FECHA_BAJA DATE NULL ;
ALTER TABLE CEN_TIPOVIA ADD FECHA_BAJA DATE NULL;
ALTER TABLE CER_MOTIVOANULACION ADD FECHA_BAJA DATE NULL;
ALTER TABLE CER_MOTIVOSOLICITUD ADD FECHA_BAJA DATE NULL;
ALTER TABLE EXP_TIPORESULTADORESOLUCION ADD FECHA_BAJA DATE NULL;
ALTER TABLE PYS_METODOSOLICITUD ADD FECHA_BAJA DATE NULL;
ALTER TABLE PYS_TIPOSERVICIOS ADD FECHA_BAJA DATE NULL ;
ALTER TABLE PYS_TIPOSPRODUCTOS ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_COSTEFIJO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DELITO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_EJERCICIO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_ORIGENVAL_BI ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_ORIGENVAL_BM ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_PERIODICIDAD ADD FECHA_BAJA DATE NULL ;
ALTER TABLE SCS_DE_TIPOCARGAECONOMICA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_TIPOINGRESO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_TIPOMUEBLE ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_TIPORENDIMIENTO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_DE_TIPOVIVIENDA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_ESTADOASISTENCIA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_GRUPOFACTURACION ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_JURISDICCION ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_MAESTROESTADOSEJG ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_MINUSVALIA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_ORIGENCAJG ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_ORIGENCONTACTO ADD FECHA_BAJA DATE NULL ;
ALTER TABLE SCS_PARENTESCO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_PONENTE ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_PRECEPTIVO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_PRESENTADOR ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_PRESTACION ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_PRETENSION ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_PROFESION ADD FECHA_BAJA DATE NULL; 
ALTER TABLE SCS_RENUNCIA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_SITUACION ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOASISTENCIA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOASISTENCIACOLEGIO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOCONOCE ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOCONSULTA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPODELITO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPODESIGNACOLEGIO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPODICTAMENEJG ADD FECHA_BAJA DATE NULL ;
ALTER TABLE SCS_TIPODOCUMENTODES ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOEJG ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOEJGCOLEGIO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOENCALIDAD ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOFUNDAMENTOCALIF ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOFUNDAMENTOS ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOGRUPOLABORAL ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOINGRESO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOMOTIVO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPORESOLAUTO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPORESOLUCION ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPORESPUESTA ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOSENTIDOAUTO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOSGUARDIAS ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOSOJ ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOSOJCOLEGIO ADD FECHA_BAJA DATE NULL;
ALTER TABLE SCS_TIPOTURNO ADD FECHA_BAJA DATE NULL;
ALTER TABLE ADM_USUARIOS ADD FECHA_BAJA DATE NULL; -- GESTION DE USUARIOS
ALTER TABLE GEN_PARAMETROS ADD FECHA_BAJA DATE NULL; --  GESTION DE PARAMETROS
ALTER TABLE ADM_PERFIL ADD FECHA_BAJA DATE NULL; --  GESTION DE GRUPOS DE USUARIOS
ALTER TABLE GEN_MENU ADD FECHA_BAJA DATE NULL;
ALTER TABLE CEN_TIPOSOCIEDAD ADD FECHA_BAJA DATE NULL; -- BUSQUEDA DE PERSONA JURIDICA
ALTER TABLE CEN_TIPOSOCIEDAD ADD SOCIEDAD_PROFESIONAL VARCHAR2(1 BYTE) NULL;  -- BUSQUEDA DE PERSONA JURIDICA
ALTER TABLE CEN_NOCOLEGIADO ADD FECHA_BAJA DATE NULL; -- BUSQUEDA DE PERSONA JURIDICA
ALTER TABLE CEN_GRUPOSCLIENTE_CLIENTE ADD FECHA_BAJA DATE NULL; -- BUSQUEDA EN PERSONA JURIDICA 
ALTER TABLE CEN_GRUPOSCLIENTE_CLIENTE ADD FECHA_INICIO DATE; -- BUSQUEDA EN PERSONA JURIDICA
UPDATE CEN_GRUPOSCLIENTE_CLIENTE SET FECHA_INICIO = to_date('01/01/2018','DD/MM/RR'); -- BUSQUEDA EN PERSONA JURIDICA

COMMIT;