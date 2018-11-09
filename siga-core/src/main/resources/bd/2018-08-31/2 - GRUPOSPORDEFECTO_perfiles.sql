update adm_perfil_rol set grupopordefecto = 'S' where grupopordefecto is null or GRUPOPORDEFECTO = 'N';

commit;