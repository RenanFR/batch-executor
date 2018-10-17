package br.com.metasix.batch.executor.job;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.metasix.resolucao.cli.StatusProcessamentoLoteClient;
import br.com.metasix.resolucao.cli.pojo.Solicitacao;
import br.com.metasix.resolucao.cli.pojo.StatusProcessamentoLote;
import br.com.metasix.resolucao.cli.v2.SolicitacaoClient;

@Component
@EnableScheduling
public class Scheduler {
	
	@Autowired
	private SolicitacaoClient solicitacaoClient;
	
	@Autowired
	private StatusProcessamentoLoteClient statusProcessamentoLoteClient;
	
	@Autowired
	private br.com.metasix.resolucao.cli.SolicitacaoClient solicitacaoClientResolucao;
	
	@Scheduled(cron = "0 * * ? * *")
	public void changeStatusByConfiguration() {
		List<StatusProcessamentoLote> listProcessamentoLote = statusProcessamentoLoteClient.buscarTodasAsRegras();
		List<Solicitacao> listSolicitacao = new ArrayList<>();
		solicitacaoClientResolucao.listByStatusWithBatchRule().forEach(solic -> {
			Solicitacao solicitacao = solicitacaoClientResolucao.findById(solic);
			listSolicitacao.add(solicitacao);
		});;
		listProcessamentoLote.forEach(proc -> {
			if (proc.getDataUltimoProcessamento() == null) {
				listSolicitacao
				.stream()
				.filter(sol -> sol.getHistoricoAtual().getStatus().getId() == proc.getStatusOrigem().getId())
				.forEach(sol -> {
					sol .getHistoricoAtual().setStatus(proc.getStatusSolicitacaoDestino());
					solicitacaoClientResolucao.update(sol);
				});
			} else {
				listProcessamentoLote
				.stream()
				.filter(p -> p.getDataUltimoProcessamento().plusDays(p.getQtdDiasProcessar()).toLocalDate() == LocalDate.now())
				.forEach(p -> {
					listSolicitacao
					.stream()
					.filter(sol -> sol.getHistoricoAtual().getStatus().getId() == p.getStatusOrigem().getId())
					.forEach(sol -> {
						sol.getHistoricoAtual().setStatus(p.getStatusSolicitacaoDestino());
						solicitacaoClientResolucao.update(sol);
					});
				});
			}
		});
	}
	
}
