package rule;

import model.Record;

import java.util.List;

public interface Rule {

    /**
     *
     * @param list A sorted list of Records for a specified user id,
     *             each representing one file from sourceData
     *
     * @return     The number of times the rule is satisfied for the given list
     *
     */
    public int satisfiesRule(List<Record> list);

}
