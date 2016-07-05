package cs.scrs.service.poolDispatcher;


import cs.scrs.miner.dao.block.Block;
import cs.scrs.miner.dao.transaction.Transaction;
import cs.scrs.service.request.AsyncRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






import com.google.common.reflect.TypeToken;
import cs.scrs.config.KeysConfig;
import cs.scrs.config.network.Network;
import cs.scrs.miner.dao.block.Block;
import cs.scrs.miner.dao.transaction.Transaction;
import cs.scrs.miner.dao.user.User;
import cs.scrs.miner.dao.user.UserRepository;
import cs.scrs.miner.models.Filechain;
import cs.scrs.service.connection.ConnectionServiceImpl;
import cs.scrs.service.poolDispatcher.PoolDispatcherServiceImpl;
import cs.scrs.service.request.AsyncRequest;
import cs.scrs.service.util.Conversions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;


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
    @Autowired
    private Network networkProperties;
	
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

        List<Transaction> transactionsTemp = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        String URL = "http://" + networkProperties.getEntrypoint().getIp() + ":" + networkProperties.getEntrypoint().getPort() + networkProperties.getPooldispatcher().getBaseUri() + networkProperties.getActions().getGetTransaction();
        String request= null;
        try {
            request = asyncRequest.doGet(URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Type type = new TypeToken<List<Transaction>>() {}.getType();
        transactionsTemp=Conversions.fromJson(request,type);

//        for(int i = 0; i < TRANSINBLOCK; i++) {
//            // Transazione mock
//            Transaction transaction = new Transaction();
//            transaction.setFilename("Ciano's file " + new Random().nextInt());
//            transaction.setHashFile(org.apache.commons.codec.digest.DigestUtils.sha256Hex(transaction.getFilename()));
//
//            transactions.add(transaction);
//        }
//
        //Controllo che non siano vuote
        if(!transactionsTemp.isEmpty()){

        //Eseguo uno shuffle in maniera randomica di tutte le transazioni che mi sono arrivate
        long seed = System.nanoTime();
        Collections.shuffle(transactionsTemp, new Random(seed));

//            if(TRANSINBLOCK>transactionsTemp.size())
//                size=transactionsTemp.size();
//            else
//                size=TRANSINBLOCK;
//
            for(int i=0;i<TRANSINBLOCK;i++){
                //Eseguo un piccolo controllo in modo da evitare di inserire più blocchi
                //di quanti effettivamente ne possiedo
                if(i<transactionsTemp.size())
                transactions.add(transactionsTemp.get(i));
            }
        }

        return transactions;
    }

}
