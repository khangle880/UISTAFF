package classComponent;

import java.util.concurrent.Callable;

import com.jfoenix.controls.JFXTextArea;

import javafx.animation.AnimationTimer;
import javafx.beans.binding.Bindings;
import javafx.scene.Node;


public class ExpandableTextArea extends JFXTextArea {
	public ExpandableTextArea(String text) {
		super(text);
		setMinHeight(24);
		new AnimationTimer() {
			@Override
			public void handle(long now) {
				Node text = lookup(".text");
				if (text != null) {
					prefHeightProperty().bind(
						Bindings.createDoubleBinding(
							new Callable<Double>() {
								@Override
								public Double call() throws Exception {
									return text.getBoundsInLocal().getHeight();
								}
							}, text.boundsInLocalProperty()).add(15));
					this.stop();
				}
			}
		}.start();
	}
	public ExpandableTextArea() {
		this("");
	}
}