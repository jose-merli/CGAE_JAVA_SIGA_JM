package org.itcgae.siga.adm.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.itcgae.siga.DTOs.adm.CreateResponseDTO;
import org.itcgae.siga.DTOs.adm.DeleteResponseDTO;
import org.itcgae.siga.DTOs.adm.UpdateResponseDTO;
import org.itcgae.siga.DTOs.adm.UsuarioCreateDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDTO;
import org.itcgae.siga.DTOs.adm.UsuarioDeleteDTO;
import org.itcgae.siga.DTOs.adm.UsuarioItem;
import org.itcgae.siga.DTOs.adm.UsuarioRequestDTO;
import org.itcgae.siga.DTOs.adm.UsuarioUpdateDTO;
import org.itcgae.siga.DTOs.gen.ComboDTO;
import org.itcgae.siga.DTOs.gen.ComboItem;
import org.itcgae.siga.adm.service.IGestionUsuariosGruposService;
import org.itcgae.siga.db.entities.AdmPerfil;
import org.itcgae.siga.db.entities.AdmPerfilExample;
import org.itcgae.siga.db.entities.AdmRol;
import org.itcgae.siga.db.entities.AdmRolExample;
import org.itcgae.siga.db.entities.AdmUsuarios;
import org.itcgae.siga.db.entities.AdmUsuariosExample;
import org.itcgae.siga.db.mappers.AdmRolMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmPerfilExtendsMapper;
import org.itcgae.siga.db.services.adm.mappers.AdmUsuariosExtendsMapper;
import org.itcgae.siga.security.UserAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionUsuariosGruposServiceImpl implements IGestionUsuariosGruposService {

	@Autowired
	private AdmRolMapper admRolMapper;

	@Autowired
	private AdmPerfilExtendsMapper admPerfilExtendsMapper;

	@Autowired
	private AdmUsuariosExtendsMapper admUsuariosExtendsMapper;

	/***
	 * Get the different users roles Return ComboDTO (id, value) where id is the
	 * identificator rol and value is the description of these rol.
	 */
	@Override
	public ComboDTO getUsersRole() {
		AdmRolExample example = new AdmRolExample();
		List<AdmRol> roles = new ArrayList<AdmRol>();
		ComboDTO comboDto = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		String unblocked = "N";

		example.createCriteria().andBloqueadoEqualTo(String.valueOf(unblocked));
		example.setOrderByClause("DESCRIPCION ASC");
		roles = admRolMapper.selectByExample(example);

		if (roles != null && roles.size() > 0) {
			ComboItem comboItem = new ComboItem();
			comboItem.setValue("");
			comboItem.setLabel("");
			comboItems.add(comboItem);
			for (AdmRol admRol : roles) {
				comboItem = new ComboItem();
				comboItem.setValue(admRol.getIdrol());
				comboItem.setLabel(admRol.getDescripcion());
				comboItems.add(comboItem);
			}
		}

		comboDto.setCombooItems(comboItems);
		return comboDto;
	}

	/***
	 * Get the different users profiles. Return object ComboDto (id,
	 * description) sorted by description(natural String order) and where each
	 * element id is unique for a description
	 */
	@Override
	public ComboDTO getUsersProfile() {
		AdmPerfilExample example = new AdmPerfilExample();
		HashMap<String, String> hashProfiles = new HashMap<String, String>();
		List<AdmPerfil> profiles = new ArrayList<AdmPerfil>();
		ComboDTO comboDto = new ComboDTO();
		List<ComboItem> comboItems = new ArrayList<ComboItem>();

		example.setOrderByClause("DESCRIPCION ASC");
		example.setDistinct(true);
		profiles = admPerfilExtendsMapper.selectComboPerfilDistinctByExample(example);

		if (profiles != null && profiles.size() > 0) {
			for (AdmPerfil admPerfil : profiles) {
				hashProfiles.put(admPerfil.getIdperfil(), admPerfil.getDescripcion());

			}

			ComboItem comboItem = new ComboItem();
			comboItem.setValue("");
			comboItem.setLabel("");
			comboItems.add(comboItem);
			hashProfiles.forEach((id, description) -> {
				ComboItem comboItem2 = new ComboItem();
				comboItem2.setValue(id);
				comboItem2.setLabel(description);
				comboItems.add(comboItem2);
			});

			// ordenar por descripcion
			Comparator<ComboItem> orderBydDescription = new Comparator<ComboItem>() {
				public int compare(ComboItem combo1, ComboItem combo2) {
					return String.valueOf(combo1.getValue()).compareTo(String.valueOf(combo2.getValue()));
				}
			};

			comboItems.sort(orderBydDescription);

		}

		comboDto.setCombooItems(comboItems);
		return comboDto;
	}

	/***
	 * Get users information, showing them on different pages on web page. This
	 * information can be searched by a web user using different parameters.
	 * Call the mapper to get them. Return the users information.
	 */
	@Override
	public UsuarioDTO getUsersSearch(int numPagina, UsuarioRequestDTO usuarioRequestDTO) {
		List<UsuarioItem> usuarioItems = new ArrayList<UsuarioItem>();
		UsuarioDTO usuarioDTO = new UsuarioDTO();

		usuarioItems = admUsuariosExtendsMapper.getUsersByFilter(numPagina, usuarioRequestDTO);

		if (usuarioItems != null) {
			usuarioDTO.setUsuarioItem(usuarioItems);
		}

		return usuarioDTO;
	}

	@Override
	public UpdateResponseDTO updateUsers(UsuarioUpdateDTO usuarioUpdateDTO) {
		UpdateResponseDTO updateResponseDTO = new UpdateResponseDTO();
		int response1 = 1;
		int response2 = 1;

		if (!usuarioUpdateDTO.getActivo().equalsIgnoreCase("")
				|| !usuarioUpdateDTO.getCodigoExterno().equalsIgnoreCase("")) {
			response1 = admUsuariosExtendsMapper.updateUsersAdmUserTable(usuarioUpdateDTO);
		}
		if (!usuarioUpdateDTO.getGrupo().equalsIgnoreCase("")) {
			response2 = admUsuariosExtendsMapper.updateUsersAdmPerfilTable(usuarioUpdateDTO);
		}

		if (response1 == 1 && response2 == 1)
			updateResponseDTO.setStatus("OK");
		else
			updateResponseDTO.setStatus("ERROR");

		return updateResponseDTO;
	}

	@Override
	public CreateResponseDTO createUsers(UsuarioCreateDTO usuarioCreateDTO, HttpServletRequest request) {
		CreateResponseDTO createResponseDTO = new CreateResponseDTO();
		int response1 = 1;
		int response2 = 1;
		int response3 = 1;

		String dni = UserAuthenticationToken.getUserFromJWTToken(request.getHeader("Authorization"));
		AdmUsuariosExample exampleUsuarios = new AdmUsuariosExample();
		exampleUsuarios.createCriteria().andNifEqualTo(dni);
		exampleUsuarios.createCriteria().andIdinstitucionEqualTo(Short.valueOf(usuarioCreateDTO.getIdInstitucion()));
		admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		List<AdmUsuarios> usuarios = admUsuariosExtendsMapper.selectByExample(exampleUsuarios);
		AdmUsuarios usuario = usuarios.get(0);

		if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("")) {
			response1 = admUsuariosExtendsMapper.createUserAdmUsuariosTable(usuarioCreateDTO, usuario.getIdusuario());
		}
		if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioCreateDTO.getRol().equalsIgnoreCase("")
				&& !usuarioCreateDTO.getRol().equalsIgnoreCase("")) {
			response2 = admUsuariosExtendsMapper.createUserAdmUsuarioEfectivoTable(usuarioCreateDTO,
					usuario.getIdusuario());
		}

		if (!usuarioCreateDTO.getIdInstitucion().equalsIgnoreCase("") && !usuarioCreateDTO.getRol().equalsIgnoreCase("")
				&& !usuarioCreateDTO.getRol().equalsIgnoreCase("")
				&& !usuarioCreateDTO.getGrupo().equalsIgnoreCase("")) {
			response3 = admUsuariosExtendsMapper.createUserAdmUsuariosEfectivoPerfilTable(usuarioCreateDTO,
					usuario.getIdusuario());
		}

		if (response1 == 0 || response2 == 0 || response3 == 0)
			createResponseDTO.setStatus("ERROR");
		else
			createResponseDTO.setStatus("OK");

		return createResponseDTO;
	}

	@Override
	public DeleteResponseDTO deleteUsers(UsuarioDeleteDTO usuarioDeleteDTO) {

		DeleteResponseDTO deleteResponseDTO = new DeleteResponseDTO();
		int response = 0;

		if (!usuarioDeleteDTO.getIdUsuario().equals(null) && usuarioDeleteDTO.getIdUsuario().size() != 0
				&& !usuarioDeleteDTO.getIdInstitucion().equalsIgnoreCase("")) {

			// si activo = S, dar de baja => fecha de baja a fecha actual y
			// activo = N
			if (usuarioDeleteDTO.getActivo().equals("S")) {
				response = admUsuariosExtendsMapper.disableUser(usuarioDeleteDTO);
			}
			// si activo = N, dar de alta => fecha de baja a null y activo = S
			else if (usuarioDeleteDTO.getActivo().equals("N")) {
				response = admUsuariosExtendsMapper.enableUser(usuarioDeleteDTO);
			}
		} else
			response = 0;

		if (response > 0)
			deleteResponseDTO.setStatus("OK");
		else
			deleteResponseDTO.setStatus("ERROR");

		return deleteResponseDTO;
	}

}
