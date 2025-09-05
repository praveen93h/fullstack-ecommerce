public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE " +
            "LOWER(p.name) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page<Product> search(@Param("q") String q, Pageable pageable);
}
