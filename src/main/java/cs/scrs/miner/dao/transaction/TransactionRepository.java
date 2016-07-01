package cs.scrs.miner.dao.transaction;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import cs.scrs.miner.dao.block.Block;


@RepositoryRestResource(collectionResourceRel = "trsnsaction", path = "transactions")
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> findByhashFile(String hashBlock);

    Transaction findByHashFile(@Param("hashFile") String hashFile);
    

}
