package br.com.metasix.batch.executor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.metasix.resolucao.cli.StatusProcessamentoLoteClient;
import br.com.metasix.resolucao.cli.httpcomponents.StatusProcessamentoLoteClientImpl;
import br.com.metasix.resolucao.cli.v2.SolicitacaoClient;
import br.com.metasix.resolucao.cli.v2.httpcomponents.SolicitacaoClientImpl;

@SpringBootApplication
public class BatchExecutorApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchExecutorApplication.class, args);
	}
	
	@Bean
	public SolicitacaoClient solicitacaoClient() {
		return new SolicitacaoClientImpl("http://localhost:8080/resolucao-ws", ""
				+ "7Hmc_WuPoZ-sEiaM1xxbGung3Ik5-XcYw9Zn8Oqta6kN7TulxJ_qZIHN0yXFpX43O5pN9UryEOcvus-619QrJKQbwhYNgJpTnoNNUtLNc70=", 
				"RgNcOcBsUgFKr_Jx7Hdlgg==");
	}
	
	@Bean
	public StatusProcessamentoLoteClient statusProcessamentoLoteClient() {
		return new StatusProcessamentoLoteClientImpl("http://localhost:8080/resolucao-ws", ""
				+ "7Hmc_WuPoZ-sEiaM1xxbGung3Ik5-XcYw9Zn8Oqta6kN7TulxJ_qZIHN0yXFpX43O5pN9UryEOcvus-619QrJKQbwhYNgJpTnoNNUtLNc70=");
	}
	
	@Bean
	public br.com.metasix.resolucao.cli.SolicitacaoClient solicitacaoClientResolucao() {
		return new br.com.metasix.resolucao.cli.httpcomponents.SolicitacaoClientImpl("http://localhost:8080/resolucao-ws",
				"7Hmc_WuPoZ-sEiaM1xxbGung3Ik5-XcYw9Zn8Oqta6kN7TulxJ_qZIHN0yXFpX43O5pN9UryEOcvus-619QrJKQbwhYNgJpTnoNNUtLNc70="
				, "RgNcOcBsUgFKr_Jx7Hdlgg==");
	}
}
