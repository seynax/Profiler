package fr.seynax.profiler;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class Main
{
	private long  window;
	private Graph graph;

	public void run()
	{
		init();
		graph = new Graph();
		graph.init();
		loop();
		graph.cleanup();

		// Free the window callbacks and destroy the window
		Callbacks.glfwFreeCallbacks(window);
		GLFW.glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
	}

	private void init()
	{
		// Initialize GLFW
		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("Unable to initialize GLFW");
		}

		// Configure GLFW
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

		// Create the window
		window = GLFW.glfwCreateWindow(800, 600, "LWJGL Graph", MemoryUtil.NULL, MemoryUtil.NULL);
		if (window == MemoryUtil.NULL)
		{
			throw new RuntimeException("Failed to create the GLFW window");
		}

		// Get the resolution of the primary monitor
		GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

		// Center the window
		GLFW.glfwSetWindowPos(window, (vidmode.width() - 800) / 2, (vidmode.height() - 600) / 2);

		// Make the OpenGL context current
		GLFW.glfwMakeContextCurrent(window);
		// Enable v-sync
		GLFW.glfwSwapInterval(1);

		// Make the window visible
		GLFW.glfwShowWindow(window);

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
	}

	private void loop()
	{
		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while (!GLFW.glfwWindowShouldClose(window))
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT); // clear the framebuffer

			// Render graphics using NanoVG
			graph.render();

			GLFW.glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			GLFW.glfwPollEvents();
		}
	}

	public static void main(String[] args)
	{
		new Main().run();
	}
}