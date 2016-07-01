package cs.scrs.service.poolDispatcher;


import cs.scrs.miner.dao.block.Block;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cs.scrs.miner.dao.transaction.Transaction;
import cs.scrs.service.request.AsyncRequest;

import java.util.*;

/**
 *
 * Insieme di metodi statici per le richieste di utilità
 * al Pool Dispatcher
 *
 */



@Service
public class PoolDispatcherServiceImpl {

	private static final int TRANSINBLOCK = 3;//TODO TROVARMI POSTO E METTERMI NEL PROPERTIES
	@Autowired
	private AsyncRequest asyncRequest;
	
	
	
    /**
     * Metodo sincrono per la richiesta della complessità attuale
     * @return La complessoità attuale o -1 nel caso di errore
     */
    public Integer getCurrentComplexity() {
        try {
            JSONObject result  = new JSONObject(asyncRequest.doPost("http://vmanager:80/sdcmgr/PD/get_complexity", "{\"date\" : \"" + new Date().getTime() + "\"}"));
            return (Integer) result.get("complexity");
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * Metodo sincrono per la richiesta della complessità
     * al tempo della creazione di un blocco
     * @return La complessoità al tempo della creazione del blocco o -1 nel caso di errore
     */
    public Integer getBlockComplexity(String blockCreationTime) {
    	Boolean flag = Boolean.TRUE;
        while(flag)
    	try {
            JSONObject result  = new JSONObject(asyncRequest.doPost("http://vmanager:80/sdcmgr/PD/get_complexity", "{\"date\" : \"" + blockCreationTime + "\"}"));
            flag = Boolean.FALSE;
            return ((Integer) (result.get("complexity")));
        } catch (Exception e) {
            e.printStackTrace();
            try {
				Thread.sleep(250);
			} catch (InterruptedException e1) {
				
				e1.printStackTrace();
			}
            return -1;
        }
        return -1;
    }

    public  List<Transaction> getTransactions() {
        //TODO: Mettere chiamata al server reale
        Set<Block> p=new HashSet<>();
        ArrayList<Transaction> transactions = new ArrayList<>();

        for(int i = 0; i < TRANSINBLOCK; i++) {
            // Transazione mock
            Transaction transaction = new Transaction();
            transaction.setFilename("Ciano's file " + new Random().nextInt());
            transaction.setHashFile(org.apache.commons.codec.digest.DigestUtils.sha256Hex(transaction.getFilename()));

            transactions.add(transaction);
        }

        long seed = System.nanoTime();
        Collections.shuffle(transactions, new Random(seed));

        return transactions;
    }

}
