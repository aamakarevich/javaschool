import com.tsystems.ecare.app.model.Plan;
import com.tsystems.ecare.app.services.impl.PlanServiceImpl;
import com.tsystems.ecare.config.root.DevelopmentConfiguration;
import com.tsystems.ecare.config.root.RootContextConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;

@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("development")
@ContextConfiguration(classes={DevelopmentConfiguration.class, RootContextConfig.class})
public class PlanServiceTest {

    @Autowired
    private PlanServiceImpl planService;

    @Test
    public void test() {
        Plan plan = new Plan();
        plan.setTitle("Title");
        plan.setDescription("Description");
        plan.setMonthlyFee(new BigDecimal(10));
        planService.saveNewPlan(plan);
    }
}