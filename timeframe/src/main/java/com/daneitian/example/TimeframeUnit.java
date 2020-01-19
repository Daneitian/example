package com.daneitian.example;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

public enum TimeframeUnit implements TemporalUnit {
	
	M15(Duration.ofMinutes(15)) {
		
		/* (non-Javadoc)
		 * @see com.daneitian.example.TimeframeUnit#round(java.time.temporal.Temporal)
		 */
		@Override
		public Temporal round(Temporal temporal) {
			
			BigDecimal minutes = BigDecimal.valueOf(temporal.get(ChronoField.MINUTE_OF_HOUR));
			BigDecimal timeframe = BigDecimal.valueOf(this.getDuration().toMinutes());
			
			BigDecimal frameCount = minutes.divide(timeframe, RoundingMode.FLOOR);
			long amountMinutes = frameCount.multiply(timeframe).longValue();
			
			return temporal.with(ChronoField.MINUTE_OF_HOUR, 0).with(ChronoField.SECOND_OF_MINUTE, 0).with(ChronoField.MILLI_OF_SECOND, 0).plus(amountMinutes, ChronoUnit.MINUTES);
		}
	};
	
	private final Duration duration;
	
	private TimeframeUnit(Duration duration) {
		this.duration = duration;
	}
	
	public abstract Temporal round(Temporal temporal);
	
	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalUnit#getDuration()
	 */
	public Duration getDuration() {
		return this.duration;
	}
	
	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalUnit#isDurationEstimated()
	 */
	public boolean isDurationEstimated() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalUnit#isDateBased()
	 */
	public boolean isDateBased() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalUnit#isTimeBased()
	 */
	public boolean isTimeBased() {
		return false;
	}
	
	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalUnit#addTo(R, long)
	 */
	@SuppressWarnings("unchecked")
	public <R extends Temporal> R addTo(R temporal, long amount) {
		
		long amountToAdd = amount * this.duration.getSeconds();
		return (R) this.round(temporal.plus(amountToAdd, ChronoUnit.SECONDS));
	}
	
	/* (non-Javadoc)
	 * @see java.time.temporal.TemporalUnit#between(java.time.temporal.Temporal, java.time.temporal.Temporal)
	 */
	public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
		
		Temporal inclusive = this.round(temporal1Inclusive);
		Temporal exclusive = this.round(temporal2Exclusive);
		return inclusive.until(exclusive, ChronoUnit.SECONDS) / this.duration.getSeconds();
	}
}
