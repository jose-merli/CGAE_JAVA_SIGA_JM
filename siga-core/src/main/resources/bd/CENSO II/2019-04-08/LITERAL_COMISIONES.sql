Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.comisiones','Comisiones','0','1',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.comisiones','Comissions','0','2',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.comisiones','Comisiones#GL','0','4',to_date('25/03/19','DD/MM/RR'),'0','19');
Insert into GEN_DICCIONARIO (IDRECURSO,DESCRIPCION,ERROR,IDLENGUAJE,FECHAMODIFICACION,USUMODIFICACION,IDPROPIEDAD) values ('censo.consultaDatosGenerales.literal.comisiones','Comisiones#EU','0','3',to_date('25/03/19','DD/MM/RR'),'0','19');


Update Cen_Datoscolegialesestado Est

   Set Est.Situacionresidente = (Select Est2.Situacionresidente

                                   From Cen_Colegiado Est2

                                  Where Est.Idpersona = Est2.Idpersona

                                    And Est.Idinstitucion = Est2.Idinstitucion)

 Where Est.Fechaestado = (Select Max(Est2.Fechaestado)

                            From Cen_Datoscolegialesestado Est2

                           Where Est.Idpersona = Est2.Idpersona

                             And Est.Idinstitucion = Est2.Idinstitucion);