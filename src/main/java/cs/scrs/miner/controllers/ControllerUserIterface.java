package cs.scrs.miner.controllers;

import cs.scrs.miner.dao.block.BlockRepository;
import cs.scrs.miner.dao.transaction.TransactionRepository;
import cs.scrs.miner.dao.user.UserRepository;
import cs.scrs.miner.models.Filechain;
import cs.scrs.service.ip.IPServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Christian on 24/06/2016.
 */
@Component
@RestController
public class ControllerUserIterface {

    @Autowired
    private Filechain filechain;

    @RequestMapping(value = "/fil3chain/starMining", method = RequestMethod.GET)
    public String startMining() {
        // Inutile che ritorno si/no con accodato il chain level basta che torno
        // il chain level e il ricevente sa a chi chiedere tutti i blocchi di cui ha bisogno
        //return blockRepository.findFirstByOrderByChainLevelDesc().getChainLevel();
        filechain.startMining();
        return "OK";
    }

    @RequestMapping(value = "/fil3chain/stopMining", method = RequestMethod.GET)
    public String stopMining() {
        // Inutile che ritorno si/no con accodato il chain level basta che torno
        // il chain level e il ricevente sa a chi chiedere tutti i blocchi di cui ha bisogno
        //filechain.manageMine();
        //return blockRepository.findFirstByOrderByChainLevelDesc().getChainLevel();
        filechain.setFlagRunningMinining(Boolean.FALSE);
        System.out.println("Mining Fermato");
        return "OK";
    }

    @RequestMapping(value = "/fil3chain/sendTransaction", method = RequestMethod.GET)
    public void sendTransaction() {
        //TODO inviare transazioni ad arcieri
        // Inutile che ritorno si/no con accodato il chain level basta che torno
        // il chain level e il ricevente sa a chi chiedere tutti i blocchi di cui ha bisogno
        //filechain.manageMine();
        //return blockRepository.findFirstByOrderByChainLevelDesc().getChainLevel();
    }

}
