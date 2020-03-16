package com.apirest.MedicamentoSolidario.controle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.apirest.MedicamentoSolidario.Models.Role;
import com.apirest.MedicamentoSolidario.Models.Usuario;
import com.apirest.MedicamentoSolidario.dto.UsuarioDTO;
import com.apirest.MedicamentoSolidario.dto.UsuarioRespostaDTO;
import com.apirest.MedicamentoSolidario.errors.ResourceNotFoundException;
import com.apirest.MedicamentoSolidario.repository.RoleRepository;
import com.apirest.MedicamentoSolidario.repository.UsuarioRepository;

@Service
public class UsuarioControle {
	final static String DATE_FORMAT = "dd/MM/yyyy";

	SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	RoleRepository roleRepository;

	public Usuario salvar2(UsuarioDTO userDTO)  {
		verificaCPF(userDTO.getCpf());
		validaRole(userDTO);
		verificaData(userDTO);
		// criptografa a senha
		String senha = new BCryptPasswordEncoder().encode(userDTO.getSenha());
		userDTO.setSenha(senha);
		// busca o objeto role atraves do nome da role recebido da reqisiçao
		Role role = findRole(userDTO.getRole());
		// valida a role
		if (role == null) {
			throw new ResourceNotFoundException(MenssagemErro() + " NÂO È POSSIVEL CADASTRAR SEM ROLE ");
		} else {
			userDTO.setFullRole(role);
		}
		Usuario user = userDTO.trsnformaParaObjSalvar();
		return usuarioRepository.save(user);

	}

	// função que busca no banco a role recebida no formulario
	private Role findRole(String pRole) {
		pRole.toUpperCase();
		if (pRole.contains("ADMIN")) {
			return roleRepository.findByNameRole("ROLE_ADMIN");
		} else if (pRole.contains("INTERMEDIADOR")) {
			return roleRepository.findByNameRole("ROLE_INTERMEDIADOR");
		} else if (pRole.contains("USER")) {
			return roleRepository.findByNameRole("ROLE_USER");
		} else
			throw new ResourceNotFoundException(pRole + " ROLE INVALIDA PARA CADASTRO! ");
	}

	public Iterable<UsuarioRespostaDTO> listarTodosNormal() {
		Iterable<Usuario> listar = usuarioRepository.findAll();
		List<UsuarioRespostaDTO> result = new ArrayList<UsuarioRespostaDTO>();
		for (Usuario str : listar) {
			result.add(UsuarioRespostaDTO.transformaEmDTO(str));
		}
		return result;
	}

	public Optional<Usuario> listar(long id) {
		verifyIfObjectExists(id);
		Optional<Usuario> findById = usuarioRepository.findById(id);
		return findById;
	}

	public UsuarioRespostaDTO listarDTO(long id) {
		verifyIfObjectExists(id);
		Optional<Usuario> findById = usuarioRepository.findById(id);
		UsuarioRespostaDTO resposta = UsuarioRespostaDTO.transformaEmDTO(findById.get());
		return resposta;
	}

	public void deletarById(long id) {
		verifyIfObjectExists(id);
		usuarioRepository.deleteById(id);
	}

	public void deletar(Usuario usuario) {
		verifyIfObjectExists(usuario.getId());
		usuarioRepository.delete(usuario);
	}

	public Usuario atualizar(Usuario usuario) {
		verifyIfObjectExists(usuario.getId());
		return usuarioRepository.save(usuario);
	}

	// ---------------------// METODOS DE VALISAÇAO //------------------------------
	private void verificaData(UsuarioDTO userDTO){
		if (userDTO.getNascimento() == null) {
			throw new ResourceNotFoundException(MenssagemErro() + " Data Vazia! ");
		}
		isDateValid(userDTO.getNascimento());
	}

	public void isDateValid(Date date) {
		DateFormat df = new SimpleDateFormat ("yyyy/MM/dd");
		String strData =df.format(date);
		df.setLenient (false); // aqui o pulo do gato
		try {
		    df.parse (strData);
		    // data válida
		} catch (ParseException ex) {
			throw new ResourceNotFoundException(" Esta data é invalida : "+"' " +date+" '");
		}
	}

		private void validaRole(UsuarioDTO userDTO) {
		if (userDTO.getRole().isEmpty()) {
			throw new ResourceNotFoundException(MenssagemErro() + " Campo role esta vazio! ");
		}

	}

	private void verifyIfObjectExists(long id) {
		String msg = MenssagemErro();
		Optional<Usuario> retorno = usuarioRepository.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(msg + " nao encontrado para o ID: " + id));
	}

	private void verificaCPF(String cpf) {
		Usuario user = usuarioRepository.findByCpf(cpf);
		if (user != null) {
			throw new ResourceNotFoundException(MenssagemErro() + " existente para o  CPF: " + user.getCpf());
		}

	}

	private Optional<Usuario> verifySave(long id) {
		Optional<Usuario> retorno = usuarioRepository.findById(id);
		return retorno;

	}

	protected String MenssagemErro() {
		String msg = "Objeto";
		return msg;
	}

}
