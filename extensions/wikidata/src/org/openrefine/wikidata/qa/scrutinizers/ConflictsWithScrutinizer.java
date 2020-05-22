package org.openrefine.wikidata.qa.scrutinizers;

import org.openrefine.wikidata.qa.QAWarning;
import org.openrefine.wikidata.updates.ItemUpdate;
import org.wikidata.wdtk.datamodel.interfaces.PropertyIdValue;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConflictsWithScrutinizer extends EditScrutinizer {

    public static final String type = "having-conflicts-with-statements";

    @Override
    public void scrutinize(ItemUpdate update) {
        Map<PropertyIdValue, Value> propertyIdValueValueMap = new HashMap<>();
        for (Statement statement : update.getAddedStatements()){
            PropertyIdValue pid = statement.getClaim().getMainSnak().getPropertyId();
            Value value = statement.getClaim().getMainSnak().getValue();
            propertyIdValueValueMap.put(pid, value);
        }

        for(PropertyIdValue propertyId : propertyIdValueValueMap.keySet()){
            List<PropertyIdValue> conflictingProperties = _fetcher.getConflictsWithProperties(propertyId);
            if (conflictingProperties != null){
                for (PropertyIdValue conflictingPid : conflictingProperties) {
                    if (raiseWarning(propertyId, propertyIdValueValueMap, conflictingPid)) {
                        QAWarning issue = new QAWarning(type, propertyId.getId(), QAWarning.Severity.WARNING, 1);
                        issue.setProperty("property_entity", propertyId);
                        issue.setProperty("added_property_entity", conflictingPid);
                        issue.setProperty("example_entity", update.getItemId());
                        addIssue(issue);
                    }
                }
            }
        }
    }

    private boolean raiseWarning(PropertyIdValue propertyId, Map<PropertyIdValue, Value> propertyIdValueValueMap, PropertyIdValue conflictingPid) {
        List<Value> conflictingValues = _fetcher.getItemwithConflicts(propertyId);
        if (conflictingValues == null){
            return true;
        }

        if (propertyIdValueValueMap.containsKey(conflictingPid)){
            for (Value value : conflictingValues) {
                if (propertyIdValueValueMap.get(conflictingPid).equals(value)){
                    return true;
                }
            }
        }

        return false;
    }
}
