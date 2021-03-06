package cmu.varviz;

import org.eclipse.swt.graphics.Color;

import cmu.varviz.trace.NodeColor;

public interface VarvizConstants {

	Color BLACK = new Color(null, 0, 0, 0);
	Color WHITE = new Color(null, 255, 255, 255);
	Color GRAY2 = new Color(null, 240, 240, 240);
	Color GRAY = new Color(null, 150, 150, 150);

	Color red = new Color(null, 255, 0, 0);
	Color yellow = new Color(null, 255, 200, 0);
	Color darkorange = new Color(null, 255, 160, 0);
	Color limegreen = new Color(null, 124, 252, 0);
	Color firebrick1 = new Color(null, 255, 100, 0);
	Color tomato = firebrick1;

	static Color getColor(NodeColor c) {
		if (c == null) {
			return GRAY2;
		}
		switch (c) {
		case black:
			return BLACK;
		case darkorange:
			return darkorange;
		case firebrick1:
			return firebrick1;
		case limegreen:
			return limegreen;
		case red:
			return red;
		case tomato:
			return tomato;
		case white:
			return WHITE;
		case yellow:
			return yellow;
		case gray:
			return GRAY;
		default:
			break;
		}
		return GRAY;
	}
}
