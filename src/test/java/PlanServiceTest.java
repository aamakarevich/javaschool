import com.tsystems.ecare.app.model.Address;
import com.tsystems.ecare.app.model.Customer;
import com.tsystems.ecare.app.model.SearchResult;
import com.tsystems.ecare.app.security.SecurityUserDetailsService;
import com.tsystems.ecare.app.services.CustomerService;
import com.tsystems.ecare.app.services.impl.CustomerServiceImpl;
import com.tsystems.ecare.app.services.PlanService;
import com.tsystems.ecare.config.root.DevelopmentConfiguration;
import com.tsystems.ecare.config.root.RootContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("development")
@ContextConfiguration(classes={DevelopmentConfiguration.class, RootContextConfig.class})
public class PlanServiceTest {

    @Autowired
    private PlanService planService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SecurityUserDetailsService securityUserDetailsService;

    @Test
    public void test() throws NoSuchAlgorithmException {
        /*Plan plan = new Plan();
        plan.setTitle("Title");
        plan.setDescription("Description");
        plan.setMonthlyFee(new BigDecimal(10));
        planService.savePlan(plan);*/
//        SearchResult<Customer> result = customerService.findCustomers(null, 10, 2);
//        System.out.println(result);
        for (int i = 0; i < 100; i++) {
            Customer customer = new Customer();
            customer.setFirstName("Ivan");
            customer.setLastName("Ivanov");
            customer.setBirthdate(new Date());
            customer.setPassport("0000 000000");
            Address address = new Address();
            address.setCity("Saint Petersburg");
            address.setAddress1("Nevskiy prospect");
            address.setAddress2("Building 92");
            customer.setAddress(address);
            customerService.saveNewCustomer(customer);
        }
    }
}