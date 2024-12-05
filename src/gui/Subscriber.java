package gui;

import program.StudentGraph;

/**
 * Interface for subscribers to the control panel
 */
public interface Subscriber {
    /**
     * Notify the subscriber of an update
     * @param podGraph the graph describing the student relationships
     * @param referralGraph the graph describing the student referrals (inverted relationships)
     */
    void update(StudentGraph podGraph, StudentGraph referralGraph);
}
