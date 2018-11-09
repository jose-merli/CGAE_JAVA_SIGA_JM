Insert Into Scs_Retencionesirpf
  (Fechainicio, Fechamodificacion, Usumodificacion, Idinstitucion, Idpersona, Idretencion)
  (Select cli.Fechaalta, Sysdate, -1, noc.Idinstitucion, noc.Idpersona, ret.Idretencion
     From Cen_Cliente Cli, Cen_Nocolegiado Noc, Scs_Maestroretenciones Ret
    Where Cli.Idpersona = Noc.Idpersona
      And Cli.Idinstitucion = Noc.Idinstitucion
      And Noc.Tipo Not In ('0', '1', '2')
      And Noc.Tipo = Ret.Letranifsociedad);
