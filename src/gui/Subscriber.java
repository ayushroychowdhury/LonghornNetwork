package gui;

import program.StudentGraph;

public interface Subscriber {
    void update(StudentGraph podGraph, StudentGraph referralGraph);
}
