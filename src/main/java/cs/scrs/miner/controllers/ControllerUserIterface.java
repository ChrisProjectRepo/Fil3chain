package cs.scrs.miner.controllers;


import cs.scrs.config.KeysConfig;
import cs.scrs.config.network.Network;
import cs.scrs.miner.dao.transaction.Transaction;
import cs.scrs.miner.dao.user.User;
import cs.scrs.miner.dao.user.UserRepository;
import cs.scrs.miner.models.Filechain;
import cs.scrs.service.connection.ConnectionServiceImpl;
import cs.scrs.service.poolDispatcher.PoolDispatcherServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cs.scrs.service.request.AsyncRequest;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;



/**
 * 
 */
@Component
@RestController
public class ControllerUserIterface {

	@Autowired
	private Filechain filechain;
	@Autowired
	private KeysConfig keyProperties;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AsyncRequest asyncRequest;
	@Autowired
	private Network networkProperties;

	@Autowired
	RestTemplate restTemplate;
	@Autowired
	PoolDispatcherServiceImpl poolD;
	
	@Autowired
	ConnectionServiceImpl connectionServiceImpl; 
	
	@RequestMapping(value = "/fil3chain/ips", method = RequestMethod.GET)
	@ResponseBody
	public List<String> getAllIpAddresses() {
		return connectionServiceImpl.getAllIpAddresses();
	}

	@RequestMapping(value = "/fil3chain/checkMining", method = RequestMethod.GET)
	@ResponseBody
	public String checkMining() {

		System.out.println("Mininig: " + filechain.getFlagRunningMinining().toString());
		return filechain.getFlagRunningMinining().toString();
	}

	@RequestMapping(value = "/fil3chain/starMining", method = RequestMethod.GET)
	@ResponseBody
	public String startMining() {

		// Inutile che ritorno si/no con accodato il chain level basta che torno
		// il chain level e il ricevente sa a chi chiedere tutti i blocchi di cui ha bisogno
		// return blockRepository.findFirstByOrderByChainLevelDesc().getChainLevel();
		filechain.startMining();
		return "{\"response\":\"ACK\"}";
	}

	@RequestMapping(value = "/fil3chain/stopMining", method = RequestMethod.GET)
	@ResponseBody
	public String stopMining() {

		// Inutile che ritorno si/no con accodato il chain level basta che torno
		// il chain level e il ricevente sa a chi chiedere tutti i blocchi di cui ha bisogno
		// filechain.manageMine();
		// return blockRepository.findFirstByOrderByChainLevelDesc().getChainLevel();
		filechain.setFlagRunningMinining(Boolean.FALSE);
		System.out.println("Mining Fermato");
		return "{\"response\":\"ACK\"}";
	}

	@RequestMapping(value = "/fil3chain/sendTransaction", method = RequestMethod.POST)
	@ResponseBody
	public String sendTransaction(@RequestBody String transaction) throws Exception {

		System.out.println("Transazione arrivata: " + transaction);
		// asyncRequest.doPost("http://"+networkProperties.getEntrypoint().getIp()+":"+networkProperties.getEntrypoint().getPort()+ networkProperties.getPooldispatcher().getBaseUri()+networkProperties.getActions().getSendTransaction(), transaction);
		return "{\"response\":\"ACK\"}";
	}

	@RequestMapping(value = "/fil3chain/init_user", method = RequestMethod.POST)
	@ResponseBody
	public String initUser(@RequestBody User user) throws Exception {

		System.out.println("User arrivato: " + user);
		// asyncRequest.doPost("http://"+networkProperties.getEntrypoint().getIp()+":"+networkProperties.getEntrypoint().getPort()+ networkProperties.getPooldispatcher().getBaseUri()+networkProperties.getActions().getSendTransaction(), transaction);
		user.setPublicKey(keyProperties.getPublicKey());
		String pkh = org.apache.commons.codec.digest.DigestUtils.sha256Hex(keyProperties.getPublicKey());
		user.setPublicKeyHash(pkh);
		userRepository.save(user);

		User user1 = userRepository.findByPublicKey(keyProperties.getPublicKey());
		System.out.println("User founded " + user1);
		return "{\"response\":\"ACK\"}";
	}

	@RequestMapping(value = "/fil3chain/transactions", method = RequestMethod.GET)
	@ResponseBody
	public String add_transaction(@RequestBody String transaction) throws Exception {

		System.out.println("Transaction arrived: " + transaction);
//		List<Transaction> result = poolD.getTransactions();
		return "{\"response\":\"ACK\"}";
	}

	@RequestMapping(value = "/fil3chain/citations", method = RequestMethod.GET)
	@ResponseBody
	public List<Transaction> get_citations() throws Exception {
		
//		String urlTransaction = "http://" + networkProperties.getEntrypoint().getIp() + ":" + networkProperties.getEntrypoint().getPort() + networkProperties.getPooldispatcher().getBaseUri() + networkProperties.getActions().getGetTransaction();
//		System.out.println("Ui Controller Citation");
//		System.out.println("Url transaction PD " + urlTransaction);
		// ResponseEntity<Transaction[]> ips = restTemplate.getForEntity(urlTransaction,Transaction[].class);
		// Transaction[] result = ips.getBody();
		List<Transaction> result = filechain.getAllAvalaibleCit();
		System.out.println("Citations length " + result.size());
//		for (Transaction string : result) {
//			System.out.println("Transaction found " + string);
//		}
		// String x = asyncRequest.doGet( urlTransaction );
		// System.out.println("transazioni ricevute "+x);
		return result;
	}

}
