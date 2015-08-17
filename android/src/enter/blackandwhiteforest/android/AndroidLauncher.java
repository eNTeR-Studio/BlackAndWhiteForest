package enter.blackandwhiteforest.android;

import android.os.Bundle;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import enter.blackandwhiteforest.BlackAndWhiteForest;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		//BlackAndWhiteForest.loader=new DexClassLoader(null, Gdx.files.internal("dex/").file().getAbsolutePath(), null, ClassLoader.getSystemClassLoader());
		initialize(BlackAndWhiteForest.INSTANSE, config);
	}
}
