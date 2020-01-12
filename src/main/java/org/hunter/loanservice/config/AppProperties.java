package org.hunter.loanservice.config;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {
    private static final BigDecimal RATE_INCREMENT = new BigDecimal(".10");

    private String fixed30YearMinRate;
    private String fixed30YearMaxRate;

    private String fixed15YearMinRate;
    private String fixed15YearMaxRate;

    private Set<BigDecimal> fixed30YearRates;
    private Set<BigDecimal> fixed15YearRates;

    public String getFixed30YearMinRate() {
        return fixed30YearMinRate;
    }

    public void setFixed30YearMinRate(String fixed30YearMinRate) {
        this.fixed30YearMinRate = fixed30YearMinRate;
    }

    public String getFixed30YearMaxRate() {
        return fixed30YearMaxRate;
    }

    public void setFixed30YearMaxRate(String fixed30YearMaxRate) {
        this.fixed30YearMaxRate = fixed30YearMaxRate;
    }

    public String getFixed15YearMinRate() {
        return fixed15YearMinRate;
    }

    public void setFixed15YearMinRate(String fixed15YearMinRate) {
        this.fixed15YearMinRate = fixed15YearMinRate;
    }

    public String getFixed15YearMaxRate() {
        return fixed15YearMaxRate;
    }

    public void setFixed15YearMaxRate(String fixed15YearMaxRate) {
        this.fixed15YearMaxRate = fixed15YearMaxRate;
    }

    public Set<BigDecimal> getFixedRates(int term) {
        if (term == 15) {
            return getFixed15YearRates();
        }
        else if (term == 30) {
            return getFixed30YearRates();
        }

        return null;
    }

    public Set<BigDecimal> getFixed30YearRates() {
        if (fixed30YearRates == null) {
            fixed30YearRates = new LinkedHashSet<BigDecimal>();

            if (fixed30YearMaxRate == null) {
                throw new RuntimeException("fixed30YearMaxRate not set!");
            }

            if (fixed30YearMinRate == null) {
                throw new RuntimeException("fixed30YearMinRate not set!");
            }

            BigDecimal start = new BigDecimal(fixed30YearMinRate);
            BigDecimal end = new BigDecimal(fixed30YearMaxRate);

            while (start.compareTo(end) < 1) {
                fixed30YearRates.add(start);
                start = start.add(RATE_INCREMENT);
            }
        }

        return fixed30YearRates;
    }

    public Set<BigDecimal> getFixed15YearRates() {
        if (fixed15YearRates == null) {
            fixed15YearRates = new LinkedHashSet<BigDecimal>();

            if (fixed15YearMaxRate == null) {
                throw new RuntimeException("fixed15YearMaxRate not set!");
            }

            if (fixed15YearMinRate == null) {
                throw new RuntimeException("fixed15YearMinRate not set!");
            }

            BigDecimal start = new BigDecimal(fixed15YearMinRate);
            BigDecimal end = new BigDecimal(fixed15YearMaxRate);

            while (start.compareTo(end) < 1) {
                fixed15YearRates.add(start);
                start = start.add(RATE_INCREMENT);
            }
        }

        return fixed15YearRates;
    }

}
