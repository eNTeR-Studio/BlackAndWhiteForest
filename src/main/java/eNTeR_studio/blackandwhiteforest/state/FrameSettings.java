package eNTeR_studio.blackandwhiteforest.state;

import java.net.URL;
import java.net.URLDecoder;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.newdawn.slick.state.BasicGameState;

import eNTeR_studio.blackandwhiteforest.BlackAndWhiteForest;
import eNTeR_studio.blackandwhiteforest.api.BAWFUsefulFunctions;

public class FrameSettings extends JFrame {

	private static final long serialVersionUID = -5301893661086347194L;
	public static int scaling_back = 10;

	public BasicGameState state;

	public FrameSettings(BasicGameState state) throws Exception {
		super("Black And White Forest Settings");
		this.state = state;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(BlackAndWhiteForest.width, BlackAndWhiteForest.height);

		BlackAndWhiteForest.separator="/";
		String iconPath = URLDecoder.decode("file://"+
				BAWFUsefulFunctions.getResource("assets", "fxzjshm", "textures", "framesettings", "back.png"));
		BlackAndWhiteForest.separator=(BlackAndWhiteForest.isWindowsOs ? "\\" : "/");
		URL buttonImageUrl = new URL(iconPath);
		Icon buttonImage = new ImageIcon(buttonImageUrl);
		JButton buttonBack = new JButton(buttonImage);
		buttonBack.setBounds(0, 0, BlackAndWhiteForest.width / scaling_back, BlackAndWhiteForest.height / scaling_back);
		getContentPane().add(buttonBack);

		JLabel emptyLabel = new JLabel();
		emptyLabel.setBounds(0, 0, 0, 0);
		getContentPane().add(emptyLabel);
	}
}
