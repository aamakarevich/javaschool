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
    public void test() {
        /*Plan plan = new Plan();
        plan.setTitle("Title");
        plan.setDescription("Description");
        plan.setMonthlyFee(new BigDecimal(10));
        planService.savePlan(plan);*/
        UserDetails details = securityUserDetailsService.loadUserByUsername("andy@ecare.com");
        System.out.println(details);
    }
}