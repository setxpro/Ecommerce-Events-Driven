package br.com.innoacct.order_service.database.repositories;

import br.com.innoacct.order_service.database.models.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IProductRepository extends MongoRepository<Product, UUID> {
    Optional<Product> findByName(String string);
}
