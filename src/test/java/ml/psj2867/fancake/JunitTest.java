package ml.psj2867.fancake;


import org.junit.jupiter.api.Test;
import org.junit.platform.commons.annotation.Testable;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Testable
public class JunitTest {

	@Test
	public void testLog() {
		log.info("asfd");
	}

}
