package org.openrefine.model.changes;

import org.openrefine.history.Change;
import org.openrefine.history.dag.DagSlice;
import org.openrefine.model.GridState;

public class FillDownChange implements Change {

	@Override
	public GridState apply(GridState projectState) throws DoesNotApplyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isImmediate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public DagSlice getDagSlice() {
		// TODO Auto-generated method stub
		return null;
	}

}
