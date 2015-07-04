package junittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import businesslogic.OrderManager;
import datastorage.DatabaseConnection;
import datastorage.ProductDAO;
import domain.ProductIngredients;

public class TestUpdateProduct {
    private OrderManager orderManager;

    /**
     * This method generates the orderManager which contains the method we are going to test.
     * The to be tested method uses a DAO, so we are going to use a stub, to fake the result of the DAO.
     * When the product number is greater than 21, which is the total amount of products, than the result is false. 
     * Otherwise, the result is true.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        orderManager = new OrderManager();
        orderManager.setProductDAO(new ProductDAO() {
            public boolean updateProduct(int productNr, String name, long price, long prepTime) {
                if(productNr > 21) {
                    return false;
                } else {
                    return true;
                }
            }
        });
    }

    @After
    public void tearDown() throws Exception {
    }

    /**
     * This method tests the method of the orderManager, with an existing product.
     * This means that, according to the method in the orderManager, the DAO will be called and the product will be changed.
     */
    @Test
    public void testUpdateExistingProduct() {
        boolean result = orderManager.updateProduct(1, "Portie olijven", 10, 300);
        
        assertEquals(true, result);
    }
    
    /**
     * This method tests the method of the orderManager, with a non-existing product.
     * This means that, according to the method in the orderManager, the DAO will be called and the result will be false.
     */
    @Test
    public void testUpdateNonExistingProduct() {
        boolean result = orderManager.updateProduct(33, "Brood met kruidenboter", 5, 300);
        
        assertEquals(false, result);
    }

}
