package fr.seynax.profiler.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.function.Function;

@Getter
public class Scheduler<T, R> extends Timer
{
	private @Setter Function<T, R> function;

	private         Thread  thread;
	private         boolean isRunning;
	private         boolean hasBeenExecuted;
	private         boolean isStopped;
	private @Setter int     sleepInterval;
	private         R       result;

	public Scheduler(long durationIn)
	{
		super(durationIn);
	}

	public final void start()
	{
		this.start(null);
	}

	public final void start(T inputIn)
	{
		this.isRunning = true;
		this.isStopped = false;

		this.thread = new Thread(() ->
		                         {
			                         while (this.isRunning)
			                         {
				                         if (this.isReached() && !this.hasBeenExecuted)
				                         {
					                         this.isRunning       = false;
					                         this.hasBeenExecuted = true;

					                         this.result = this.function.apply(inputIn);
					                         break;
				                         }

				                         if (this.sleepInterval > 0)
				                         {
					                         try
					                         {
						                         Thread.sleep(this.sleepInterval);
					                         }
					                         catch (InterruptedException e)
					                         {
						                         e.printStackTrace();
					                         }
				                         }
			                         }

			                         this.isStopped = true;
		                         });
		this.thread.start();
		super.start();
	}

	public final long stop()
	{
		super.stop();

		this.isRunning = false;

		try
		{
			this.thread.join();
			var start = System.nanoTime();
			while (!this.isStopped && System.nanoTime() - start < 1_000_000L)
			{
				Thread.sleep(10);
			}
			this.thread.interrupt();
		}
		catch (InterruptedException eIn)
		{
			throw new RuntimeException(eIn);
		}

		this.thread = null;

		this.isStopped       = true;
		this.hasBeenExecuted = false;

		return this.time();
	}
}