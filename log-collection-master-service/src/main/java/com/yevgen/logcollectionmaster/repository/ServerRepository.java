package com.yevgen.logcollectionmaster.repository;

import com.yevgen.logcollectionmaster.model.Server;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServerRepository extends CrudRepository<Server, String> {
}
