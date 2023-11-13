package org.usfirst.frc3620.misc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import edu.wpi.first.wpilibj.Timer;

public class ValueTracker {
    double value = 0;
    Map<UUID, QueryRecord> queryRecords = new HashMap<>();

    public class Change {
        private double valueDelta;
        private double timeDelta;
        private Change (double v, double t) {
            valueDelta = v;
            timeDelta = t;
        }
        public double getValueDelta() {
            return valueDelta;
        }
        public double getTimeDelta() {
            return timeDelta;
        }
        public double getRate() {
            if (timeDelta == 0) return 0;
            return valueDelta / timeDelta;
        }
    }

    class QueryRecord {
        protected double lastQueried;
        protected double lastValue;
    }

    public double bump (double delta) {
        value = value + delta;
        return value;
    }

    public double bump() {
        return bump(1.0);
    }

    public void set(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }

    public Change getChange (UUID uuid) {
        QueryRecord record = queryRecords.get(uuid);
        double dt = 0;
        double dv = this.value;
        double now = Timer.getFPGATimestamp();
        if (record == null) {
            record = new QueryRecord();
            queryRecords.put(uuid, record);
        } else {
            dt = now - record.lastQueried;
            dv = this.value - record.lastValue;
        }
        record.lastQueried = now;
        record.lastValue = this.value;
        return new Change(dv, dt);
    }
}
