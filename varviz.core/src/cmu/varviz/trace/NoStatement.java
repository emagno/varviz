package cmu.varviz.trace;

import de.fosd.typechef.featureexpr.FeatureExpr;
import de.fosd.typechef.featureexpr.bdd.BDDFeatureExprFactory;

class NoStatement<T> extends Statement<T> {

	private String name;

	private NoStatement(T op, FeatureExpr ctx) {
		super(op, null, ctx);
	}

	public NoStatement(String name) {
		this(null, BDDFeatureExprFactory.True());
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
