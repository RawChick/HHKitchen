package junittests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import businesslogic.OrderManager;
import datastorage.ProductIngredientsDAO;
import domain.ProductIngredients;

public class TestRetrieveIngredients {
    private OrderManager orderManager;
    
    /**
     * This method generates the orderManager which contains the method we are going to test.
     * The to be tested method uses a DAO, so we are going to use a stub, to fake the reulst of the DAO.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        orderManager = new OrderManager();
        orderManager.setProductIngredientsDAO(new ProductIngredientsDAO() {
            public List<ProductIngredients> retrieveIngredients(int productNr) {
                List<ProductIngredients> newIngredients = new ArrayList<ProductIngredients>();
                
                ProductIngredients newIngredient = new ProductIngredients(1, "Appel", productNr, 50, "gram");

                newIngredients.add(newIngredient);
                
                return newIngredients;
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        
    }

    /**
     * This method tests the method of the orderManager, when there are no exisiting objects in the ArrayList.
     * This means that, according to the method in the orderManager, the DAO has to be called.
     */
    @Test
    public void testWithoutExisitingObjects() {
        System.out.println("Methode testWithoutExisitingObjects is aangeroepen");
        
        List<ProductIngredients> listProductIngredients = orderManager.retrieveIngredients(2);
        
        assertEquals(1, listProductIngredients.size());
    }
    
    /**
     * This method generates 3 objects and inserts them into the ArrayList inside the orderManager.
     * According to the method, the DAO is not being called, because of the already existing objects.
     */
    @Test 
    public void testWithExistingObjects() {
        System.out.println("Methode testWithExisitingObjects is aangeroepen");
        
        ProductIngredients newIngredient_1 = new ProductIngredients(1, "Appel", 3, 50, "gram");
        ProductIngredients newIngredient_2 = new ProductIngredients(2, "Banaan", 3, 50, "gram");
        ProductIngredients newIngredient_3 = new ProductIngredients(3, "Kaneel", 3, 3, "gram");
        
        orderManager.addProductIngredients(newIngredient_1);
        orderManager.addProductIngredients(newIngredient_2);
        orderManager.addProductIngredients(newIngredient_3);
        
        List<ProductIngredients> listProductIngredients = orderManager.retrieveIngredients(3);
        
        assertEquals(3, listProductIngredients.size());
    }
    
    /**
     * This method tests the scenario where objects exists, but where the product number does not comply with the objects' product number.
     */
    @Test
    public void testWithExistingObjectsDifferentProductNr() {
        System.out.println("Methode testWithExisitingObjectsDifferentProductNr is aangeroepen");
        
        ProductIngredients newIngredient_1 = new ProductIngredients(1, "Appel", 3, 50, "gram");
        ProductIngredients newIngredient_2 = new ProductIngredients(2, "Banaan", 3, 50, "gram");
        ProductIngredients newIngredient_3 = new ProductIngredients(3, "Kaneel", 3, 3, "gram");
        
        orderManager.addProductIngredients(newIngredient_1);
        orderManager.addProductIngredients(newIngredient_2);
        orderManager.addProductIngredients(newIngredient_3);
        
        List<ProductIngredients> listProductIngredients = orderManager.retrieveIngredients(4);
        
        assertEquals(1, listProductIngredients.size());
    }
}
