package cs.scrs.miner;


import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

import cs.scrs.miner.models.Filechain;
import cs.scrs.service.mining.IMiningService;



@SpringBootApplication
@ComponentScan("cs.scrs")
@EnableAsync
public class Fil3Chain implements CommandLineRunner {

	@Autowired
	Filechain filechain;
	@Autowired
	RestTemplate restTemplate;
	@Autowired
	IMiningService ms;


	// avvia applicazione SpringBoot con il thread run
	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(Fil3Chain.class, args);
		//
		// Stampa dei Bean
		// String[] beanNames = ctx.getBeanDefinitionNames();
		// Arrays.sort(beanNames);
		// for (String beanName : beanNames) {
		// System.out.println(beanName);
		// }

	}

	// Applicazione reale
	@Override
	public void run(String... args) throws Exception {
		System.out.println(ms.getPrivateKey()+" "+ms.getPublicKey());
//		filechain.initializeFilechain();
//		filechain.update();
//		filechain.manageMine();

		// inizializzazione dell apllicazione
		// filechain.update();
		// set-up rete
		// set-ip blockchain

		// System.out.println(async.getIpFromEntryPoint("http://vmanager:80/sdcmgr/EP/user_connect", new IP("10.192.0.8")));
		// ResponseEntity<ArrayList> entity = restTemplate.getForEntity("http://10.192.0.7:8080/fil3chain/getBlockByChain?chainLevel=1",
		// ArrayList.class);
		// ArrayList<Block> blocks= entity.getBody();
		//
		// System.out.println("Body: "+blocks);
		// for (Block block : blocks) {
		// System.out.println(block.toString());
		// }

	}

}
