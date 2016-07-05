package cs.scrs.miner.dao.citations;


import cs.scrs.miner.dao.block.*;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;



// findTop10BychainLevelOrderBychainLeveleAsc(Integer cLevel);

@RepositoryRestResource(collectionResourceRel = "citation", path = "citations")
public interface CitationRepository extends CrudRepository<Citation, Long> {

	
}
