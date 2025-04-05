package ca.concordia.soen6441.project;

import ca.concordia.soen6441.project.context.GameEngineTest;
import ca.concordia.soen6441.project.gameplay.CountryAssignmentTest;
import ca.concordia.soen6441.project.gameplay.orders.AdvanceTest;
import ca.concordia.soen6441.project.gameplay.orders.AirliftTest;
import ca.concordia.soen6441.project.gameplay.orders.BlockadeTest;
import ca.concordia.soen6441.project.gameplay.orders.BombTest;
import ca.concordia.soen6441.project.map.*;
import ca.concordia.soen6441.project.phases.AssignReinforcementsTest;
import ca.concordia.soen6441.project.phases.IssueOrderTest;
import ca.concordia.soen6441.project.phases.StartupTest;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("junit-jupiter")
//@SelectPackages("com.example.tests") // Replace with your test package
@SelectClasses({AdvanceTest.class, AirliftTest.class, BlockadeTest.class, BombTest.class, CountryAssignmentTest.class, ContinentImplTest.class, CountryImplTest.class, MapFileReaderTest.class, ValidateMapFromFile.class, ValidateMapImplTest.class, AssignReinforcementsTest.class, IssueOrderTest.class, StartupTest.class, GameEngineTest.class,})

public class AllTestsSuite
{
}

