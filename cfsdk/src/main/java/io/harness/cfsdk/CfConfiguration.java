package io.harness.cfsdk;

import io.harness.cfsdk.cloud.analytics.AnalyticsCacheFactory;

/**
 * Main configuration class used to tune the behaviour of {@link CfClient}. It uses builder pattern.
 */
public class CfConfiguration {

    public static final int MIN_FREQUENCY;

    private static final String BASE_URL;
    private static final String EVENT_URL;
    private static final String STREAM_URL;

    private final int frequency;
    private final int bufferSize;
    private final int pollingInterval;

    private final String baseURL;
    private final String eventURL;
    private final String streamURL;

    private boolean analyticsEnabled;
    private final boolean streamEnabled;

    private String analyticsCacheType;

    private long metricsServiceAcceptableDuration;

    static {

        MIN_FREQUENCY = 60;
    }

    {

        bufferSize = 1024;
        analyticsEnabled = true;
        frequency = MIN_FREQUENCY; // unit: second
        metricsServiceAcceptableDuration = 10000;
        analyticsCacheType = AnalyticsCacheFactory.DEFAULT_CACHE;
    }

    static {

        BASE_URL = "https://config.ff.harness.io/api/1.0";
        STREAM_URL = BASE_URL + "/stream";
        EVENT_URL = "https://events.ff.harness.io/api/1.0";
    }

    protected CfConfiguration(

            String baseURL,
            String streamURL,
            boolean streamEnabled,
            boolean analyticsEnabled,
            int pollingInterval
    ) {

        this.baseURL = baseURL;
        this.streamURL = streamURL;
        this.eventURL = EVENT_URL;
        this.streamEnabled = streamEnabled;
        this.pollingInterval = pollingInterval;
        this.analyticsEnabled = analyticsEnabled;
    }

    protected CfConfiguration(

            String baseURL,
            String streamURL,
            boolean streamEnabled,
            int pollingInterval
    ) {

        this.baseURL = baseURL;
        this.streamURL = streamURL;
        this.eventURL = EVENT_URL;
        this.streamEnabled = streamEnabled;
        this.pollingInterval = pollingInterval;
    }

    CfConfiguration(

            String baseURL,
            String streamURL,
            String eventURL,
            boolean streamEnabled,
            boolean analyticsEnabled,
            int pollingInterval
    ) {

        this.baseURL = baseURL;
        this.streamURL = streamURL;
        this.eventURL = eventURL;
        this.streamEnabled = streamEnabled;
        this.pollingInterval = pollingInterval;
        this.analyticsEnabled = analyticsEnabled;
    }

    CfConfiguration(

            String baseURL,
            String streamURL,
            String eventURL,
            boolean streamEnabled,
            int pollingInterval
    ) {

        this.baseURL = baseURL;
        this.streamURL = streamURL;
        this.eventURL = eventURL;
        this.streamEnabled = streamEnabled;
        this.pollingInterval = pollingInterval;
    }

    public String getBaseURL() {

        return baseURL;
    }

    public String getStreamURL() {

        return streamURL;
    }

    public String getEventURL() {
        return eventURL;
    }

    public boolean getStreamEnabled() {
        return streamEnabled;
    }

    /**
     * Are analytics enabled?
     *
     * @return True == Using SDK metrics is enabled.
     */
    public boolean isAnalyticsEnabled() {

        return analyticsEnabled;
    }

    public static Builder builder() {

        return new Builder();
    }

    public int getPollingInterval() {
        return pollingInterval;
    }

    public static class Builder {

        private String baseURL;
        private String eventURL;
        private String streamURL;
        private int pollingInterval;
        private boolean streamEnabled;
        private boolean analyticsEnabled;
        private long metricsServiceAcceptableDuration;

        {

            analyticsEnabled = true;
            metricsServiceAcceptableDuration = 10000;
        }

        /**
         * Sets the base API url
         *
         * @param baseURL Base url for the API.
         * @return Builder instance.
         */
        public Builder baseUrl(String baseURL) {

            this.baseURL = baseURL;
            return this;
        }

        /**
         * Sets the metrics events API url
         *
         * @param eventURL Base url for the metrics API.
         * @return Builder instance.
         */
        public Builder eventUrl(String eventURL) {

            this.eventURL = eventURL;
            return this;
        }

        /**
         * Sets the stream url to be used for realtime evaluation update
         *
         * @param streamURL Base url for the SSE API.
         * @return Builder instance.
         */
        public Builder streamUrl(String streamURL) {

            this.streamURL = streamURL;
            return this;
        }

        /**
         * Configuration to explicitly enable or disable analytics. If enabled, SDK metrics will be used.
         *
         * @param analyticsEnabled True == Analytics enabled.
         * @return Builder instance.
         */
        public Builder enableAnalytics(boolean analyticsEnabled) {

            this.analyticsEnabled = analyticsEnabled;
            return this;
        }

        /**
         * Configuration to explicitly enable or disable stream. If enabled, the stream url must be valid.
         *
         * @param streamEnabled True == SSE is enabled.
         * @return Builder instance.
         */
        public Builder enableStream(boolean streamEnabled) {

            this.streamEnabled = streamEnabled;
            return this;
        }

        /**
         * Polling interval to use when getting new evaluation data from server
         *
         * @param pollingInterval Polling interval.
         * @return Builder instance.
         */
        public Builder pollingInterval(int pollingInterval) {

            this.pollingInterval = pollingInterval;
            return this;
        }

        /**
         * Metrics service acceptable duration.
         *
         * @return Duration in milliseconds.
         */
        public long getMetricsServiceAcceptableDuration() {

            return metricsServiceAcceptableDuration;
        }

        /**
         * @param metricsServiceAcceptableDuration Metrics service acceptable duration.
         * @return This builder.
         */
        public Builder setMetricsServiceAcceptableDuration(long metricsServiceAcceptableDuration) {

            this.metricsServiceAcceptableDuration = metricsServiceAcceptableDuration;
            return this;
        }

        /**
         * Build the configuration instance.
         *
         * @return Configuration instance.
         */
        public CfConfiguration build() {

            if (baseURL == null || baseURL.isEmpty()) {

                baseURL = BASE_URL;
            }
            if (eventURL == null || eventURL.isEmpty()) {

                eventURL = EVENT_URL;
            }
            if (streamEnabled && (streamURL == null || streamURL.isEmpty())) {

                streamURL = STREAM_URL;
            }

            final CfConfiguration cfConfiguration = new CfConfiguration(

                    baseURL, streamURL, eventURL, streamEnabled, analyticsEnabled, pollingInterval
            );

            cfConfiguration.setMetricsServiceAcceptableDuration(metricsServiceAcceptableDuration);
            return cfConfiguration;
        }
    }

    public int getFrequency() {

        return Math.max(frequency, MIN_FREQUENCY);
    }

    /*
     BufferSize must be a power of 2 for LMAX to work. This function vaidates
     that. Source: https://stackoverflow.com/a/600306/1493480
    */
    public int getBufferSize() {

        return bufferSize;
    }

    public String getAnalyticsCacheType() {

        return analyticsCacheType;
    }

    public void setAnalyticsCacheType(String analyticsCacheType) {

        this.analyticsCacheType = analyticsCacheType;
    }

    public long getMetricsServiceAcceptableDuration() {

        return metricsServiceAcceptableDuration;
    }

    public void setMetricsServiceAcceptableDuration(long metricsServiceAcceptableDuration) {

        this.metricsServiceAcceptableDuration = metricsServiceAcceptableDuration;
    }
}
