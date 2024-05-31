package fr.seynax.profiler.utils;

import lombok.Getter;
import lombok.Setter;

public class Timer extends Chronometer
{
	private @Setter @Getter long duration;

	public Timer()
	{
		this.duration = 0;
	}

	public Timer(long durationIn)
	{
		this.duration = durationIn;
	}

	public void update()
	{
		this.time = System.nanoTime() - this.start;
	}

	public final boolean isReached()
	{
		this.update();
		return this.time() >= this.duration;
	}
}