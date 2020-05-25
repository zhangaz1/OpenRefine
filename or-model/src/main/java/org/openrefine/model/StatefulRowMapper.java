package org.openrefine.model;

import java.io.Serializable;

/**
 * A row mapper which is able to update a state as it
 * reads rows. Only use this if {@link RowMapper} cannot
 * be used.
 * 
 * @author Antonin Delpeuch
 *
 * @param <S>
 */
public interface StatefulRowMapper<S> extends Serializable {
    
    public static class RowAndState<S> {
        public final Row row;
        public final S state;
        public RowAndState(Row row, S state) {
            this.row = row;
            this.state = state;
        }
    }
    
    public RowAndState<S> call(S state, long rowIndex, Row row);
}
