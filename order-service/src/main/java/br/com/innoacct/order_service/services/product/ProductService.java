package br.com.innoacct.order_service.services.product;

import br.com.innoacct.order_service.database.models.Product;
import br.com.innoacct.order_service.database.repositories.IProductRepository;
import br.com.innoacct.order_service.dtos.ProductRequest;
import br.com.innoacct.order_service.exceptions.ProductNameAlreadyExistsException;
import br.com.innoacct.order_service.exceptions.ProductNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private final IProductRepository iProductRepository;

    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public void saveNewProduct(ProductRequest request) throws Exception {
        if (iProductRepository.findByName(request.getName()).isPresent()) {
            throw new ProductNameAlreadyExistsException();
        }

        iProductRepository.save(new Product(request));
    }

    public void updateProduct(Product product) throws Exception {
        getProductById(product.getId());
        iProductRepository.save(product);
    }

    public Product getProductById(UUID id) throws Exception{
        return iProductRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public List<Product> getAllProducts() {
        return iProductRepository.findAll();
    }

    public void deleteProduct(UUID id) throws Exception{
        iProductRepository.delete(getProductById(id));
    }
}
