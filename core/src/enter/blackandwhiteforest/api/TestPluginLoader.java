package enter.blackandwhiteforest.api;

import enter.blackandwhiteforest.BlackAndWhiteForest;

public class TestPluginLoader implements IBAWFPlugin{

	@Override
	public void init() {
		BlackAndWhiteForest.stage.setDebugAll(false);
	}

}
