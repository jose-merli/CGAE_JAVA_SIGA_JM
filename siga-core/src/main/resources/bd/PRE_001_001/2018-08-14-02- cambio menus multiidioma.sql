-- Este script cambia los nombres de los men�s Multiidioma, eliminando el submen�
Update uscgae.gen_diccionario rec Set rec.Descripcion = 'Traducci�n Recursos' || Case rec.idlenguaje When '3' Then '#EU' When '4' Then '#GL' End Where rec.Idrecurso = 'menu.administracion.gestionMultiidioma.etiquetas' And rec.idlenguaje In (1,3,4);
Update uscgae.gen_diccionario rec Set rec.Descripcion = 'Traducci� Recursos' Where rec.Idrecurso = 'menu.administracion.gestionMultiidioma.etiquetas' And rec.idlenguaje = 2;
Update uscgae.gen_diccionario rec Set rec.Descripcion = 'Traducci�n Cat�logos' || Case rec.idlenguaje When '3' Then '#EU' When '4' Then '#GL' End Where rec.Idrecurso = 'menu.administracion.gestionMultiidioma.catalogosMaestros' And rec.idlenguaje In (1,3,4);
Update uscgae.gen_diccionario rec Set rec.Descripcion = 'Traducci� Cat�legs' Where rec.Idrecurso = 'menu.administracion.gestionMultiidioma.catalogosMaestros' And rec.idlenguaje = 2;
Update uscgae.gen_diccionario rec Set rec.Descripcion = 'Traducci�n Cat�logos' || Case rec.idlenguaje When '3' Then '#EU' When '4' Then '#GL' End Where rec.Idrecurso = 'menu.administracion.gestionMultiidioma.catalogos' And rec.idlenguaje In (1,3,4);
Update uscgae.gen_diccionario rec Set rec.Descripcion = 'Traducci� Cat�legs' Where rec.Idrecurso = 'menu.administracion.gestionMultiidioma.catalogos' And rec.idlenguaje = 2;
Update Gen_Menu men Set men.Fecha_Baja = Sysdate Where men.Idrecurso = 'menu.administracion.gestionMultiidioma';
Update gen_menu men Set men.Idparent = '90' Where men.idrecurso In ('menu.administracion.gestionMultiidioma.etiquetas', 'menu.administracion.gestionMultiidioma.catalogosMaestros');
