package cs.scrs.service.mining;


import java.util.List;
import java.util.concurrent.Future;

import org.springframework.web.client.RestTemplate;

import cs.scrs.miner.dao.block.Block;
import cs.scrs.miner.dao.block.BlockRepository;
import cs.scrs.miner.dao.transaction.Transaction;
import cs.scrs.miner.dao.transaction.TransactionRepository;
import cs.scrs.service.connection.ConnectionServiceImpl;
import cs.scrs.service.ip.IPServiceImpl;
import cs.scrs.service.poolDispatcher.PoolDispatcherServiceImpl;



public interface MiningServiceInt{

	/**
	 *
	 */
	void loadKeyConfig();


	/**
	 * Metodo per minare un blocco
	 * @return 
	 */
	Future<Boolean> mine() throws Exception;

	Future<List<Block>> sendBlockToMiners() throws InterruptedException;

	Block getBlock();

	void setBlock(Block block);

	String getPrivateKey();

	void setPrivateKey(String privateKey);

	Integer getDifficulty();

	void setDifficulty(Integer difficulty);

	Boolean isInitialized();

	void updateService(Block miningBlock, Block previousBlock, int difficulty, List<Transaction> transactionList);

	void initializeService();

	void updateMiningService();

	/**
	 * @return the fullMask
	 */
	Integer getFullMask();

	/**
	 * @param fullMask the fullMask to set
	 */
	void setFullMask(Integer fullMask);

	/**
	 * @return the restMask
	 */
	byte getRestMask();

	/**
	 * @param restMask the restMask to set
	 */
	void setRestMask(byte restMask);

	/**
	 * @return the interruptCallback
	 */
	Runnable getInterruptCallback();

	/**
	 * @param interruptCallback the interruptCallback to set
	 */
	void setInterruptCallback(Runnable interruptCallback);

	/**
	 * @return the publicKey
	 */
	String getPublicKey();

	/**
	 * @param publicKey the publicKey to set
	 */
	void setPublicKey(String publicKey);

	/**
	 * @return the previousBlock
	 */
	Block getPreviousBlock();

	/**
	 * @param previousBlock the previousBlock to set
	 */
	void setPreviousBlock(Block previousBlock);

	/**
	 * @return the transactions
	 */
	List<Transaction> getTransactions();

	/**
	 * @param transactions the transactions to set
	 */
	void setTransactions(List<Transaction> transactions);

	/**
	 * @return the blockRepository
	 */
	BlockRepository getBlockRepository();

	/**
	 * @param blockRepository the blockRepository to set
	 */
	void setBlockRepository(BlockRepository blockRepository);

	/**
	 * @return the transRepo
	 */
	TransactionRepository getTransRepo();

	/**
	 * @param transRepo the transRepo to set
	 */
	void setTransRepo(TransactionRepository transRepo);

	/**
	 * @return the restTemplate
	 */
	RestTemplate getRestTemplate();

	/**
	 * @param restTemplate the restTemplate to set
	 */
	void setRestTemplate(RestTemplate restTemplate);

	/**
	 * @return the connectionServiceImpl
	 */
	ConnectionServiceImpl getConnectionServiceImpl();

	/**
	 * @param connectionServiceImpl the connectionServiceImpl to set
	 */
	void setConnectionServiceImpl(ConnectionServiceImpl connectionServiceImpl);

	/**
	 * @return the ipService
	 */
	IPServiceImpl getIpService();

	/**
	 * @param ipService the ipService to set
	 */
	void setIpService(IPServiceImpl ipService);

	/**
	 * @return the poolDispService
	 */
	PoolDispatcherServiceImpl getPoolDispService();

	/**
	 * @param poolDispService the poolDispService to set
	 */
	void setPoolDispService(PoolDispatcherServiceImpl poolDispService);

	public Boolean getStopMining();

	public void setStopMining(Boolean stopMining);
}