package br.com.innoacct.order_service.services.order;

import br.com.innoacct.order_service.database.models.Order;
import br.com.innoacct.order_service.database.models.Product;
import br.com.innoacct.order_service.database.repositories.IOrderRepository;
import br.com.innoacct.order_service.database.repositories.IProductRepository;
import br.com.innoacct.order_service.dtos.OrderRequest;
import br.com.innoacct.order_service.dtos.OrderResponse;
import br.com.innoacct.order_service.exceptions.InsufficientStockException;
import br.com.innoacct.order_service.exceptions.OrderNotFoundException;
import br.com.innoacct.order_service.services.product.ProductService;
import br.com.innoacct.order_service.services.sns.AwsSnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class OrderService {
    private final IOrderRepository iOrderRepository;
    private final ProductService productService;
    private final IProductRepository iProductRepository;
    private final AwsSnsService awsSnsService;

    public OrderService(IOrderRepository iOrderRepository, ProductService productService, IProductRepository iProductRepository, AwsSnsService awsSnsService) {
        this.iOrderRepository = iOrderRepository;
        this.productService = productService;
        this.iProductRepository = iProductRepository;
        this.awsSnsService = awsSnsService;
    }

    public void saveNewOrder(OrderRequest request) throws Exception {
        Product product = productService.getProductById(request.getProductId());

        if(product.getQuantity() < request.getQuantity()) throw new InsufficientStockException();

        product.setQuantity(product.getQuantity() - request.getQuantity());
        productService.updateProduct(product);

        Order order = new Order(request, product);
        iOrderRepository.save(order);

        awsSnsService.publish(new OrderResponse(order, iProductRepository).toString());
    }

    public OrderResponse getOrderById(UUID id) throws Exception{
        Order order = iOrderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        return new OrderResponse(order, iProductRepository);
    }

    public List<OrderResponse> getAllOrders() {
        return iOrderRepository.findAll().stream()
                .map(o -> new OrderResponse(o, iProductRepository))
                .sorted(Comparator.comparing(OrderResponse::getOrderTime).reversed())
                .toList();
    }

    public void updatePaymentStatus(UUID id, String status) throws Exception{
        Order order = iOrderRepository.findById(id)
                .orElseThrow(OrderNotFoundException::new);

        order.setPaymentStatus(status);

        iOrderRepository.save(order);
    }
}
