@Entity
@Table(name="orders")
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    private Double totalAmount;

    private String status; // e.g., CREATED, PAID

    private String transactionId; // from dummy payment

    @OneToMany(cascade = CascadeType.ALL, mappedBy="order")
    private List<OrderItem> items = new ArrayList<>();
    // getters/setters
}

@Entity
@Table(name="order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;
    // getters/setters
}
