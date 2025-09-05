@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductRepository repo;
    public ProductController(ProductRepository repo) { this.repo = repo; }

    @GetMapping
    public Page<Product> list(
            @RequestParam(value="q", required=false) String q,
            @RequestParam(value="page", defaultValue="0") int page,
            @RequestParam(value="size", defaultValue="12") int size) {
        Pageable p = PageRequest.of(page, size, Sort.by("id").descending());
        if (q == null || q.isBlank()) {
            return repo.findAll(p);
        }
        return repo.search(q, p);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return repo.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
