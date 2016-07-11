package cs.scrs.miner.controllers;

import cs.scrs.config.KeysConfig;
import cs.scrs.miner.dao.block.Block;
import cs.scrs.miner.dao.block.BlockRepository;
import cs.scrs.miner.dao.login.LoginRepository;
import cs.scrs.miner.models.DndTree;
import cs.scrs.miner.models.Filechain;
import cs.scrs.miner.models.IP;
import cs.scrs.miner.models.WidgetModel;
import cs.scrs.service.ip.IPServiceImpl;
import cs.scrs.service.mining.IMiningService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.codec.digest.DigestUtils.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Christian on 11/07/2016.
 */
@Component
@RestController
public class ControllerStatistics {

    @Autowired
    private IPServiceImpl ipService;
    @Autowired
    private BlockRepository blockRepository;
    @Autowired
    private KeysConfig keyProperties;
    @Autowired
    private Filechain filechain;



    @RequestMapping(value = "/fil3chain/statistics", method = RequestMethod.POST)
    @ResponseBody
    public String statistics(@RequestBody WidgetModel widgetModel) {

        switch (widgetModel.getName()){
            case "ips":
                    return "{\"value\":"+getNumbersConnectedIp()+"}";
            case "blocks":
                    return "{\"value\":"+blockRepository.findAll().size()+"}";
            case "fil3chain":
                getBlockToDraw(widgetModel.getPage());
                return null;
            case "mlevel":
                //Attuale chain level massimo
                return "{\"value\":"+blockRepository.findFirstByOrderByChainLevelDesc().getChainLevel()+"}";
            case "power":
                //Potenza media di calcolo dell'hash
                return "{\"value\":"+filechain.getMiningService().getAveragePowerMachine()+"}";

        }
        return null;
    }


    private Integer getNumbersConnectedIp(){
        return ipService.getIPList().size();
    }

    private String getBlockToDraw(Integer val){
        Integer inf=10*(val-1);
        Integer sup=10*(val)-1;

        String myHashKey= DigestUtils.sha256Hex(keyProperties.getPublicKey());


        List<Block> blocks=blockRepository.findByChainLevelBetweenOrderByChainLevelAsc(inf,sup);

        HashMap<String,DndTree> result=new HashMap<>();

        HashMap<Integer,List<Block>> temp=new HashMap<>();


        for(Block b:blocks){
            temp.put(b.getChainLevel(),new ArrayList<>());
        }


        for(Block b:blocks){
            temp.get(b.getChainLevel()).add(b);
        }


        for(Block b:blocks){
            if(temp.get(inf).size()>1)
                result.put(b.getHashBlock(),new DndTree("previous","{\"Style\":\"other\"}"));
            else
                if(b.getMinerPublicKey().equals(myHashKey))
                    result.put(b.getHashBlock(),new DndTree(b.getHashBlock(),"{\"Style\":\"my\"}"));
                else
                    result.put(b.getHashBlock(),new DndTree(b.getHashBlock(),"{\"Style\":\"other\"}"));

        }

        return null;
    }


}

