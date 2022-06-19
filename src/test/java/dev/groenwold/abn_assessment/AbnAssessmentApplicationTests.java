package dev.groenwold.abn_assessment;

import dev.groenwold.abn_assessment.controller.RecipeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AbnAssessmentApplicationTests {

	@Autowired
	private RecipeController recipeController;
	@Test
	void contextLoads() {
		assertNotNull(recipeController);
	}
}
