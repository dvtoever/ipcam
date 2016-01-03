package nl.vantoever.repository;

import com.ninja_squad.dbsetup.operation.Operation;

import static com.ninja_squad.dbsetup.Operations.deleteAllFrom;

/**
 * Created by dvtoever on 3-1-2016.
 */
public class CommonOperations {

    /** Clear all tables */
    public static final Operation DELETE_ALL = deleteAllFrom("alarm_event");

}
