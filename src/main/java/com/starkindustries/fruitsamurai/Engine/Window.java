package com.starkindustries.fruitsamurai.Engine;

import org.lwjgl.glfw.GLFWCursorPosCallback;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLUtil;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private long windowID;

    private int width;
    private int height;
    private boolean vsync;
    private boolean resized = false;
    private String windowtitle;
    @SuppressWarnings("unused")
    private GLFWCursorPosCallback posCallback;
    private final WindowOptions opts;
    private double mouseX;
    private double mouseY;
    private boolean fullscreen;
    public  boolean[] keys = new boolean[65536];


    public Window(String title, int width, int height, boolean vSync,WindowOptions opts) {
        this.windowtitle = title;
        this.width = width;
        this.height = height;
        this.vsync = vSync;
        this.resized = false;
        this.fullscreen = false;
        this.opts = opts;
    }

    public void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_DEBUG_CONTEXT, GLFW_TRUE);
        if (opts.compatibleProfile) {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_COMPAT_PROFILE);
        } else {
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        }

        // Create the window
        windowID = glfwCreateWindow(width, height, windowtitle, NULL, NULL);
        if (windowID == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Setup resize callback
        glfwSetFramebufferSizeCallback(windowID, (window, width, height) -> {
            this.width = width;
            this.height = height;
            this.setResized(true);
        });

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(windowID, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
            /*if (isKeyReleased(GLFW_KEY_F12) && fullscreen){
                glfwSetWindowMonitor(windowID,0L,xpos,ypos,width,height,GLFW_DONT_CARE);
                fullscreen = !fullscreen;
            }
            else
            {
                glfwSetWindowMonitor(windowID, glfwGetPrimaryMonitor(), xpos, ypos, width, height, GLFW_DONT_CARE);
                fullscreen = !fullscreen;
            }*/
        });
        glfwSetCursorPosCallback(windowID, posCallback = GLFWCursorPosCallback.create((window, xpos, ypos) -> {
            mouseX = xpos;
            mouseY = ypos;
        }));

        // Get the resolution of the primary monitor
        GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
                windowID,
                (vidmode.width() - width) / 2,
                (vidmode.height() - height) / 2
        );

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowID);

        if (isvSync()) {
            // Enable v-sync
            glfwSwapInterval(1);
        }

        // Make the window visible
        glfwShowWindow(windowID);

        GL.createCapabilities();
        //GLUtil.setupDebugMessageCallback();
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glEnable(GL_DEPTH_TEST);
        glEnable(GL_STENCIL_TEST);
        if (opts.showTriangles) {
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        }
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    }

    public void setClearColor(float r, float g, float b, float alpha) {
        glClearColor(r, g, b, alpha);
    }

    public boolean isKeyPressed(int keyCode) {
        return glfwGetKey(windowID, keyCode) == GLFW_PRESS;
    }

    public boolean isKeyReleased(int keyCode) {
        return glfwGetKey(windowID, keyCode) == GLFW_RELEASE;
    }


    public boolean isMouseKeyPressed(int keyCode) {
        return glfwGetMouseButton(windowID, keyCode) == GLFW_PRESS;
    }

    public boolean isMouseKeyReleased(int keyCode) {
        return glfwGetMouseButton(windowID, keyCode) == GLFW_RELEASE;
    }

    public boolean windowShouldClose() {
        return glfwWindowShouldClose(windowID);
    }

    public void closeWindow()
    {
        glfwSetWindowShouldClose(windowID, true);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isResized() {
        return resized;
    }

    public void setResized(boolean resized) {
        this.resized = resized;
    }

    public boolean isvSync() {
        return vsync;
    }

    public void setvsync(boolean vsync) {
        this.vsync = vsync;
    }

    public void update() {
        glfwSwapBuffers(windowID);
        glfwPollEvents();
    }

    public double getMouseX() {
        return mouseX;
    }

    public double getMouseY() {
        return mouseY;
    }

    public void hideMouse() {
        glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    }

    public void showMouse() {
        glfwSetInputMode(windowID, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public WindowOptions getOptions() {
        return opts;
    }
    public void setWindowTitle(String title) {
        glfwSetWindowTitle(windowID, title);
    }
    public String getWindowTitle() {
        return windowtitle;
    }


    public static class WindowOptions {
        public boolean showTriangles;
        public boolean showFps;
        public boolean compatibleProfile;
    }
}
