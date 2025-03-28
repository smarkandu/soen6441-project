package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.gameplay.CountryAssignmentTest;
import ca.concordia.soen6441.project.gameplay.orders.AdvanceTest;
import ca.concordia.soen6441.project.gameplay.orders.AirliftTest;
import ca.concordia.soen6441.project.gameplay.orders.Blockade;
import ca.concordia.soen6441.project.gameplay.orders.BlockadeTest;
import ca.concordia.soen6441.project.map.ValidateMapFromFile;
import ca.concordia.soen6441.project.map.ValidateMapImplTest;
import ca.concordia.soen6441.project.phases.AssignReinforcementsTest;
import ca.concordia.soen6441.project.phases.IssueOrderTest;
import ca.concordia.soen6441.project.phases.OrderExecutionTest;
import ca.concordia.soen6441.project.phases.StartupTest;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("junit-jupiter")
//@SelectPackages("com.example.tests") // Replace with your test package
@SelectClasses({
        GameEngineTest.class,
        StartupTest.class,
        OrderExecutionTest.class,
        IssueOrderTest.class,
        AssignReinforcementsTest.class,
        ValidateMapImplTest.class,

        CountryAssignmentTest.class,
        BlockadeTest.class,
        AirliftTest.class,
        AdvanceTest.class
})

public class AllTestsSuite {
}

