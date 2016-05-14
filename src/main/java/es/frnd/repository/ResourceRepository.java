package es.frnd.repository;

import es.frnd.model.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by fernando on 27/04/16.
 */
@Repository
public interface ResourceRepository extends PagingAndSortingRepository<Resource, String> {

    Resource findByUri(String uri);
}
