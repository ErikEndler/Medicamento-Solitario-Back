package com.apirest.MedicamentoSolidario.controle;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apirest.MedicamentoSolidario.Models.Medicamento;
import com.apirest.MedicamentoSolidario.Models.PontoColeta;
import com.apirest.MedicamentoSolidario.config.DataUtil;
import com.apirest.MedicamentoSolidario.dto.MedicamentoDTO;
import com.apirest.MedicamentoSolidario.dto.MedicamentoRespostaDTO;
import com.apirest.MedicamentoSolidario.errors.ResourceNotFoundException;
import com.apirest.MedicamentoSolidario.repository.DoacaoRepository;
import com.apirest.MedicamentoSolidario.repository.MedicamentoRepository;
import com.apirest.MedicamentoSolidario.repository.PontoColetaRepository;
import com.apirest.MedicamentoSolidario.repository.RecebimentoRepository;

@Service
public class MedicamentoControle {

	@Autowired
	MedicamentoRepository repository;
	@Autowired
	DoacaoRepository doacaoRepositoy;
	@Autowired
	RecebimentoRepository recebimentoRepository;
	@Autowired
	PontoColetaRepository pontoColetaRepository;
	@Autowired
	DataUtil dataUtil;

	public Medicamento salvar(Medicamento medicamento) {
		Optional<Medicamento> ret = verifySave(medicamento.getId());
		if (ret.isPresent()) {
			throw new ResourceNotFoundException(MenssagemErro() + " existente para o  ID: " + medicamento.getId());

		} else {
			verificadoacaoExiste(medicamento);
			medicamento.setStatus(false);
			return repository.save(medicamento);
		}
	}

	public List<MedicamentoRespostaDTO> listarPorPonto(long pontoID) {
		verificaPonto(pontoID);
		List<Medicamento> lista = repository.findMedicamentosByPonto(pontoID);
		List<MedicamentoRespostaDTO> result = new ArrayList<>();
		for (Medicamento medicamento : lista) {
			result.add(MedicamentoRespostaDTO.transformaEmDTOList(medicamento));
		}
		return result;
	}
	private void verificaPonto(long pontoID) {
		Optional<PontoColeta> ponto = pontoColetaRepository.findById(pontoID);
		if (!ponto.isPresent()) {
			throw new ResourceNotFoundException( " Ponto de Coleta solicitado nao existente  " );
		}
	}

	public Iterable<MedicamentoRespostaDTO> listarTodosNormal() {
		Iterable<Medicamento> listar = repository.findAll();
		List<MedicamentoRespostaDTO> result = new ArrayList<MedicamentoRespostaDTO>();
		for (Medicamento str : listar) {
			result.add(MedicamentoRespostaDTO.transformaEmDTOList(str));
		}
		return result;
	}

	public Optional<Medicamento> listar(long id) {
		verifyIfObjectExists(id);
		Optional<Medicamento> findById = repository.findById(id);
		return findById;
	}

	public void deletarById(long id) {
		verifyIfObjectExists(id);
		repository.deleteById(id);
	}

	public Medicamento atualizar(MedicamentoDTO medicamentoDTO) {
		// pega a data em string e converteem Localdate
		LocalDate data = dataUtil.converterData(medicamentoDTO.getDataValidade());
		// pega a data convertida e adiciona a variavel tipo localdate
		medicamentoDTO.setDataValidadeLocalDate(data);
		verifyIfObjectExists(medicamentoDTO.getId());
		medicamentoDTO = setDoacao(medicamentoDTO);
		
		return repository.save(medicamentoDTO.TransformarParaObjEditar());
	}

//---------------------------------------------------------------------//
	private void verifyIfObjectExists(long id) {
		String msg = MenssagemErro();
		Optional<Medicamento> retorno = repository.findById(id);
		retorno.orElseThrow(() -> new ResourceNotFoundException(msg + " nao encontrado para o ID: " + id));
	}

	private Optional<Medicamento> verifySave(long id) {
		Optional<Medicamento> retorno = repository.findById(id);
		return retorno;

	}

	protected String MenssagemErro() {
		String msg = "Medicamento";
		return msg;
	}

	// verifica se a doação aq ual medicamento esta atrelado existe
	private void verificadoacaoExiste(Medicamento med) {
		if (med.getDoacao_in() == null) {
			throw new ResourceNotFoundException(MenssagemErro() + " Falha ao pegar DOACAO ");
		}
	}

	// busca a doação pelo id recebido na requisisao e coloca no medicamentoDTO
	private MedicamentoDTO setDoacao(MedicamentoDTO medicamentoDTO) {
		medicamentoDTO.setFullDoacaoIn(doacaoRepositoy.findById(medicamentoDTO.getIdDoacaoIn()).get());
		return medicamentoDTO;
	}
	
	private MedicamentoDTO setDoacaoOut(MedicamentoDTO medicamentoDTO) {
		medicamentoDTO.setFullDoacaoOut(recebimentoRepository.findById(medicamentoDTO.getIdDoacaoIn()).get());
		return medicamentoDTO;
	}

}
