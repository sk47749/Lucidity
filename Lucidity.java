package Lucidity_automation.Lucidity_automation;

 

 import org.junit.jupiter.api.BeforeEach;
 import org.junit.jupiter.api.Test;
 import org.mockito.Mock;
 import org.mockito.MockitoAnnotations;
 import org.mockito.Mockito;
 import static org.mockito.Mockito.*;
 import static org.junit.jupiter.api.Assertions.*;

	import java.util.HashMap;
	import java.util.Map;
	
	public class Lucidity
	{

	    @Mock
	    private CartService cartService;  // Service that interacts with the cart API

	    @Mock
	    private OfferService offerService; // Service that interacts with the offer API

	    @Mock
	    private UserService userService;  // Service that interacts with the user segment API

	    @BeforeEach
	    void setup() 
	    {
	        MockitoAnnotations.openMocks(this);
	    }

	    // Test Case: Apply Flat Amount Off Offer for p1
	    @Test
	    public void testApplyFlatAmountOffOfferForP1() 
	    {
	        // Mock user segment response (p1)
	        when(userService.getUserSegment(1)).thenReturn("p1");

	        // Mock offer response (10 off for p1)
	        when(offerService.applyOffer(200, "p1")).thenReturn(190);

	        // Apply offer on cart and validate
	        int finalAmount = cartService.applyOfferToCart(200, 1, 1);  // 200 cart value, user_id=1, restaurant_id=1
	        assertEquals(190, finalAmount, "The cart value after applying offer should be 190");
	    }

	    // Test Case: Apply Flat Percentage Off Offer for p1
	    @Test
	    public void testApplyFlatPercentageOffOfferForP1() {
	        // Mock user segment response (p1)
	        when(userService.getUserSegment(1)).thenReturn("p1");

	        // Mock offer response (10% off for p1)
	        when(offerService.applyOffer(200, "p1")).thenReturn(180);

	        // Apply offer on cart and validate
	        int finalAmount = cartService.applyOfferToCart(200, 1, 1);  // 200 cart value, user_id=1, restaurant_id=1
	        assertEquals(180, finalAmount, "The cart value after applying offer should be 180");
	    }

	    // Test Case: Apply No Offer for p1
	    @Test
	    public void testApplyNoOfferForP1() {
	        // Mock user segment response (p1)
	        when(userService.getUserSegment(1)).thenReturn("p1");

	        // Mock no offer response (no discount)
	        when(offerService.applyOffer(200, "p1")).thenReturn(200);

	        // Apply offer on cart and validate
	        int finalAmount = cartService.applyOfferToCart(200, 1, 1);  // 200 cart value, user_id=1, restaurant_id=1
	        assertEquals(200, finalAmount, "The cart value after applying no offer should remain 200");
	    }

	    // Test Case: Apply 100% Off Offer for p1
	    @Test
	    public void testApply100PercentOffOfferForP1() {
	        // Mock user segment response (p1)
	        when(userService.getUserSegment(1)).thenReturn("p1");

	        // Mock 100% off offer response
	        when(offerService.applyOffer(200, "p1")).thenReturn(0);

	        // Apply offer on cart and validate
	        int finalAmount = cartService.applyOfferToCart(200, 1, 1);  // 200 cart value, user_id=1, restaurant_id=1
	        assertEquals(0, finalAmount, "The cart value after applying 100% offer should be 0");
	    }

	    // Test Case: Invalid Segment (p4)
	    @Test
	    public void testApplyOfferForInvalidSegment() {
	        // Mock user segment response (p4 - invalid)
	        when(userService.getUserSegment(1)).thenReturn("p4");

	        // Mock no offer response (no discount)
	        when(offerService.applyOffer(200, "p4")).thenReturn(200);

	        // Apply offer on cart and validate
	        int finalAmount = cartService.applyOfferToCart(200, 1, 1);  // 200 cart value, user_id=1, restaurant_id=1
	        assertEquals(200, finalAmount, "The cart value should remain 200 for invalid segment");
	    }

	    // Test Case: Apply Offer with Large Cart Value
	    @Test
	    public void testApplyOfferWithLargeCartValue() {
	        // Mock user segment response (p1)
	        when(userService.getUserSegment(1)).thenReturn("p1");

	        // Mock offer response (10 off for p1)
	        when(offerService.applyOffer(1000, "p1")).thenReturn(990);

	        // Apply offer on cart and validate
	        int finalAmount = cartService.applyOfferToCart(1000, 1, 1);  // 1000 cart value, user_id=1, restaurant_id=1
	        assertEquals(990, finalAmount, "The cart value after applying offer should be 990");
	    }
	}

	class CartService {
	    private OfferService offerService;
	    private UserService userService;

	    public CartService(OfferService offerService, UserService userService) {
	        this.offerService = offerService;
	        this.userService = userService;
	    }

	    public int applyOfferToCart(int cartValue, int userId, int restaurantId) {
	        String segment = userService.getUserSegment(userId);
	        return offerService.applyOffer(cartValue, segment);
	    }
	}

	class OfferService {
	    public int applyOffer(int cartValue, String segment) {
	        // Mock logic to apply offer
	        if (segment.equals("p1")) {
	            return cartValue - 10;  // Flat 10 off for p1
	        }
	        return cartValue;  // No discount for others
	    }
	}

	class UserService {
	    public String getUserSegment(int userId) {
	        // Mock logic to return user segment
	        if (userId == 1) {
	            return "p1";
	        }
	        return "p4";  // Invalid segment for other user IDs
	    }
	}
}
