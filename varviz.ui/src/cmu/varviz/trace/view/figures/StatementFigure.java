package cmu.varviz.trace.view.figures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.draw2d.FreeformLayout;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;

import cmu.conditional.Conditional;
import cmu.varviz.VarvizConstants;
import cmu.varviz.trace.NodeColor;
import cmu.varviz.trace.Statement;
import cmu.varviz.trace.view.editparts.EditPartUtils;
import de.fosd.typechef.featureexpr.FeatureExpr;

/**
 * TODO description
 * 
 * @author Jens Meinicke
 *
 */
public class StatementFigure extends RoundedRectangle {

	private Statement<?> statement;
	private final Label label = new Label();
	private final Label a = new Label();
	private final Label b = new Label();
	private SourceAnchor sourceAnchor;
	private TargetAnchor targetAnchor;
	private static final int BORDER_MARGIN = 10;
	private static final int MIN_WIDTH = 20;
	
	public StatementFigure(Statement<?> statement) {
		super();
		this.statement = statement;
		this.setLayoutManager(new FreeformLayout());
		NodeColor color = statement.getColor();
		setBackgroundColor(VarvizConstants.getColor(color));
		setCornerDimensions(new Dimension(20, 20));
		if (statement.getValue() != null) {
			createLabelName();
			if (statement.getOldValue() != null) {
				this.add(label);
				createLabelOld();
				this.add(a);
				
				Label arrowLabel = new Label("\u2192");
				arrowLabel.setFont(new Font(null, "arial",15, SWT.BOLD));
				arrowLabel.setSize(arrowLabel.getPreferredSize());
				arrowLabel.setLocation(new Point(a.getBounds().right() + BORDER_MARGIN / 2, a.getBounds().getRight().y - arrowLabel.getSize().height / 2));
				this.add(arrowLabel);
				
				createLabelNew(arrowLabel.getBounds().right() + BORDER_MARGIN / 2);
				this.add(b);
				
				Rectangle bounds = getBounds();
				bounds.setSize(b.getBounds().right()+ BORDER_MARGIN, BORDER_MARGIN + Math.max(a.getBounds().bottom(), b.getBounds().bottom()));
				setBounds(bounds);
			} else {
				this.add(label);

				createLabelNew(label.getBounds().right() + BORDER_MARGIN);
				this.add(b);
				
				Rectangle bounds = getBounds();
				bounds.setSize(b.getBounds().right()+ BORDER_MARGIN, BORDER_MARGIN + b.getBounds().bottom());
				setBounds(bounds);
			}
		} else {
			setName(statement.toString());
			this.add(label);
		}
		this.setOpaque(true);

		sourceAnchor = new SourceAnchor(this, statement);
		targetAnchor = new TargetAnchor(this, statement);
	}
	
	private void createLabelName() {
		label.setText(statement.toString());
		Dimension labelSize = label.getPreferredSize();
		label.setLocation(new Point(BORDER_MARGIN , BORDER_MARGIN));		
		label.setSize(labelSize);
	}

	private void createLabelOld() {
		Conditional<?> old = statement.getOldValue();
		a.setText(createText(old));
		Dimension labelSize = a.getPreferredSize();
		a.setLocation(new Point(label.getBounds().right() + BORDER_MARGIN, BORDER_MARGIN));		
		a.setSize(labelSize);
	}

	private String createText(Conditional<?> value) {
		if (value.isOne()) {
			return value.getValue().toString();
		} else {
			final List<FeatureExpr> sortedExpressions = new ArrayList<>();
			sortedExpressions.addAll(value.toMap().values());
			Collections.sort(sortedExpressions, (o1, o2) -> o1.toString().replaceAll("!", "Z").compareToIgnoreCase(o2.toString().replaceAll("!", "Z")));

			final StringBuilder text = new StringBuilder();
			for (FeatureExpr context : sortedExpressions) {
				text.append(value.simplify(context));
				text.append(" : ");
				text.append(EditPartUtils.getContext(context));
				text.append('\n');
			}
			return text.subSequence(0, text.length() - 1).toString();
		}
	}
	
	private void createLabelNew(int x) {
		Conditional<?> newValue = statement.getValue();
		b.setText(createText(newValue));
		b.setLocation(new Point(x, BORDER_MARGIN));
		Dimension labelSize = b.getPreferredSize();
						
		b.setSize(labelSize);
	}

	@Override
	public float getLineWidthFloat() {
		return statement.getWidth();
	}
	
	private void setName(String name){
		label.setText(name);
		Dimension labelSize = label.getPreferredSize();
		
		if (labelSize.width < MIN_WIDTH)
			labelSize.width = MIN_WIDTH;
				
		label.setSize(labelSize);

		Rectangle bounds = getBounds();
		bounds.setSize(labelSize.expand(BORDER_MARGIN * 2, BORDER_MARGIN));
		setBounds(bounds);
		label.setLocation(new Point(BORDER_MARGIN, BORDER_MARGIN / 2));
	}

	public SourceAnchor getSourceAnchor() {
		return sourceAnchor;
	}

	public TargetAnchor getTargetAnchor() {
		return targetAnchor;
	}
}
