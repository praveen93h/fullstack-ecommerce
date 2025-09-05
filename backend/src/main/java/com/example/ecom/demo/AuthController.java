@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authManager;
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authManager,
                          UserRepository userRepo,
                          PasswordEncoder passwordEncoder,
                          JwtUtil jwtUtil) { ... }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userRepo.existsByUsername(req.getUsername())) {
            return ResponseEntity.badRequest().body("Username taken");
        }
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.getRoles().add("ROLE_USER");
        userRepo.save(u);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );
        UserDetails ud = (UserDetails) auth.getPrincipal();
        String token = jwtUtil.generateToken(ud);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
