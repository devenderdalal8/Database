package Replication.Implementation.RoutingDataSource;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    @ReadOnly
    public List<Product> getProducts() {
        return repository.findAll();
    }

    @Transactional
    public void createProduct(Product product) {
        repository.save(product);
    }
}