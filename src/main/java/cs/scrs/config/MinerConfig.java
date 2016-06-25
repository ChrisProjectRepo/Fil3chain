package cs.scrs.config;


import java.util.ArrayList;
import java.util.Collections;

import cs.scrs.ui.config.AUiConfig;
import cs.scrs.ui.config.UiConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import cs.scrs.service.connection.ConnectionServiceImpl;
import cs.scrs.service.ip.IPServiceImpl;
import cs.scrs.service.mining.IMiningService;
import cs.scrs.service.mining.MiningServiceImpl;
import cs.scrs.service.mining.VerifyServiceImpl;
import cs.scrs.service.request.AsyncRequest;
import cs.scrs.miner.models.*;



@Configuration
public class MinerConfig {

	// [FORSE, DA TESTARE] I BEAN DEVONO ESSERE IN ORDINE DI UTILIZZO (Quelli che usano "altri oggetti" devono avere gli "altri oggetti",ovvero i BEan, già instanziati)
	// Creazione del bean per ottenere i servizi altrove

	@Bean
	@ConfigurationProperties(prefix = "custom.rest.connection")
	public HttpComponentsClientHttpRequestFactory HttpRequestFactory() {
		System.out.println("1");
		return new HttpComponentsClientHttpRequestFactory();
	}

	@Bean
	public RestTemplate RestTemplate() {
		System.out.println("2");
		return new RestTemplate(HttpRequestFactory());
	}
	

	@Bean
	public IPServiceImpl IPServiceImpl() {
		System.out.println("3");
		IPServiceImpl ipServiceImpl = new IPServiceImpl();
		ipServiceImpl.setIpList(Collections.synchronizedList(new ArrayList<>()));
		return ipServiceImpl;
	}

	@Bean
	public AsyncRequest AsyncRequest() {
		System.out.println("4");
		AsyncRequest asyncRequest = new AsyncRequest();
		asyncRequest.loadConfiguration();
		return asyncRequest;
	}

	
	@Bean
	public ConnectionServiceImpl ConnectionServiceImpl() {
		System.out.println("5");
		ConnectionServiceImpl connectionServiceImpl = new ConnectionServiceImpl();
		connectionServiceImpl.selectIp();
		connectionServiceImpl.loadNetworkConfig();

		return connectionServiceImpl;
	}

	
	@Bean
	public VerifyServiceImpl verifyServiceImpl() {
		System.out.println("6");
		VerifyServiceImpl verifyServiceImpl = new VerifyServiceImpl();
		return verifyServiceImpl;

	}
	
	
//	@Bean
//	public IMiningService miningService() {
//		System.out.println("7");
//		IMiningService miningService = new MiningServiceImpl();
//		miningService.loadKeyConfig();
//		return miningService;
//
//	}

	@Bean
	public Filechain Filechain() {
		System.out.println("8");
		Filechain filechain = new Filechain();
		return filechain;
	}





	/*
	 * @Override public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException { for (String beanName : beanFactory.getBeanDefinitionNames()) { beanFactory.getBeanDefinition(beanName).setLazyInit(Boolean.TRUE); } }
	 */

}
