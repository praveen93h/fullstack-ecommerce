@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(@RequestBody CheckoutRequest req, Principal principal) {
        // principal.getName() -> username
        User user = userRepo.findByUsername(principal.getName()).orElseThrow();
        // Validate items, calculate total, decrement stock (simple version)
        Order order = new Order();
        order.setUser(user);
        double total = 0;
        for (CheckoutItem ci : req.getItems()) {
            Product p = productRepo.findById(ci.getProductId()).orElseThrow();
            if (p.getStock() < ci.getQuantity()) return ResponseEntity.badRequest().body("Not enough stock");
            OrderItem oi = new OrderItem();
            oi.setProductId(p.getId());
            oi.setProductName(p.getName());
            oi.setPrice(p.getPrice().doubleValue());
            oi.setQuantity(ci.getQuantity());
            oi.setOrder(order);
            order.getItems().add(oi);
            total += p.getPrice().doubleValue() * ci.getQuantity();
            p.setStock(p.getStock() - ci.getQuantity());
            productRepo.save(p);
        }
        order.setTotalAmount(total);
        // dummy payment: generate tx id and set status
        order.setStatus("PAID");
        order.setTransactionId(UUID.randomUUID().toString());
        orderRepo.save(order);
        return ResponseEntity.ok(Map.of("status", "success", "transactionId", order.getTransactionId()));
    }
}
