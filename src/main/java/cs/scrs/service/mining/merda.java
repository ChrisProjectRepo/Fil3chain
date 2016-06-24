//package cs.scrs.service.mining;
//
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//import java.util.concurrent.Future;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.AsyncResult;
//
//import cs.scrs.miner.dao.block.Block;
//import cs.scrs.miner.dao.transaction.Transaction;
//import cs.scrs.miner.dao.user.User;
//import cs.scrs.miner.models.IP;
//import cs.scrs.miner.models.MerkleTree;
//import cs.scrs.service.util.AudioUtil;
//import cs.scrs.service.util.CryptoUtil;
//
//
//
//public class merda extends Thread implements Runnable {
//
//	public Boolean isInitialized() {
//
//		return (block != null && difficulty != -1);
//	}
//
//	public void initializeService() {
//
//		System.out.println("Inizializza servizio");
//		// Prendo l'ultmo blocco della catena
//		Block lastBlock = blockRepository.findFirstByOrderByChainLevelDesc();
//
//		// Inizializzo il nuovo blocco da minare
//		block = new Block();
//		block.setFatherBlockContainer(lastBlock.getHashBlock());
//		block.setChainLevel(lastBlock.getChainLevel() + 1);
//		block.setMinerPublicKey(publicKey);
//		block.setUserContainer(new User("", "Ciano", "Bug", "Miner", "Mail", "Cianone"));
//
//		// Prendo le transazioni dal Pool Dispatcher
//		List<Transaction> transactionsList = PoolDispatcherUtility.getTransactions();
//
//		ArrayList<String> hashTransactions = new ArrayList<>();
//		for (Transaction transaction : transactionsList) {
//			hashTransactions.add(transaction.getHashFile());
//		}
//		block.setMerkleRoot(MerkleTree.buildMerkleTree(hashTransactions));
//
//		// Test chiamata per difficoltà
//		Integer complexity = PoolDispatcherUtility.getCurrentComplexity();
//
//		previousBlock = lastBlock;
//		difficulty = complexity;
//		transactions = transactionsList;
//	}
//
//	public void updateService(Block miningBlock, Block previousBlock, int difficulty, List<Transaction> transactionList) {
//
//		System.out.println("Update service");
//		this.block = miningBlock;
//		this.previousBlock = previousBlock;
//		this.difficulty = difficulty;
//		this.transactions = transactionList;
//	}
//
//	private Boolean verifyHash(byte[] hash) {
//
//		// Verifica dei primi fullMask byte interi
//		for (int i = 0; i < fullMask; i++) {
//			if (hash[i] != 0) {
//				return false;
//			}
//		}
//
//		// Se non ci sono bit restanti allora restituisce true
//		if (restMask == 0)
//			return true;
//
//		// Altrimenti controlla i bit rimanenti
//		return (hash[fullMask] & restMask) == 0;
//	}
//
//	@Override
//	public void run() {
//
//		try {
//			mine();
//		} catch (Exception ex) {
//			Logger.getLogger(MiningServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
//		}
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see cs.scrs.service.mining.IMiningServiceImpl#mine()
//	 */
//	@Override
//	public void mine() throws Exception {
//
//		if (difficulty == -1) {
//			System.err.println("Complessità per il calcolo del blocco errata, impossibile minare");
//			return;
//		}
//
//		if (block == null)
//			initializeService();
//
//		// Calcolo le maschere per il check dell'hash.
//		calculateMasks();
//
//		// Tempo di inizio mining
//		long startTime = new Date().getTime();
//
//		// Nonce
//		Integer nonce = new Random().nextInt();
//		Integer nonceStart = nonce;
//		Integer nonceFinish = 0;
//		float totalTime = 0;
//
//		System.out.println("Nonce di partenza: " + nonce);
//
//		// Hash del blocco
//		byte[] hash;
//
//		do {
//			// Genera nuovo hash
//			hash = org.apache.commons.codec.digest.DigestUtils.sha256(block.toString() + nonce);
//
//			// Incremento il nonce
//			nonce++;
//		} while (!verifyHash(hash));
//		AudioUtil.alert(); // avviso sonoro
//		nonceFinish = nonce - 1;
//		totalTime = (new Date().getTime() - startTime) / 1000.0f;
//
//		// Calcolo hash corretto in esadecimale
//		// Spiegazione nonce - 1: Viene fatto -1 perché nell'ultima iterazione
//		// viene incrementato anche se l'hash era corretto.
//		String hexHash = org.apache.commons.codec.digest.DigestUtils.sha256Hex(block.toString() + (nonce - 1));
//
//		// Impostazione dell'hash e del nonce
//		block.setHashBlock(hexHash);
//		block.setNonce(nonce - 1);
//		block.setSignature(CryptoUtil.sign(hexHash, privateKey));
//		block.setMinerPublicKey(publicKey);
//		block.setFatherBlockContainer(previousBlock.getHashBlock());
//		block.setTransactionsContainer(transactions);
//
//		block.setCreationTime(Long.toString(System.currentTimeMillis()));
//		System.out.println("Hash trovato: " + block.getHashBlock() + " con difficoltà: " + difficulty + " Nonce: " + nonce + " Tempo impiegato: " + totalTime + " secondi");
//		System.out.println("Hash provati: " + (Math.abs(nonceFinish - nonceStart)) + " HashRate: " + (((Math.abs(nonceFinish - nonceStart)) / totalTime) / 1000000.0f) + " MH/s");
//		// Chiude il thread
//		// interrupt();
//		// Salvo il blocco
//		if (blockRepository != null)
//			blockRepository.save(block);
//		// per ogni transazione mette il riferimento al blocco container
//		int indexInBlock = 0;
//		for (Transaction trans : transactions) {
//			trans.setBlockContainer(block.getHashBlock());
//			trans.setIndexInBlock(indexInBlock);
//			System.out.println(trans.getIndexInBlock());
//			transRepo.save(trans);
//			indexInBlock++;
//		}
//
//		sendBlockToMiners();
//
//		// TODO: Ricomincia a minare
//		initializeService();
//		mine();
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see cs.scrs.service.mining.IMiningServiceImpl#sendBlockToMiners()
//	 */
//	@Override
//	@Async
//	public Future<List<Block>> sendBlockToMiners() throws InterruptedException {
//
//		//
//		// SimpleClientHttpRequestFactory rf = ((SimpleClientHttpRequestFactory) restTemplate.getRequestFactory());
//		// rf.setReadTimeout(1000 * 5);
//		// rf.setConnectTimeout(1000 * 5);
//		// restTemplate.setRequestFactory(rf);
//
//		List<Block> blocks = new ArrayList<Block>();
//		String bool = Boolean.FALSE.toString();
//		Map<IP, Integer> map = new HashMap<IP, Integer>();
//		Map<IP, Integer> counter = Collections.synchronizedMap(map);
//		connectionServiceImpl.firstConnectToEntryPoint();
//		synchronized (counter) {
//			for (IP ip : ipService.getIPList()) {
//				counter.put(ip, 0);
//			}
//
//			System.out.println("dimensione lista hashmap " + counter.size());
//
//		}
//
//		while (counter.size() > 0) {
//
//			for (IP ip : ipService.getIPList()) {
//				System.out.println("Invio blocco a: " + ip.getIp());
//				try {
//					// String response = HttpUtil.doPost("http://" + ip.getIp() + "/fil3chain/newBlock",
//					// JsonUtility.toJson(block));
//
//					String response = restTemplate.postForObject("http://" + ip.getIp() + "/fil3chain/newBlock", block, String.class);
//					System.out.println("Ho inviato il blocco e mi è ritornato come risposta: " + response);
//					synchronized (counter) {
//
//						// Se ho mandato il blocco rimuovo il miner
//						counter.remove(ip);
//
//					}
//				} catch (Exception e) {
//					e.printStackTrace();
//					sleep(1000);
//					System.out.println("Il miner " + ip.getIp() + " non è più connesso.");
//					System.out.println("Errore invio blocco: " + bool);
//				} finally {
//					synchronized (counter) {
//						// altrimenti aumenta il counter di uno
//
//						counter.put(ip, counter.get(ip) + 1);
//						if (counter.get(ip) > 5)// TODO PRENDERE PROPERTIES
//							counter.remove(ip);
//
//					}
//				}
//			}
//
//		}
//
//		// Annullo il blocco appena minato
//		block = null;
//
//		return new AsyncResult<>(blocks);
//	}
//
//	/*
//	 * (non-Javadoc)
//	 * @see cs.scrs.service.mining.IMiningServiceImpl#interrupt()
//	 */
//	@Override
//	public void interrupt() {
//
//		super.interrupt();
//		if (interruptCallback != null)
//			interruptCallback.run();
//	}
//
//}
