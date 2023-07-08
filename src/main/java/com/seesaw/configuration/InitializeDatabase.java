package com.seesaw.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seesaw.exception.UserExistException;
import com.seesaw.model.*;
import com.seesaw.repository.TokenRepository;
import com.seesaw.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.seesaw.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Configuration
public class InitializeDatabase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CollectionService collectionService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private CartService cartService;

    @Autowired
    private CartDetailService cartDetailService;

    private CategoryModel categoryModel;
    @Bean
    public CommandLineRunner loadDatabase() {
        return args -> {
            addUser("admin", "admin", "admin@gmail.com", "123456", "male", "0123456789", Role.ADMIN);
            addUser("trong", "dat", "nguyentrongdat1108@gmail.com", "123456", "male", "0123456789", Role.USER);
            addUser("quoc", "bao", "quocbao@gmail.com", "123456", "male", "0123456789", Role.USER);
            addUser("Lam", "Nhu", "lamnhu@gmail.com", "123456", "female", "0123456789", Role.USER);
            addUser("Minh", "Thu", "minhthu@gmail.com", "123456", "female", "0123456789", Role.USER);
//            loadCategory();
//            loadCollection();
//            loadInvoices();
//            loadProducts();
//            loadFeedbacks();
//            loadCarts();
//            loadCartDetail();
//            loadOrder();
        };
    }

    private void loadCategory() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            TypeReference<List<CategoryModel>> categoryReference = new TypeReference<List<CategoryModel>>() {};
            InputStream categoryStream = TypeReference.class.getResourceAsStream("/json/categories.json");
            List<CategoryModel> categories = mapper.readValue(categoryStream, categoryReference);
            categoryService.save(categories);
            System.out.println("Categories Saved!");

            TypeReference <List<CollectionModel>> collectionReference = new TypeReference<List<CollectionModel>>() {};
            InputStream collectionStream = TypeReference.class.getResourceAsStream("/json/collections.json");
            List<CollectionModel> collections = mapper.readValue(collectionStream, collectionReference);
            collectionService.save(collections);
            System.out.println("Collections Saved!");

            TypeReference<List<ProductModel>> productReference = new TypeReference<List<ProductModel>>() {};
            InputStream productStream = TypeReference.class.getResourceAsStream("/json/products.json");
            List<ProductModel> products = mapper.readValue(productStream, productReference);

            products.forEach(product -> {
               product.setDate_created(new Date());
               product.setCollection(collections.get(0));
               product.setCategory(categories.get(0));
            });
            productService.save(products);
            System.out.println("Products Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save categories: " + e.getMessage());
        }
    }

    private void loadCollection() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CollectionModel>> typeReference = new TypeReference<List<CollectionModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/collections.json");
        try {
            List<CollectionModel> collections = mapper.readValue(inputStream, typeReference);
            collectionService.save(collections);
            System.out.println("Collections Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save collections: " + e.getMessage());
        }
    }

    private void loadInvoices() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<InvoiceModel>> typeReference = new TypeReference<List<InvoiceModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/invoices.json");
        try {
            List<InvoiceModel> invoices = mapper.readValue(inputStream, typeReference);
            invoiceService.save(invoices);
            System.out.println("Invoices Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save invoices: " + e.getMessage());
        }
    }

    private void loadProducts() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<ProductModel>> typeReference = new TypeReference<List<ProductModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/products.json");
        try {
            List<ProductModel> products = mapper.readValue(inputStream, typeReference);
            products.forEach(product -> {
                product.setCategory(categoryModel);
                System.out.println(product.getCategory().getName());
            });
            productService.save(products);
            System.out.println("Products Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save products: " + e.getMessage());
        }
    }

    private void loadOrder() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<OrderModel>> typeReference = new TypeReference<List<OrderModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/orders.json");
        try {
            List<OrderModel> orders = mapper.readValue(inputStream, typeReference);
            orderService.save(orders);
            System.out.println("Orders Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save orders: " + e.getMessage());
        }
    }

    private void loadFeedbacks() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<FeedbackModel>> typeReference = new TypeReference<List<FeedbackModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/feedbacks.json");
        try {
            List<FeedbackModel> feedbacks = mapper.readValue(inputStream, typeReference);
            feedbackService.save(feedbacks);
            System.out.println("Feedbacks Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save feedbacks: " + e.getMessage());
        }
    }

    private void loadCarts() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CartModel>> typeReference = new TypeReference<List<CartModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/carts.json");
        try {
            List<CartModel> carts = mapper.readValue(inputStream, typeReference);
            cartService.save(carts);
            System.out.println("Carts Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save carts: " + e.getMessage());
        }
    }

    private void loadCartDetail() {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<List<CartDetailModel>> typeReference = new TypeReference<List<CartDetailModel>>() {};
        InputStream inputStream = TypeReference.class.getResourceAsStream("/json/cart_details.json");
        try {
            List<CartDetailModel> cartDetails = mapper.readValue(inputStream, typeReference);
            cartDetailService.save(cartDetails);
            System.out.println("CartDetails Saved!");

        } catch (IOException e) {
            System.out.println("Unable to save cartDetails: " + e.getMessage());
        }
    }

    private void addUser(String firstName, String lastName, String email, String password, String gender, String contact, Role role) {
        var user = UserModel.builder()
                .firstname(firstName)
                .lastname(lastName)
                .email(email)
                .password(passwordEncoder.encode(password))
                .gender(gender)
                .contact(contact)
                .avatar("avatar ne")
                .date_created(Date.from(java.time.Instant.now()))
                .role(role)
                .build();
        if (userRepository.existsUserModelByEmail(email)) {
            userRepository.delete(user);
            System.out.println("User deleted!");
        } else {
            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            saveUserToken(savedUser, jwtToken);
            System.out.println("User saved!");
            userRepository.save(user);
        }
    }

//    private void checkUser(String email) {
//        if (userRepository.existsUserModelByEmail(email)) {
//            userRepository.delete(userRepository.findByEmail(email).orElseThrow());
//        }
//    }

    private void saveUserToken(UserModel user, String jwtToken) {
        var token = TokenModel.builder()
                .users(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }
}
