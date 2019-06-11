package ru.trofimov.server.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.trofimov.server.domain.ContentEntity;

import java.util.Optional;

public interface ContentRepository extends CrudRepository<ContentEntity, String> {

    //added for demonstration only. CrudRepository already has findById method
    @Query("select product from ContentEntity product where product.id = :id")
    Optional<ContentEntity> findProductById(@Param("id") String productId);
}
