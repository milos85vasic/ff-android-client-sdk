package io.harness.cfsdk.cloud.analytics;

import io.harness.cfsdk.cloud.analytics.model.Analytics;
import io.harness.cfsdk.cloud.core.model.Variation;
import io.harness.cfsdk.cloud.model.EventType;
import io.harness.cfsdk.cloud.model.Target;

public class AnalyticsBuilder {

    private Target target;
    private String evaluationId;
    private Variation variation;
    private EventType eventType;

    {

        eventType = EventType.METRICS;
    }

    public AnalyticsBuilder target(Target target) {

        this.target = target;
        return this;
    }

    public AnalyticsBuilder variation(Variation variation) {

        this.variation = variation;
        return this;
    }

    public AnalyticsBuilder eventType(EventType eventType) {

        this.eventType = eventType;
        return this;
    }

    public AnalyticsBuilder evaluationId(String evaluationId) {

        this.evaluationId = evaluationId;
        return this;
    }

    public Analytics build() {

        return new Analytics(

                target,
                evaluationId,
                variation,
                eventType
        );
    }
}
