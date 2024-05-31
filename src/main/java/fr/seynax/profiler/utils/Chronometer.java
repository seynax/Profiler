package fr.seynax.profiler.utils;

import lombok.Getter;

public class Chronometer
{
	protected @Getter long start;
	protected @Getter long time;

	public Chronometer()
	{
		this.start();
	}

	public void start()
	{
		this.start = System.nanoTime();
	}

	public long stop()
	{
		this.time = System.nanoTime() - this.start;

		return this.time;
	}
}