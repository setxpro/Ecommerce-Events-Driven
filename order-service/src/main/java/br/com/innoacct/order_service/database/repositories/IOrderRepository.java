package br.com.innoacct.order_service.database.repositories;

import br.com.innoacct.order_service.database.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IOrderRepository extends MongoRepository<Order, UUID> {
}
